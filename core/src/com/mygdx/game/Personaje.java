package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class Personaje extends Actor {

    public enum Estados{
        Quieto,
        Caminando,
        ;
    }

    float stateTime;
    float vx = 5;
    float vy = 5;
    Estados estado;
    int vidas = 4;


    Personaje(){
        setSize(40,40);
        setOrigin(Align.center);
        estado = Estados.Quieto;

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (estado == Estados.Caminando){
            batch.draw(Assets.caminar.getKeyFrame(stateTime,true), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }else if (estado == Estados.Quieto){
            batch.draw(Assets.quieto.getKeyFrame(3), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    public void daño(){
        vidas--;
    }
}
