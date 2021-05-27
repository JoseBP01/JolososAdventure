package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.Assets;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class IpScreen extends MyScreen {
    SpriteBatch spriteBatch = new SpriteBatch();

    NakamaSessionManager nakamaSessionManager;
    Label texto = new Label("Introduzca la ip del servidor", Assets.uiSkin);
    TextField ip =  new TextField("",Assets.uiSkin);
    TextButton boton = new TextButton("Siguiente",Assets.uiSkin);
    Texture fondo =new Texture("backgound/battleback1.png");
    Table tabla = new Table(Assets.uiSkin);

    public IpScreen(JadventureMain game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        tabla.add(texto).colspan(3);
        tabla.row().spaceTop(20);
        tabla.add(ip);
        tabla.row().spaceTop(20);
        tabla.add(boton);
        tabla.setPosition(320,240);

        stage.addActor(tabla);

        boton.addListener(event -> {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){

                if (!ip.getText().equals("")){
                    nakamaSessionManager = new NakamaSessionManager(ip.getText());
                    setScreen(new LoginScreen(game,nakamaSessionManager));
                }
            }
            return false;
        });


    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0f, 0f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(fondo, 0, 0, 640, 480);
        tabla.draw(spriteBatch,1f);
        spriteBatch.end();

    }
}
