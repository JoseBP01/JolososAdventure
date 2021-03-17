package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

public class LoadScreen extends BaseScreen {

    public LoadScreen(JadventureMain si) {
        super(si);
    }

    @Override
    public void render(float delta) {
        if(!assets.update()){
            return;
        }

        setScreen(new GameScreen(game));
    }
}
