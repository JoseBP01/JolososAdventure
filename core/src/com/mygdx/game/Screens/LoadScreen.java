package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class LoadScreen extends MyScreen {

    SpriteBatch spriteBatch;
    NakamaSessionManager nakamaSessionManager = new NakamaSessionManager();

    public LoadScreen(JadventureMain game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        spriteBatch = new SpriteBatch();
    }

    Pixmap cursorPixmap = new Pixmap(Gdx.files.internal("core/assets/cursor2.png"));
    Music aMusic = Gdx.audio.newMusic((Gdx.files.internal("core/assets/musica.mp3")));



    @Override
    public void render(float delta) {
        if(!assets.update()){
            return;
        }

        Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursorPixmap,0,0));
        cursorPixmap.dispose();

        aMusic.play();




        setScreen(new LoginScreen(game,nakamaSessionManager));
//        setScreen(new MenuScreen(game));
//        spriteBatch.begin();
//
//        spriteBatch.draw(background, 0, 0, 640, 480);
//        spriteBatch.end();
    }
}
