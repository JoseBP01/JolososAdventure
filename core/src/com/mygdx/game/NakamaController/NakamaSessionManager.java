package com.mygdx.game.NakamaController;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.heroiclabs.nakama.*;
import com.heroiclabs.nakama.api.Account;
import com.heroiclabs.nakama.api.Rpc;
import com.mygdx.game.Actors.MyWorld;
import com.mygdx.game.Actors.Personaje;
import com.mygdx.game.Actors.PersonajeOnline;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NakamaSessionManager {

    public DefaultClient client;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    public static Account account;
    private SocketClient socket;
    public Session session;
    Channel channel = null;
    public NakamaChat nakamaChat;
    public NakamaStorage nakamaStorage;

    private String idPartida;
    private List<Position> posRecibidas = new ArrayList<>();
    private MyWorld myWorld;
    public String personajeAborrar;
    public List<String > mensajes = new ArrayList<>();
    ObjectMapper om = new ObjectMapper();

    public interface IniciarSesionCallback {
        void loginOk();
        void loginError(String error);
    }

    public NakamaSessionManager() {
        client = new DefaultClient("mynewkey", "192.168.22.198", 7349, false);
        String host = "192.168.22.198";
        int port = 7350; // different port to the main API port
        socket = client.createSocket(host, port, false);
    }

    public void iniciarSesion(String email, String password, String username, IniciarSesionCallback callback){

        ListenableFuture<Session> authFuture = client.authenticateEmail(email, password,username);

        try {
            session = authFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        AsyncFunction<Session, Account> accountFunction = session -> client.getAccount(session);

        ListenableFuture<Account> accountFuture = Futures.transformAsync(authFuture, accountFunction, executor);
        Futures.addCallback(accountFuture, new FutureCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                NakamaSessionManager.account = account;
                callback.loginOk();
                executor.shutdown();
            }

            @Override
            public void onFailure(Throwable e) {
                callback.loginError(e.getMessage());
                executor.shutdown();
            }
        }, executor);

        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("ERRORRRRR " + e.getMessage());
        }
    }

    public String  listarPartidas(){
        Rpc rpc;
        try {
            rpc = socket.rpc("get_world_id").get();
            idPartida = rpc.getPayload();
            return rpc.getPayload();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void registrarPersonaje(){
        try {
            Rpc rpc = socket.rpc("register_character_name","joloco").get();
            System.out.println("personaje registrado");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void unirsePartida(){
        SocketListener listener = new AbstractSocketListener() {
            @Override
            public void onChannelMessage(final com.heroiclabs.nakama.api.ChannelMessage message) {
                try {
                    mensajes.add(transformarMensaje(message));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                String[] msgArray = mensajes.toArray(new String[0]);
                myWorld.mensajesOnline.setItems(msgArray);
            }

            @Override
            public void onMatchData(MatchData matchData) {
                super.onMatchData(matchData);
                recibirDatosPartida(matchData);
            }

            @Override
            public void onMatchPresence(MatchPresenceEvent matchPresence) {
                super.onMatchPresence(matchPresence);
               if (matchPresence.getJoins() != null){
                   for (UserPresence us : matchPresence.getJoins()){
                       if (!us.getUserId().equals(session.getUserId())){
                           myWorld.addPersonajeOnline(us.getUserId(),0,0);
                           System.out.println("nuevo personaje añadido");
                       }
                   }
               }

                if (matchPresence.getLeaves() != null){
                    for (UserPresence presenceEvent: matchPresence.getLeaves()){
                        for(PersonajeOnline pO : myWorld.personajesOnline){
                            if (presenceEvent.getUserId().equals(pO.getId())){
                                myWorld.quitarPOnline = true;
                                personajeAborrar = presenceEvent.getUserId();
                                System.out.println("Personaje quitado");
                            }
                        }
                    }
                }

            }
        };

        try {
            socket.connect(session,listener,true).get();
            nakamaChat = new NakamaChat(socket);
            nakamaStorage = new NakamaStorage(this);
            System.out.println("Socket connected successfully.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            socket.joinMatch(listarPartidas()).get();
            registrarPersonaje();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private String transformarMensaje(com.heroiclabs.nakama.api.ChannelMessage content) throws JsonProcessingException {
        Mensaje mensaje = om.readValue(content.getContent(), Mensaje.class);
        return content.getUsername()+": "+mensaje.getMessage();
    }

    public void unirseChat() {
        String roomName = "game";
        try {
            channel = socket.joinChat(roomName,ChannelType.ROOM, true, false).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Conectado al chat: "+channel.getId());
    }

    public void enviarMensaje(String messageText){
        while (channel.getId() == null){
            System.out.println("no hay id");
        }
        String channelId = channel.getId();
        final String content = "{\"message\":\""+messageText+"\"}";
        try {
            ChannelMessageAck sendAck = socket.writeChatMessage(channelId, content).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void enviarDatosPartida(Personaje personaje,int opCode){
        String data = "{\"id\" : \""+session.getUserId()+"\", "+"\"pos\""+": {\"x\":"+personaje.getX()+",\"y\":"+personaje.getY()+"}}";
        socket.sendMatchData(idPartida,1,data.getBytes(StandardCharsets.UTF_8));
    }

    private void recibirDatosPartida(MatchData matchData) {

        String datos = new String(matchData.getData());
        Position position = new Position();
        posRecibidas = position.fromJson(datos);
        boolean hacerNuevoPersonaje = false;

        for (Position position1: posRecibidas){
            if (myWorld.personajesOnline.size() == 0 && !position1.id.equals(session.getUserId())){
                myWorld.addNuevoPOnline=true;
                myWorld.cargarNuevoPOnline(position1.id,position1.x,position1.y);
            }
            for (PersonajeOnline pO: myWorld.personajesOnline){

                if (position1.id.equals(pO.getId())){
                    pO.update(position1.x, position1.y);
                    hacerNuevoPersonaje = false;
                }else if (!position1.id.equals(session.getUserId()) && !pO.getId().equals(position1.id)){
                    hacerNuevoPersonaje = true;
                }

                if (hacerNuevoPersonaje){
                    myWorld.addNuevoPOnline = true;
                    myWorld.cargarNuevoPOnline(position1.id,position1.x,position1.y);
                }
            }
        }
        posRecibidas.clear();
    }

    public void setMyWorld(MyWorld myWorld){
        this.myWorld = myWorld;
    }

    public MyWorld getMyWorld() {
        return myWorld;
    }
}