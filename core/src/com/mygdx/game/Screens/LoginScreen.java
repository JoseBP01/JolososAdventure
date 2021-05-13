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
        nakamaSessionManager.iniciarSesion("fcano@elpuig.xeill.net", "Test1234", "test1", new NakamaSessionManager.IniciarSesionCallback() {            @Override
            public void loginOk() {
                System.out.println("se ha logueado " + NakamaSessionManager.account.getEmail());
            }

            @Override
            public void loginError(String error) {
                System.out.println("Error " + error);
            }
        });
        setScreen(new MenuScreen( game,nakamaSessionManager));
    }

}
