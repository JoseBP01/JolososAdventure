package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
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
    SpriteBatch spriteBatch = new SpriteBatch();
    Table table = new Table();
    Texture fondo =new Texture("backgound/battleback8.png");


    public RegisterScreen(JadventureMain game , NakamaSessionManager nakamaSessionManager) {
        super(game);
        this.nakamaSessionManager = nakamaSessionManager;
    }

    @Override
    public void show() {
        super.show();

        Label email = new Label("Email:", Assets.uiSkin);
        TextField emailText = new TextField("",Assets.uiSkin);
        Label username = new Label("Username:", Assets.uiSkin);
        TextField usernameText = new TextField("",Assets.uiSkin);

        Label password = new Label("Password:",Assets.uiSkin);
        TextField passwordText = new TextField("",Assets.uiSkin);

        TextButton button = new TextButton("Registrarse",Assets.uiSkin);
        TextButton buttonVolver = new TextButton("Volver",Assets.uiSkin);

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

        buttonVolver.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                    setScreen(new LoginScreen(game,nakamaSessionManager));
                }
                return false;
            }
        });

        table.add(email);
        table.add(emailText);
        table.row();
        table.add(username).spaceTop(10);
        table.add(usernameText).spaceTop(10);
        table.row();
        table.add(password).spaceTop(10);
        table.add(passwordText).spaceTop(10);
        passwordText.setPasswordMode(true);
        table.row();
        table.add(button).colspan(2).spaceTop(10);
        table.row();
        table.add(buttonVolver).colspan(2).spaceTop(10);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (registrado){
            setScreen(new MenuScreen(game,nakamaSessionManager));
        }
        table.setPosition(320,240);


        Gdx.gl.glClearColor(0.5f, 0.7f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(fondo, 0, 0, 640, 480);
        table.draw(spriteBatch,1f);
        spriteBatch.end();
    }
}
