package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.MyWidgets.MyTextButton;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class MenuScreen extends MyScreen {

    Table table = new Table();
    NakamaSessionManager nakamaSessionManager;
    SpriteBatch spriteBatch = new SpriteBatch();
    Texture fondo =new Texture("backgound/battleback9.png");



    public MenuScreen(JadventureMain game, NakamaSessionManager nakamaSessionManager) {
        super(game);
        this.nakamaSessionManager = nakamaSessionManager;
    }

    public MenuScreen(JadventureMain game) {
        super(game);
    }

    @Override
    public void show(){

        MyTextButton unirseALaPartida = new MyTextButton("Unirse a la partida");
        MyTextButton logOut = new MyTextButton("Cerrar Sesion");

        table.add(unirseALaPartida);
        table.row().spaceTop(20);
        table.add(logOut);
        unirseALaPartida.onClick(() -> {
            nakamaSessionManager.unirsePartida();
            nakamaSessionManager.unirseChat();
            setScreen(new GameScreen(game,nakamaSessionManager));
        });

        logOut.onClick(() -> System.exit(1));

        stage.addActor(table);

    }


    @Override
    public void render(float delta) {
        super.render(delta);
        table.setPosition(320,240);


        Gdx.gl.glClearColor(0.5f, 0.7f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(fondo, 0, 0, 640, 480);
        table.draw(spriteBatch,1f);
        spriteBatch.end();
    }
}