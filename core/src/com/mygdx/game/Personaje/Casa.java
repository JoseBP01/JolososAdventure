package com.mygdx.game.Personaje;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.Base.MyActor;

public class Casa extends MyActor {


    public Casa(Fixture fixture) {
        super(fixture);
    }

    @Override
    public void define(Filter filter) {

    }

    @Override
    public void define(Fixture fixture) {
        super.define(fixture);
        bodyDef.type = BodyDef.BodyType.StaticBody;
    }
}
