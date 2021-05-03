package com.mygdx.game.Actors;

import com.badlogic.gdx.physics.box2d.World;

public class PersonajeOnline {

    private String id;
    private float x;
    private float y;

    public PersonajeOnline(String id, float x, float y, World world) {
        this.id = id;
        this.x = x;
        this.y = y;
    }


}
