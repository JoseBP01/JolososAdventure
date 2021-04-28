package com.mygdx.game.Screens;

import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.MyWidgets.MyWorld;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class GameScreen extends MyScreen {
    MyWorld myWorld;
    NakamaSessionManager nakamaSessionManager;

    public GameScreen(JadventureMain game, NakamaSessionManager nka) {
        super(game);
        nakamaSessionManager = nka;
    }

    @Override
    public void show () {
        myWorld = new MyWorld(camera,nakamaSessionManager);
        stage.addActor(myWorld);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (myWorld.personaje != null){
            camera.position.set(myWorld.personaje.getX(),myWorld.personaje.getY(),0);
            camera.update();
            myWorld.personaje.manejarTeclas();
            if (myWorld.personaje != null){
                nakamaSessionManager.enviarDatosPartida(myWorld.personaje, 1);
            }
        }
    }
}
