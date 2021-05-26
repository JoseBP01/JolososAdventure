package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Actors.MyWorld;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class GameScreen extends MyScreen {
    MyWorld myWorld;
    NakamaSessionManager nakamaSessionManager;
    private boolean pausa= false;
    Table table = new Table();



    public GameScreen(JadventureMain game, NakamaSessionManager nka) {
        super(game);
        nakamaSessionManager = nka;
    }



    @Override
    public void show () {
        myWorld = new MyWorld(camera,nakamaSessionManager,stage);
        stage.addActor(myWorld);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        if (myWorld.personaje != null){
            camera.position.set(myWorld.personaje.getX(),myWorld.personaje.getY(),0);
            camera.update();
            if (!myWorld.escribiendo){
                myWorld.personaje.manejarTeclas();

            }
            if (myWorld.personaje != null){
                nakamaSessionManager.enviarDatosPartida(myWorld.personaje, 1);
            }

            if (myWorld.isGameOver()){
                setScreen(new GameOverScreen(game,nakamaSessionManager));
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                pausa = !pausa;
            }

            if (myWorld.irAlmenuPrincipal){
                setScreen(new MenuScreen(game,nakamaSessionManager));
            }
        }
    }
}
