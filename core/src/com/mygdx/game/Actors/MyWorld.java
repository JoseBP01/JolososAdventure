package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Assets;
import com.mygdx.game.Map;
import com.mygdx.game.MyWidgets.MyActor;
import com.mygdx.game.MyWidgets.MyDialog;
import com.mygdx.game.MyWidgets.MyStage;
import com.mygdx.game.NakamaController.NakamaSessionManager;
import com.mygdx.game.NakamaController.NakamaStorage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyWorld extends Group {

    public Map map;
    OrthographicCamera camera;

    public static final short TIERRA_BIT = 1;
    public static final short CASA_BIT = 2;
    public static final short NPC_BIT = 4;
    public static final short PERSONAJE_BIT = 8;
    public static final short PUERTA_BIT = 16;
    public static final short ENEMIGO_BIT = 32;
    public static final short MONEDA_BIT = 64;

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
    public List<Body> monedasContacto = new ArrayList<>();
    public List<Body> enemigosContacto = new ArrayList<>();
    public List<PersonajeOnline> personajesOnline=new ArrayList<>();
    MyStage myStage;

    Puerta puertaCambio;
    MyDialog dialog;
    NakamaSessionManager nakamaSessionManager;
    NakamaStorage nakamaStorage;

    public World world;
    boolean reloadMap;
    public boolean addNuevoPOnline=false;
    boolean limpiarMoneda;
    public static float time;
    MyDialog chat;
    MyDialog hacerObjDialog;
    boolean hacerObjeto = false;


    public String idPOnline;
    public float xPOnline;
    public float yPOnline;
    public boolean quitarPOnline;
    private Table table;


    public MyWorld(OrthographicCamera camera, NakamaSessionManager nakamaSessionManager, MyStage stage) {
        this.camera = camera;
        this.nakamaSessionManager = nakamaSessionManager;
        this.myStage = stage;
        nakamaStorage = new NakamaStorage(nakamaSessionManager);
        debugRenderer = new Box2DDebugRenderer(false, false, false, false, false, false);

        chat = new MyDialog("Chat",Assets.uiSkin,50,50);
        chat.setPosition(camera.viewportWidth,camera.viewportHeight);
        initWorld("maps/mapa.tmx");
        this.nakamaSessionManager.setMyWorld(this);
        nakamaSessionManager.nakamaStorage.getPosicionJugador();
        nakamaStorage.comprarObjeto("objetoPrueba");
    }

    void initWorld(String mapName) {
        world = new World(new Vector2(0, -80), true);
        chat.show(myStage);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
                switch (cDef) {
                    case PERSONAJE_BIT | NPC_BIT:

                        addActor(dialog = new MyDialog("Herrera", "Quieres fabricar tu propio objeto", "Yes", true, "No", false, camera.viewportWidth, 200) {
                            public void result(Object obj) {
                                System.out.println("result " + obj);
                                System.out.println(obj);

                                if (obj.equals(true)) {
                                    System.out.println("verdadero");
//                                    nakamaStorage.crearObjeto("objetoPrueba",100f, "el melhor objeto do mondo");
//                                    nakamaStorage.crearObjeto("objetoPrueba2",200f, "el melhor objeto do mondo2");
//                                    nakamaStorage.crearObjeto("objetoPrueba3",300f, "el melhor objeto do mondo3");
//                                    nakamaSessionManager.enviarMensaje();
                                    showObjetos(nakamaStorage.getObjetosTienda());

;                                } else{
                                    System.out.println("falso");
                                }
                            }
                        });

//                        dialog.show(getStage());

                        System.out.println(camera.position.x);
                        System.out.println("w: " + camera.viewportWidth);
//                        dialog.setPosition(camera.viewportWidth, 0);
                        break;

                    case PUERTA_BIT | PERSONAJE_BIT:

                        if (fixB.getFilterData().categoryBits == PUERTA_BIT) {
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
                        if (fixB.getFilterData().categoryBits == MONEDA_BIT) {
                            System.out.println("BBBBBBBBBBAAH");
                            monedasContacto.add(fixB.getBody());
                            limpiarMoneda = true;
                        } else {
                            System.out.println("AAH");
                        }

                        break;

                    case PERSONAJE_BIT | ENEMIGO_BIT:
                        if (fixB.getFilterData().categoryBits == ENEMIGO_BIT) {
                            enemigosContacto.add(fixA.getBody());
                        } else {
                            System.out.println("AAH");
                        }
                        break;
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
                        if (hacerObjeto){
                            removeActor(hacerObjDialog);
                        }

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

        map = new Map(camera, mapName);
        map.loadObjects(this);
        addActor(map);
        camera.position.set(personaje.getX(), personaje.getY(), 0);
        if (personaje != null){
            personaje.setNs(nakamaStorage);
            personaje.setMyWorld(this);
        }

    }

    public void showObjetos(List<Objeto> lista) {
        table = new Table();
        List<Objeto> objetos = new ArrayList<>();
        for (Objeto objeto: lista){

            Label nombre = new Label("Nombre:", Assets.uiSkin);
            Label nombreValue = new Label(objeto.getNombre(), Assets.uiSkin);
            Label precio = new Label("Precio:", Assets.uiSkin);
            Label precioValue = new Label(String.valueOf(objeto.getPrecio()), Assets.uiSkin);
            Label descripcion = new Label("Descripcion:", Assets.uiSkin);
            Label descripcionValue = new Label(objeto.getDescripcion(), Assets.uiSkin);

            table.add(nombre);
            table.add(nombreValue);
            table.row();
            table.add(precio);
            table.add(precioValue).width(100);
            table.row();
            table.add(descripcion);
            table.add(descripcionValue).width(100);
            table.row();
        }
        addActor(table);
        table.setPosition(personaje.getX(), personaje.getY());

    }

    public void hideObjetos(){
        removeActor(table);
    }

    private void  clearObjects(List<MyActor> actorlist){
        for(MyActor actor:actorlist) {
            System.out.println("ELIMINANDO ACTOR.....");
            world.destroyBody(actor.body);
            actor.clearActions();
            removeActor(actor);
        }
        actorlist.clear();
    }

    private void clearMyWorld() {
        //Lista de listas
        List[] colecciones = {arboles, npcs, aguaList, sillasList, puertas,monedas/*,personajesOnline*/};

        //Borra las listas
        for(List coleccion:colecciones){
            clearObjects(coleccion);
        }

        world.destroyBody(personaje.body);

        removeActor(personaje);
        removeActor(map);
        System.out.println("CARGANDO " + puertaCambio.map + " : " + puertaCambio.name);
        initWorld(puertaCambio.map);
    }

    public void addPersonaje(Fixture fixture, MapObject mapObject) {
        System.out.println("AÑADIENDO PERASONALEEEEE ...");
        addActor(personaje = new Personaje(fixture, mapObject));
    }

    public void addNpc(Fixture fixture, MapObject mapObject) {
        Npc npc = new Npc(fixture, mapObject);
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

    public void addEnemigo(Fixture fixture, MapObject mapObject) {
        Enemigo enemigoBase = new Enemigo(fixture, mapObject);
        enemigos.add(enemigoBase);
        addActor(enemigoBase);
    }

    public void addSillas(Fixture fixture) {
        Sillas sillas = new Sillas(fixture);
        sillasList.add(sillas);
        addActor(sillas);
    }

    public void addPuerta(Fixture fixture, String res, String name) {
        System.out.println("Añadiendo puerta " + name + " hacia " + res);
        Puerta puerta = new Puerta(fixture, name);
        puerta.map = res;
        puertas.add(puerta);
        puertas.forEach(System.out::println);
        addActor(puerta);
    }

    public void addArbol(Fixture fixture) {
        Arbol arbol = new Arbol(fixture);
        arboles.add(arbol);
        addActor(arbol);
    }

    public void addAgua(Fixture fixture) {
        Agua agua = new Agua(fixture);
        aguaList.add(agua);
        addActor(agua);
    }

    public void addPared(Fixture fixture) {
        Pared pared = new Pared(fixture);
        paredes.add(pared);
        addActor(pared);
    }

    public void addMoneda(Fixture fixture, MapObject mapObject) {
        Moneda moneda = new Moneda(fixture, mapObject);
        monedas.add(moneda);
        addActor(moneda);
    }

    public void addPersonajeOnline(String id, float x, float y){
        PersonajeOnline personajeOnline = new PersonajeOnline(id,x,y,world);
        personajesOnline.add(personajeOnline);
        addActor(personajeOnline);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta;
        chat.update(camera);
        world.step(delta, 6, 2);

        if (table != null){
            table.setPosition(personaje.getX(), personaje.getY());
        }

        if (reloadMap) {
            clearMyWorld();
            reloadMap = false;
        }

        if (addNuevoPOnline && idPOnline != null){
            addPersonajeOnline(idPOnline,xPOnline,yPOnline);
            addNuevoPOnline = false;
        }

        if (quitarPOnline && nakamaSessionManager.personajeAborrar != null){
            personajeOBorrar(nakamaSessionManager.personajeAborrar);
            quitarPOnline = false;
        }

        if (dialog != null) {
            dialog.update(camera);
        }

        if (hacerObjDialog != null){
            hacerObjDialog.update(camera);
        }

        for (Body body : monedasContacto){
            for (Iterator<Moneda> iterator = monedas.iterator(); iterator.hasNext(); ) {
                Moneda moneda = iterator.next();
                if (moneda.equals(body)) {
                    world.destroyBody(body);
                    iterator.remove();
                    removeActor(moneda);
                    break;
                }
            }
        }
        monedasContacto.clear();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        debugRenderer.render(world, camera.combined);
    }

    public void cargarNuevoPOnline(String id, float x, float y){
        idPOnline = id;
        xPOnline = x;
        yPOnline = y;
    }

    public void personajeOBorrar(String userId) {
        for (PersonajeOnline p : personajesOnline){
            if (p.getId().equals(userId)){
                removeActor(p);
                world.destroyBody(p.body);
//                personajesOnline.remove(p);
            }
        }
    }
}

