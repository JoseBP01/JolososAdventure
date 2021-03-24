package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.mywidgets.MyScreen;

public class GameScreen extends MyScreen {
    Personaje personaje;
    HpBarra hpBarra;
    Map map;
    public GameScreen(JadventureMain game) {
        super(game);
    }

    @Override
    public void show () {
        super.show();

        stage.addActor(map = new Map(camera));
        stage.addActor(personaje = new Personaje());
        stage.addActor(hpBarra = new HpBarra());
    }

    @Override
    public void render (float delta) {
        super.render(delta);




        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            personaje.direccion = Personaje.Direccion.Izquerda;
            personaje.setState(Personaje.State.Caminando);
            personaje.moveBy(-personaje.vx, 0);

        }else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            personaje.direccion = Personaje.Direccion.Derecha;
            personaje.setState(Personaje.State.Caminando);
            personaje.moveBy(personaje.vx, 0);
        }else if(Gdx.input.isKeyPressed(Input.Keys.W)){
            personaje.direccion = Personaje.Direccion.Arriba;
            personaje.setState(Personaje.State.Caminando);
            personaje.moveBy(0, personaje.vy);
        }else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            personaje.direccion = Personaje.Direccion.Abajo;
            personaje.setState(Personaje.State.Caminando);
            personaje.moveBy(0, -personaje.vy);
        }else{
            personaje.estado= Personaje.State.Quieto;
        }


    }

}
