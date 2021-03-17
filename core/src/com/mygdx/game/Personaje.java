package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

public class Personaje extends Actor {

    float stateTime;
    float vx = 10;

    Personaje(){
        setSize(132,132);
        setOrigin(Align.center);

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mueve();
                return false;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(Assets.caminar.getKeyFrame(stateTime,true), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public void mueve(){
        addAction(
                Actions.sequence(
                        Actions.parallel(
//                            Actions.rotateBy(180, 0.3f),
                                Actions.moveBy(160, 130, 4, Interpolation.elasticOut)
                        )
//                    Actions.scaleBy(2, 2, 1, Interpolation.elasticOut)
                )
        );
    }
}
