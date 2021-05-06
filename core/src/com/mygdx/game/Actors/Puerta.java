package com.mygdx.game.Actors;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.MyWidgets.MyActor;

public class Puerta extends MyActor {

    public String map;
    public String name;

    public Puerta(Fixture fixture, String name) {
        super(fixture);
        this.name = name;
        fixture.getFilterData().categoryBits= MyWorld.PUERTA_BIT;
        fixture.getFilterData().maskBits = MyWorld.PERSONAJE_BIT;
    }

    @Override
    public void define(Fixture fixture) {
        super.define(fixture);
        body.setType(BodyDef.BodyType.StaticBody);
    }

    @Override
    public String toString() {
        return "Puerta{" +
                "map='" + map + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
