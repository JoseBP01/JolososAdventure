package com.mygdx.game.Actors;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.MyWidgets.MyActor;

public class Pozo extends MyActor {

    public Pozo(Fixture fixture, MapObject mapObject) {
        super(fixture);
        fixture.getFilterData().categoryBits= MyWorld.POZO_BIT;
        fixture.getFilterData().maskBits = MyWorld.PERSONAJE_BIT;
    }

    @Override
    public void define(Filter filter) {

    }

    @Override
    public void defineBody() {
        body.setType(BodyDef.BodyType.StaticBody);
    }

}
