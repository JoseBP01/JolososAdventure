package com.mygdx.game.NakamaController;


import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.heroiclabs.nakama.*;
import com.heroiclabs.nakama.api.Account;
import com.heroiclabs.nakama.api.Rpc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NakamaSessionManager {
    public NakamaMatchMaking matchMaking;

    public DefaultClient client;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    public static Account account;
    private SocketClient socket;
    public Session session;
    private Match match;

    public interface IniciarSesionCallback {
        void loginOk();
        void loginError(String error);
    }

    public NakamaSessionManager() {
        client = new DefaultClient("mynewkey", "192.168.0.20", 7349, false);
        String host = "192.168.0.20";
        int port = 7350; // different port to the main API port
        socket = client.createSocket(host, port, false);
        matchMaking = new NakamaMatchMaking(socket);
    }

    public void iniciarSesion(String email, String password, String username, IniciarSesionCallback callback){

        ListenableFuture<Session> authFuture = client.authenticateEmail(email, password,username);

        try {
            session = authFuture.get();
            matchMaking.setSession(session);
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
        Rpc rpc = null;
        try {
            rpc = socket.rpc("get_world_id").get();
            return rpc.getPayload();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void crearPartida(NakamaMatchMaking.Matcheado matcheado){

        SocketListener listener = new AbstractSocketListener() {
            @Override
            public void onChannelMessage(final com.heroiclabs.nakama.api.ChannelMessage message) {
                System.out.format("Received a message on channel %s", message.getChannelId());
                System.out.format("Message content: %s", message.getContent());
            }
        };

        try {
            socket.connect(session,listener,true).get();
            System.out.println("Socket connected successfully.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        socket.joinMatch(listarPartidas());
        matcheado.PartidaEncontrada();
    }

    public void unirsePartida(){
        SocketListener listener = new AbstractSocketListener() {
            @Override
            public void onChannelMessage(final com.heroiclabs.nakama.api.ChannelMessage message) {
                System.out.format("Received a message on channel %s", message.getChannelId());
                System.out.format("Message content: %s", message.getContent());
            }
        };

        try {
            socket.connect(session,listener,true).get();
            System.out.println("Socket connected successfully.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            socket.joinMatch(listarPartidas()).get();
//            for (UserPresence presence : match1.getPresences()) {
//                System.out.format("User id %s name %s.", presence.getUserId(), presence.getUsername());
//            }
//            System.out.println(match1.toString());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

//    public void enviarMensajePrueba(){
//        int opCode = 1;
//        String data = "{\"message\":\"Hello world\"}";
//        socket.sendMatchData(idPartida[0], opCode, data.getBytes(StandardCharsets.UTF_8));
//    }
}
