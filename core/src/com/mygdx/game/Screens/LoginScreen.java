package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.Assets;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class LoginScreen extends MyScreen {
    NakamaSessionManager nakamaSessionManager;
    private boolean logeado = false;
    private boolean alRegistro;

    public LoginScreen(JadventureMain game, NakamaSessionManager nakamaSessionManager) {
        super(game);
        this.nakamaSessionManager = nakamaSessionManager;
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        table.setDebug(true);
        Label email = new Label("Email:",Assets.uiSkin);
        TextField emailText = new TextField("",Assets.uiSkin);
        Label password = new Label("Password:",Assets.uiSkin);
        TextField passwordText = new TextField("",Assets.uiSkin);
        passwordText.setPasswordMode(true);
        TextButton button = new TextButton("Iniciar sesion",Assets.uiSkin);
        Label registroText = new Label("No tienes cuenta registrate ahora", Assets.uiSkin);
        TextButton buttonRegistro = new TextButton("Registrarse",Assets.uiSkin);

        Label modoOfflineText = new Label("Modo Offline", Assets.uiSkin);
        TextButton buttonOffline = new TextButton("Modo Offline",Assets.uiSkin);

        button.addListener(event -> {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                if (!emailText.getText().equals("") && !passwordText.getText().equals("")){
                    nakamaSessionManager.iniciarSesion(emailText.getText(), passwordText.getText(), "", new NakamaSessionManager.IniciarSesionCallback() {
                        @Override
                        public void loginOk() {
                            System.out.println("se ha logueado " + NakamaSessionManager.account.getEmail());
                            logeado = true;
                        }

                        @Override
                        public void loginError(String error) {
                            System.out.println("Error " + error);
                        }
                    });
                }else {
                    System.out.println("Campo email o password no rellenado");
                }
            }
            return false;
        });

        buttonRegistro.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                    alRegistro = true;
                }
                return false;
            }
        });

        buttonOffline.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                    setScreen(new MenuScreen(game));
                }
                return false;
            }
        });

        table.add(email);
        table.add(emailText);
        table.row();
        table.add(password).spaceTop(10);
        table.add(passwordText).spaceTop(10);
        table.row();
        table.add(button).colspan(2).spaceTop(10);
        table.row();
        table.add(registroText).colspan(2).spaceTop(20);
        table.row();
        table.add(buttonRegistro).colspan(2);
        table.row();
        table.add(modoOfflineText).spaceTop(20);
        table.add(buttonOffline).spaceTop(20);
        table.setFillParent(true);
        stage.addActor(table);
        stage.act();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (logeado){
            setScreen(new MenuScreen( game,nakamaSessionManager));
        }

        if (alRegistro){
            setScreen(new RegisterScreen(game,nakamaSessionManager));
        }
    }

    /**/
}
