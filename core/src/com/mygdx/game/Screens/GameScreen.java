package com.mygdx.game.Screens;

import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.MyWidgets.MyWorld;
import com.mygdx.game.JadventureMain;

public class GameScreen extends MyScreen {
    MyWorld myWorld;

    public GameScreen(JadventureMain game) {
        super(game);

    }

    @Override
    public void show () {
        myWorld = new MyWorld(camera);
        stage.addActor(myWorld);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (myWorld.personaje != null){
            camera.position.set(myWorld.personaje.getX(),myWorld.personaje.getY(),0);
            camera.update();
            myWorld.personaje.manejarTeclas();
        }
    }
}
