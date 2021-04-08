package com.mygdx.game.Base;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.Map;
import com.mygdx.game.Personaje.Casa;
import com.mygdx.game.Personaje.EnemigoBase;
import com.mygdx.game.Personaje.Npc;
import com.mygdx.game.Personaje.Personaje;

import java.util.ArrayList;
import java.util.List;

public class MyWorld extends Group {

    OrthographicCamera camera;

    public static final short TIERRA_BIT = 1;
    public static final short CASA_BIT = 2;
    public static final short NPC_BIT = 4;
    public static final short PERSONAJE_BIT = 8;

    private final Box2DDebugRenderer debugRenderer;

    public Personaje personaje;
    public List<Npc> npcs = new ArrayList<>();
    public List<Casa> casas = new ArrayList<>();
    public List<EnemigoBase> enemigos = new ArrayList<>();

    public World world;

    public MyWorld(OrthographicCamera camera) {
        this.camera = camera;

        world = new World(new Vector2(0, -80), true);
        debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
                switch (cDef) {
                    case PERSONAJE_BIT | TIERRA_BIT:
                        break;
                    case NPC_BIT | TIERRA_BIT:
                        break;
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
        });

        Map map = new Map(camera);
        map.loadObjects(this);
        addActor(map);
    }

    public void addPersonaje(Fixture fixture) {
        addActor(personaje = new Personaje(fixture));
    }

    public void addNpc(Fixture fixture) {
        Npc npc = new Npc(fixture);
        npcs.add(npc);
        addActor(npc);
    }

    public void addCasa(Fixture fixture) {
        Casa casa = new Casa(fixture);
        casas.add(casa);
        addActor(casa);
    }

    public void addTierra(Fixture fixture) {
        fixture.setDensity(0.5f);
        fixture.setFriction(0.1f);
        fixture.setRestitution(0.5f);
    }

    public void addEnemigo(Fixture fixture){
        EnemigoBase enemigoBase = new EnemigoBase(fixture);
        enemigos.add(enemigoBase);
        addActor(enemigoBase);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        world.step(delta, 6, 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        debugRenderer.render(world, camera.combined);
    }

}
