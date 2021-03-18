package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

public class HpBarra extends Actor {
    public int vidas;
    private int estadoBarra;

    HpBarra(){
        setSize(150,150);
        setOrigin(Align.top);
        vidas = 4;
        estadoBarra = 0;

    }

    public void golpe(){
        vidas--;
        estadoBarra++;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//       batch.draw(Assets.barraHp.getKeyFrame(estadoBarra),getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
