package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyActor;
import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class LoadScreen extends MyScreen {

    SpriteBatch spriteBatch;
    NakamaSessionManager nakamaSessionManager = new NakamaSessionManager();

    MyActor logo;
    public LoadScreen(JadventureMain game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        spriteBatch = new SpriteBatch();
//        logo = new MyActor();
//        logo.currentAnimation = Assets.getAnimation("direcIzquierda",0.1f, Animation.PlayMode.LOOP);
//        logo.setSize(200, 100);
//        logo.setPosition(100,100);
//        stage.addActor(logo);
//
//        logo.addAction(
//                Actions.sequence(
//                        Actions.parallel(
//                                Actions.rotateBy(360*3, 0.9f, Interpolation.fastSlow),
//                                Actions.moveTo(200, 200, 1, Interpolation.fastSlow)
//                        ),
//                        Actions.parallel(
//                                Actions.rotateBy(360*3, 0.9f, Interpolation.fastSlow),
//                                Actions.moveTo(200, 200, 1, Interpolation.fastSlow)
//                        )
//                )
//        );
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
