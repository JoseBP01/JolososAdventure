package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Actors.Npc;
import com.mygdx.game.Actors.Personaje;

public class JContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(a.getFilterData().categoryBits == 2){
            Npc npc = (Npc) a.getUserData();
        }

        if (b.getFilterData().categoryBits ==1){
            Personaje p = (Personaje) b.getUserData();
        }

        System.out.println("contacto");

        if (Gdx.input.isKeyPressed(Input.Keys.E)){
            
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
