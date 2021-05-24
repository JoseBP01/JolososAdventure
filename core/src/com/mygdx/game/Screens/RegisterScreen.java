package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.Assets;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class RegisterScreen extends MyScreen {

    NakamaSessionManager nakamaSessionManager;
    private boolean registrado;

    public RegisterScreen(JadventureMain game , NakamaSessionManager nakamaSessionManager) {
        super(game);
        this.nakamaSessionManager = nakamaSessionManager;
    }

    @Override
    public void show() {
        super.show();

        Table table = new Table();
        table.setDebug(true);
        Label email = new Label("Email:", Assets.uiSkin);
        TextField emailText = new TextField("",Assets.uiSkin);
        Label username = new Label("Username:", Assets.uiSkin);
        TextField usernameText = new TextField("",Assets.uiSkin);

        Label password = new Label("Password:",Assets.uiSkin);
        TextField passwordText = new TextField("",Assets.uiSkin);
        passwordText.setPasswordMode(true);
        TextButton button = new TextButton("Registrarse",Assets.uiSkin);

        button.addListener(event -> {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                if (!emailText.getText().equals("") && !passwordText.getText().equals("") && !usernameText.getText().equals("")){
                    nakamaSessionManager.iniciarSesion(emailText.getText(), passwordText.getText(), usernameText.getText(), new NakamaSessionManager.IniciarSesionCallback() {
                        @Override
                        public void loginOk() {
                            System.out.println("se ha logueado " + NakamaSessionManager.account.getEmail());
                            registrado = true;
                        }

                        @Override
                        public void loginError(String error) {
                            System.out.println("Error " + error);
                        }
                    });
                }else {
                    System.out.println("Campo email, username o password no rellenado");
                }
            }
            return false;
        });

        table.add(email);
        table.add(emailText);
        table.row();
        table.add(username).spaceTop(10);
        table.add(usernameText).spaceTop(10);
        table.row();
        table.add(password).spaceTop(10);
        table.add(passwordText).spaceTop(10);
        table.row();
        table.add(button).colspan(2).spaceTop(10);
        table.row();
        table.setFillParent(true);
        stage.addActor(table);
        stage.act();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (registrado){
            setScreen(new MenuScreen(game,nakamaSessionManager));
        }
    }
}
