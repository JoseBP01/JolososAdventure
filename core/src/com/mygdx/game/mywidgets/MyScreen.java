package com.mygdx.game.mywidgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Assets;
import com.mygdx.game.Config;
import com.mygdx.game.JadventureMain;

public class MyScreen implements Screen {
    public Assets assets;
    public Stage stage;
    public OrthographicCamera camera;
    public Viewport viewport;

    public final JadventureMain game;

    public MyScreen(JadventureMain game){
        this.game = game;
        this.assets = game.assets;
    }

    public void setScreen(Screen screen){ game.setScreen(screen); }
    @Override public void show() {
        Gdx.input.setInputProcessor(stage = new Stage());

        camera = new OrthographicCamera();
        camera.position.set(Config.WIDTH/2, Config.HEIGHT/2, 0);
        viewport = new FitViewport(Config.WIDTH, Config.HEIGHT, camera);
        viewport.apply();
    }
    @Override public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();
    }
    @Override public void resize(int width, int height) {
        viewport.update(width ,height);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
