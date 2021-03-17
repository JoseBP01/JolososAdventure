package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends BaseScreen{
    Personaje personaje;
    Stage stage;

    public OrthographicCamera camera;
    public Viewport viewport;
    public int SCENE_WIDTH = 384;
    public int SCENE_HEIGHT = 256;

    public GameScreen(JadventureMain game) {
        super(game);
    }

    @Override
    public void show () {
        camera = new OrthographicCamera();
        camera.position.set(SCENE_WIDTH/2, SCENE_HEIGHT/2, 0);
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
        viewport.apply();

        Gdx.input.setInputProcessor(stage = new Stage());

        stage.addActor(personaje = new Personaje());
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 1, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            personaje.moveBy(-personaje.vx, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            personaje.moveBy(personaje.vx, 0);
        }

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
