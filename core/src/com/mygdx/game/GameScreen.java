package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends BaseScreen{
    Personaje personaje;
    HpBarra hpBarra;
    Stage stage;

    public OrthographicCamera camera;
    public Viewport viewport;
    public int SCENE_WIDTH = 640;
    public int SCENE_HEIGHT = 480;

    public GameScreen(JadventureMain game) {
        super(game);
    }

    @Override
    public void show () {



        Gdx.input.setInputProcessor(stage = new Stage());

        stage.addActor(personaje = new Personaje());
        stage.addActor(hpBarra = new HpBarra());

        camera = new OrthographicCamera();
        camera.position.set(personaje.getX(), personaje.getY(), 0);
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
        viewport.apply();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 1, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            personaje.estado= Personaje.Estados.Caminando;
            personaje.moveBy(-personaje.vx, 0);

        }else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            personaje.estado= Personaje.Estados.Caminando;
            personaje.moveBy(personaje.vx, 0);
        }else if(Gdx.input.isKeyPressed(Input.Keys.W)){
            personaje.estado= Personaje.Estados.Caminando;
            personaje.moveBy(0, personaje.vy);
        }else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            personaje.estado= Personaje.Estados.Caminando;
            personaje.moveBy(0, -personaje.vy);
        }else personaje.estado= Personaje.Estados.Quieto;

        stage.getBatch().setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width,height);

        viewport.update(width ,height);
    }
}
