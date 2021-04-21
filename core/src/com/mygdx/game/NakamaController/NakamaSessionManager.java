package com.mygdx.game.NakamaController;


import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.heroiclabs.nakama.DefaultClient;
import com.heroiclabs.nakama.Session;
import com.heroiclabs.nakama.api.Account;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NakamaSessionManager {
    private final DefaultClient client;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    public static Account account;

    public interface IniciarSesionCallback {
        void loginOk();
        void loginError(String error);
    }

    public NakamaSessionManager() {
        client = new DefaultClient("mynewkey", "192.168.22.155", 7349, false);
    }
    public void iniciarSesion(String email, String password, IniciarSesionCallback callback){

//        String email = "super@heroes.com";
//        String password = "batsignal";

        ListenableFuture<Session> authFuture = client.authenticateEmail(email, password);

        AsyncFunction<Session, Account> accountFunction = session -> client.getAccount(session);

        ListenableFuture<Account> accountFuture = Futures.transformAsync(authFuture, accountFunction, executor);
        Futures.addCallback(accountFuture, new FutureCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
//                System.out.println("Got account: " + account.getUser().getId());
                NakamaSessionManager.account = account;
                callback.loginOk();
                executor.shutdown();
            }

            @Override
            public void onFailure(Throwable e) {
//                System.out.println("ERORORORERR " + e.getMessage());
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

    public void iniciarSesion2(String username, String password){
        try {
            Session session = client.authenticateEmail(username, password).get();
            client.createGroup(session, "grupotal");

            System.out.println(session);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error al iniciar sesion1:" + e.getMessage());
        } catch (ExecutionException e) {
            System.out.println("Error al iniciar sesion2:" + e.getMessage());
        }
    }



    public void registroUsuario(){

    }
}
