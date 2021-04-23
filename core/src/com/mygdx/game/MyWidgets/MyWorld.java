package com.mygdx.game.MyWidgets;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.Actors.*;
import com.mygdx.game.Map;

import java.util.ArrayList;
import java.util.List;

public class MyWorld extends Group {

    private Map map;
    OrthographicCamera camera;

    public static final short TIERRA_BIT = 1;
    public static final short CASA_BIT = 2;
    public static final short NPC_BIT = 4;
    public static final short PERSONAJE_BIT = 8;
    public static final short PUERTA_BIT = 16;
    public static final short ENEMIGO_BIT = 10;
    public static final short MONEDA_BIT = 6;

    private final Box2DDebugRenderer debugRenderer;

    public Personaje personaje;
    public List<Npc> npcs = new ArrayList<>();
    public List<Casa> casas = new ArrayList<>();
    public List<Enemigo> enemigos = new ArrayList<>();
    public List<Arbol> arboles = new ArrayList<>();
    public List<Puerta> puertas = new ArrayList<>();
    public List<Agua> aguaList = new ArrayList<>();
    public List<Pared> paredes = new ArrayList<>();
    public List<Sillas> sillasList = new ArrayList<>();
    public List<Moneda> monedas = new ArrayList<>();

    Puerta puertaCambio;
    MyDialog dialog;

    public World world;
    boolean reloadMap;
    public static float time;
    private Moneda moneda;

    public MyWorld(OrthographicCamera camera) {
        this.camera = camera;
        debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

        initWorld("maps/mapa.tmx");

    }

    void initWorld (String mapName){
        world = new World(new Vector2(0, -80), true);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
                switch (cDef) {
                    case PERSONAJE_BIT | NPC_BIT:


                        addActor(dialog = new MyDialog("Warning", "TextoPrueba", "Yes", true, "No", false, camera.viewportWidth, 200) {
                            public void result(Object obj) {
                                System.out.println("result "+obj);
                                System.out.println(obj);

                                if (obj.equals(true)){
                                    System.out.println("verdadero");
                                    
                                }else System.out.println("falso");
                            }
                        });
//                        dialog.show(getStage());

                        System.out.println(camera.position.x);
                        System.out.println("w: "+camera.viewportWidth);
//                        dialog.setPosition(camera.viewportWidth, 0);
                        break;

                    case PUERTA_BIT | PERSONAJE_BIT:

                        if (fixB.getFilterData().categoryBits == PUERTA_BIT){
                            puertaCambio = (Puerta) fixB.getBody().getUserData();
                            reloadMap = true;
                            System.out.println("COLISION CON PUERTA " + puertaCambio);
                        } else {
                            puertaCambio = (Puerta) fixA.getBody().getUserData();
                            reloadMap = true;
                            System.out.println("COLISION CON PUERTA " + puertaCambio);
                        }
                        break;

                    case MONEDA_BIT | PERSONAJE_BIT:


                    if (fixB.getFilterData().categoryBits == MONEDA_BIT){
                        moneda = (Moneda) fixB.getBody().getUserData();
//                        world.destroyBody(moneda.body);
//                        world.

                    } else {
                        moneda = (Moneda) fixA.getBody().getUserData();
                            world.destroyBody(moneda.body);
                            removeActor(moneda);

                    }

                        break;

//                    case PERSONAJE_BIT | ENEMIGO_BIT:
//
//                        break;
                }

            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
                switch (cDef) {
                    case PERSONAJE_BIT | NPC_BIT:
                        removeActor(dialog);
//                        dialog.hide();


                        break;
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });

        map = new Map(camera,mapName);
        map.loadObjects(this);
        addActor(map);
        camera.position.set(personaje.getX(),personaje.getY(),0);

    }

    void clearMyWorld() {

        for (Arbol arbol:arboles){
            world.destroyBody(arbol.body);
            arbol.clearActions();
            removeActor(arbol);
        }
        arboles.clear();

        for (Npc npc: npcs){
            world.destroyBody(npc.body);
            npc.clearActions();
            removeActor(npc);
        }
        npcs.clear();

        for (Agua agua:aguaList){
            world.destroyBody(agua.body);
            agua.clearActions();
            removeActor(agua);
        }
        aguaList.clear();

        for (Pared pared:paredes){
            world.destroyBody(pared.body);
            pared.clearActions();
            removeActor(pared);
        }
        paredes.clear();

        for (Sillas sillas: sillasList){
            world.destroyBody(sillas.body);
            sillas.clearActions();
            removeActor(sillas);
        }
        sillasList.clear();

        for (Puerta puerta : puertas){
            world.destroyBody(puerta.body);
            puerta.clearActions();
            removeActor(puerta);
        }
        puertas.clear();

        world.destroyBody(personaje.body);

        removeActor(personaje);
        removeActor(map);
        System.out.println("CARGANDO " + puertaCambio.map + " : " + puertaCambio.name);
        initWorld(puertaCambio.map);
    }

    public void addPersonaje(Fixture fixture, MapObject mapObject) {
        System.out.println("AÑADIENDO PERASONALEEEEE ...");
        addActor(personaje = new Personaje(fixture,mapObject));
    }

    public void addNpc(Fixture fixture, MapObject mapObject) {
        Npc npc = new Npc(fixture,mapObject);
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
        Enemigo enemigoBase = new Enemigo(fixture);
        enemigos.add(enemigoBase);
        addActor(enemigoBase);
    }

    public void addSillas(Fixture fixture){
        Sillas sillas = new Sillas(fixture);
        sillasList.add(sillas);
        addActor(sillas);
    }

    public void addPuerta(Fixture fixture, String res, String name){
        System.out.println("Añadiendo puerta " + name + " hacia " + res);
        Puerta puerta = new Puerta(fixture, name);
        puerta.map = res;
        puertas.add(puerta);
        puertas.forEach(System.out::println);
        addActor(puerta);
    }

    public void addArbol(Fixture fixture){
        Arbol arbol = new Arbol(fixture);
        arboles.add(arbol);
        addActor(arbol);
    }

    public void addAgua(Fixture fixture){
        Agua agua = new Agua(fixture);
        aguaList.add(agua);
        addActor(agua);
    }

    public void addPared(Fixture fixture){
        Pared pared = new Pared(fixture);
        paredes.add(pared);
        addActor(pared);
    }

    public void addMoneda(Fixture fixture,MapObject mapObject){
        Moneda moneda = new Moneda(fixture, mapObject );
        monedas.add(moneda);
        addActor(moneda);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta;
        world.step(delta, 6, 2);

        if(reloadMap){
            clearMyWorld();
            reloadMap = false;
        }
        if(dialog != null) {
            dialog.update(camera);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        debugRenderer.render(world, camera.combined);
    }
}

