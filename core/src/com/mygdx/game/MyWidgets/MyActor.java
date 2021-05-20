package com.mygdx.game.MyWidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActor extends Actor {

    public float stateTime;
    public Body body;
    public Animation<TextureRegion> currentAnimation;


    public MyActor(Fixture fixture) {
        this.body = fixture.getBody();
        body.setUserData(this);
        defineBody();
        define(fixture);
    }

    public MyActor() {


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(body.getPosition().x, body.getPosition().y);
        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(currentAnimation != null) {
            Color color = getColor();
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
            batch.draw(currentAnimation.getKeyFrame(getStateTime()), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }


    public void define(Fixture fixture){
        Filter filter = new Filter();
        define(filter);
        fixture.setFilterData(filter);
    }

    public void define(Filter filter){}
    public void defineBody(){}


    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
