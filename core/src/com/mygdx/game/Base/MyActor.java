package com.mygdx.game.Base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActor extends Actor {

    private float stateTime;

    public MyActor(World world, float x, float y) {

    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        setPosition(body.getPosition().x, body.getPosition().y);
        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        batch.draw(currentAnimation.getKeyFrame(stateTime), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
