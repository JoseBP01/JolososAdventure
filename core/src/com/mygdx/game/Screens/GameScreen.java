package com.mygdx.game.Screens;

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
        world = new World(new Vector2(0,0), true);

        stage.addActor(personaje = new Personaje(world));
        stage.addActor(hpBarra = new HpBarra());
    }

    @Override
    public void render (float delta) {
        super.render(delta);

        spriteBatch = new SpriteBatch();


        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            personaje.setDireccion(Personaje.Direccion.Izquerda);
            personaje.setState(Personaje.State.Caminando);
            personaje.moveBy(-personaje.getVx(), 0);

        }else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            personaje.setDireccion(Personaje.Direccion.Derecha);
            personaje.setState(Personaje.State.Caminando);
            personaje.moveBy(personaje.getVx(), 0);

        }else if(Gdx.input.isKeyPressed(Input.Keys.W)){
            personaje.setDireccion(Personaje.Direccion.Arriba);
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
