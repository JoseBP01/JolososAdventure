package com.mygdx.game.NakamaController;


import com.badlogic.gdx.physics.box2d.World;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.heroiclabs.nakama.*;
import com.heroiclabs.nakama.api.Account;
import com.heroiclabs.nakama.api.Rpc;
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
    private String idJugador;
    private List<Position> posRecibidas = new ArrayList<>();
    private List<PersonajeOnline> personajeOnlineList = new ArrayList<>();
    private World world;

    public void setWorld(World world) {
        this.world = world;
    }

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
            idJugador = session.getUserId();
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
                System.out.format("Received a message on channel %s", message.getChannelId());
                System.out.format("Message content: %s", message.getContent());
            }

            @Override
            public void onMatchData(MatchData matchData) {
                super.onMatchData(matchData);
                recibirDatosPartida(matchData);
            }

            @Override
            public void onMatchPresence(MatchPresenceEvent matchPresence) {
                super.onMatchPresence(matchPresence);
                int i=0;
                String[] nombre;
                nombre = matchPresence.toString().split(",");
                for (String s:nombre){
                    if (i == 5){
                        String[] idLimpio = s.split("\\)");
                        String id = idLimpio[0].substring(8);
                        System.out.println(id);
                        if (id.equals(session.getUserId())){
                        }else {
                            System.out.println("nuevo personaje añadido");
                            nuevoJugador(id);
                        }
                    }
                    i++;
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

    private void nuevoJugador(String joins) {
//        PersonajeOnline personajeOnline = new PersonajeOnline(joins.toString());
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

    public void enviarMensaje(){
        while (channel.getId() == null){
            System.out.println("no hay id");
        }
        String channelId = channel.getId();
        final String content = "{\"message\":\"Hello world\"}";
        try {
            ChannelMessageAck sendAck = socket.writeChatMessage(channelId, content).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void enviarDatosPartida(Personaje personaje,int opCode){
        //{id = session.getUserId, pos = {x = position.x, y = position.y}}
        String data = "{\"id\" : \""+session.getUserId()+"\", "+"\"pos\""+": {\"x\":"+personaje.getX()+",\"y\":"+personaje.getY()+"}}";

     socket.sendMatchData(idPartida,1,data.getBytes(StandardCharsets.UTF_8));
    }

    private void recibirDatosPartida(MatchData matchData) {

        String datos = new String(matchData.getData());
        Position position = new Position();
        position.fromJson(datos);
        if (position != null){
            posRecibidas.add(position);

        }
//        posRecibidas.forEach(System.out::println);
    }

}
