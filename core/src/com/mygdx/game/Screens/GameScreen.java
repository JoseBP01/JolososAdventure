package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.Map;
import com.mygdx.game.Personaje.HpBarra;
import com.mygdx.game.Personaje.Personaje;
import com.mygdx.game.Base.MyScreen;

public class GameScreen extends MyScreen {
    Personaje personaje;
    HpBarra hpBarra;
    Map map;
    private World world;
    private SpriteBatch spriteBatch;

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
            personaje.moveBy(0, personaje.getVy());
        }else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            personaje.setDireccion(Personaje.Direccion.Abajo);
            personaje.setState(Personaje.State.Caminando);
            personaje.moveBy(0, -personaje.getVy());
        }else{
            personaje.setState(Personaje.State.Quieto);
        }


    }

}
