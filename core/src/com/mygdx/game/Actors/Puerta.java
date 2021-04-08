package com.mygdx.game.Actors;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.MyWidgets.MyActor;

public class Puerta extends MyActor {

    public Puerta(Fixture fixture) {
        super(fixture);
    }

    @Override
    public void define(Fixture fixture) {
        super.define(fixture);
        body.setType(BodyDef.BodyType.StaticBody);
    }
}
