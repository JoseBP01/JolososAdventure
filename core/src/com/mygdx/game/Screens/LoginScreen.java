package com.mygdx.game.Screens;

import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class LoginScreen extends MyScreen {
    NakamaSessionManager nakamaSessionManager;

    public LoginScreen(JadventureMain game, NakamaSessionManager nakamaSessionManager) {
        super(game);
        this.nakamaSessionManager = nakamaSessionManager;
    }

    @Override
    public void show() {
        super.show();
        nakamaSessionManager.iniciarSesion("gerard@gerard2.com", "Test1234", "usuarioPrueba", new NakamaSessionManager.IniciarSesionCallback() {
            @Override
            public void loginOk() {
                System.out.println("se ha logueado " + NakamaSessionManager.account.getEmail());


            }

            @Override
            public void loginError(String error) {
                System.out.println("Errror " + error);
            }
        });
        setScreen(new MenuScreen( game,nakamaSessionManager));
    }
}
