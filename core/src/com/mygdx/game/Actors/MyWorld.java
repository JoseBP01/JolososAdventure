package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.Actors.UtilidadesPersonaje.HpBarra;
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
    public static final short POZO_BIT = 128;

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
    public List<Pozo> pozos = new ArrayList<>();
    public Catedral catedral;
    public HpBarra barraVida;
    public com.badlogic.gdx.scenes.scene2d.ui.List<String> mensajesOnline = new com.badlogic.gdx.scenes.scene2d.ui.List<>(Assets.uiSkin);
    MyStage myStage;

    Puerta puertaCambio;
    MyDialog dialog,dialogoPozo;
    NakamaSessionManager nakamaSessionManager;
    NakamaStorage nakamaStorage;

    public World world;
    boolean reloadMap;
    public boolean addNuevoPOnline=false;
    boolean limpiarMoneda;
    public static float time;
    MyDialog hacerObjDialog;
    public Table chat;
    public TextField chatInput;
    public boolean escribiendo = false;
    Pursue<Vector2> pursue;

    public String idPOnline;
    public float xPOnline;
    public float yPOnline;
    public boolean quitarPOnline;

    private Table table;
    public ScrollPane sP = new ScrollPane(mensajesOnline,Assets.uiSkin);
    private boolean gameOver;
    public boolean irAlmenuPrincipal;
    public boolean pausaMenu;

    public MyWorld(OrthographicCamera camera, NakamaSessionManager nakamaSessionManager, MyStage stage) {
        this.camera = camera;
        this.nakamaSessionManager = nakamaSessionManager;
        this.myStage = stage;
        gameOver = false;
        nakamaStorage = new NakamaStorage(nakamaSessionManager);
        debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
       crearChat();

        initWorld("maps/exterior1.tmx");
        this.nakamaSessionManager.setMyWorld(this);
        nakamaSessionManager.nakamaStorage.getPosicionJugador();
        nakamaStorage.comprarObjeto("objetoPrueba");
    }

    private void crearChat() {
        chat = new Table();
        chat.setFillParent(true);
        chatInput = new TextField("",Assets.uiSkin);
        chat.add(new Label("Chat",Assets.uiSkin)).colspan(2).center();
        chat.row();
        sP.setScrollbarsVisible(false);
        sP.setScrollY(sP.getMaxY());
        chat.add(sP).height(100).expandX().width(150).center();
        chat.row();
        chat.add(chatInput).bottom().colspan(2);

        chat.setSkin(Assets.uiSkin);
//        chat.setDebug(true);

        chatInput.addCaptureListener(event -> {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                escribiendo = true;
                chatInput.setDisabled(false);

            }else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                escribiendo = false;
                chatInput.setText("");
                chatInput.setDisabled(true);
            }
            return false;
        });

        chatInput.addListener(event -> {

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && chatInput.getText() != null){
                nakamaSessionManager.enviarMensaje(chatInput.getText());
                System.out.println("mensaje: "+chatInput.getText()+" enviado.");
            }
            return false;
        });
    }

    void initWorld(String mapName) {
        world = new World(new Vector2(0, -80), true);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
                switch (cDef) {
                    case PERSONAJE_BIT | NPC_BIT:

                        addActor(dialog = new MyDialog("Mercader", "Quieres ver los articulos de la tienda?", "Yes", true, "No", false, camera.viewportWidth, 200) {
                            public void result(Object obj) {
                                System.out.println("result " + obj);
                                System.out.println(obj);

                                if (obj.equals(true)) {
                                    System.out.println("verdadero");
//                                    nakamaStorage.crearObjeto("objetoPrueba",100f, "el melhor objeto do mondo");
//                                    nakamaStorage.crearObjeto("objetoPrueba2",200f, "el melhor objeto do mondo2");
//                                    nakamaStorage.crearObjeto("objetoPrueba3",300f, "el melhor objeto do mondo3");
                                    showObjetos(nakamaStorage.getObjetosTienda());

                                } else{
                                    System.out.println("falso");
                                }
                            }
                        });
                        break;

                    case PERSONAJE_BIT | POZO_BIT:
                        addActor(dialog = new MyDialog("Pozo", "Quieres Beber del pozo?", "Yes", true, "No", false, camera.viewportWidth, 200) {
                            public void result(Object obj) {
                                System.out.println("result " + obj);
                                System.out.println(obj);

                                if (obj.equals(true)) {
                                    System.out.println("*beber fuente*");


                                } else{
                                    System.out.println("falso");
                                }
                            }
                        });
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
                            System.out.println("coger moneda");
                            monedasContacto.add(fixB.getBody());
                            limpiarMoneda = true;
                        } else {
                            System.out.println("AAH");
                        }

                        break;

                    case PERSONAJE_BIT | ENEMIGO_BIT:
                        Enemigo enemigo;
                        if (fixB.getFilterData().categoryBits == ENEMIGO_BIT) {
                            enemigo= (Enemigo) fixB.getBody().getUserData();
                            enemigo.setState(Enemigo.State.Ataque);
                            if (personaje.getState() != Personaje.State.Ataque){
                                if (personaje.getVidas() == 1){
                                    barraVida.setEstadoBarra(personaje.getVidas());
                                    gameOver = true;
                                }else {
                                    personaje.daño_recivido();
                                    barraVida.setEstadoBarra(personaje.getVidas());
                                }
                            }
                            System.out.println("Enemigo colisionado " + enemigo);
                        } else {
                            enemigo =(Enemigo) fixA.getBody().getUserData();
                            enemigo.setState(Enemigo.State.Ataque);
                            System.out.println("Enemigo colisionado " + enemigo);
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
                        removeActor(table);
                        break;

                    case PERSONAJE_BIT | POZO_BIT:
                        removeActor(dialogoPozo);
                        removeActor(table);
                        break;

                    case PERSONAJE_BIT | ENEMIGO_BIT:
                        Enemigo enemigo;
                        if (fixB.getFilterData().categoryBits == ENEMIGO_BIT) {
                            enemigo= (Enemigo) fixB.getBody().getUserData();
                            enemigo.setState(Enemigo.State.Caminando);
                        } else {
                            enemigo =(Enemigo) fixA.getBody().getUserData();
                            enemigo.setState(Enemigo.State.Caminando);
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
        addActor(chat);

        if (personaje != null){
            barraVida = new HpBarra(world,personaje);
            addActor(barraVida);
        }

        camera.position.set(personaje.getX(), personaje.getY(), 0);
        if (personaje != null){
            personaje.setNs(nakamaStorage);
            personaje.setMyWorld(this);
        }

    }


    public void showObjetos(List<Objeto> lista) {


        table = new Table(Assets.uiSkin);
        table.setFillParent(true);
        table.setDebug(true);
        table.setBackground("white");

        for (Objeto objeto: lista){
            Image image = new Image(new Texture("objeto_espada_standard.png"));
            Label nombre = new Label("Nombre:", Assets.uiSkin);
            Label nombreValue = new Label(objeto.getNombre(), Assets.uiSkin);
            Label precio = new Label("Precio:", Assets.uiSkin);
            Label precioValue = new Label(String.valueOf(objeto.getPrecio()), Assets.uiSkin);
            Label descripcion = new Label("Descripcion:", Assets.uiSkin);
            Label descripcionValue = new Label(objeto.getDescripcion(), Assets.uiSkin);
            Button button = new Button(Assets.uiSkin);

            table.setSkin(Assets.uiSkin);

            table.add(image).expand().bottom().fillX().fillY().width(30).height(30);
            table.add(nombre).expand();
            table.add(nombreValue);
            table.add(precio).right();
            table.add(precioValue).width(100);
            table.row();
            table.add(descripcion).center();
            table.add(descripcionValue).colspan(2);
            table.add(button).right();
            table.row().spaceTop(5);

            button.addListener(event -> {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                    nakamaStorage.comprarObjeto(objeto.getNombre());
                    System.out.println("se ha comprado el objeto: "+objeto.getNombre());
                }
                return false;
            });

        }
        addActor(table);
        table.setPosition(personaje.getX(), personaje.getY());

    }

    public void showObjetosInventario(List<Objeto> lista) {
        table = new Table(Assets.uiSkin);
        table.setBackground("white");

        for (Objeto objeto: lista){
            Image image = new Image(new Texture("objeto_espada_standard.png"));
            Label nombreValue = new Label(objeto.getNombre(), Assets.uiSkin);
            Label precioValue = new Label(String.valueOf(objeto.getPrecio()), Assets.uiSkin);
            Label descripcionValue = new Label(objeto.getDescripcion(), Assets.uiSkin);
            Button borrarObjeto = new Button(Assets.uiSkin);

            table.setDebug(true);
            table.add(image).expand().bottom().fillX().fillY().width(30).height(30);
            table.add(nombreValue);
            table.add(precioValue).width(100);
            table.row();
            table.add(descripcionValue).colspan(2);
            table.add(borrarObjeto);
            table.row().spaceTop(5);

            borrarObjeto.addListener(event -> {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                    nakamaStorage.eliminarObjetoDelInventario(objeto);
                    table.removeActor(image);
                    table.removeActor(nombreValue);
                    table.removeActor(precioValue);
                    table.removeActor(descripcionValue);
                    table.removeActor(borrarObjeto);

                }
                return false;
            });
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
        List[] colecciones = {arboles, npcs, aguaList, sillasList, puertas,monedas};

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

    public void addPozo(Fixture fixture, MapObject mapObject){
        Pozo pozo = new Pozo(fixture, mapObject);
        pozos.add(pozo);
        addActor(pozo);
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
        Enemigo enemigoBase = new Enemigo(fixture, mapObject,true,128f);
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

    public void addCatedral(Fixture fixture, MapObject mapObject) {
        Catedral catedral = new Catedral(fixture,mapObject);
        addActor(catedral);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta;
        world.step(delta, 6, 2);

        barraVida.setPosition(personaje.getX()-278, personaje.getY()+110);


        for (Enemigo enemigo : enemigos){

            if (personaje != null){
                if (enemigo.getBehavior() == null){
                    pursue = new Pursue<>(enemigo, personaje, 3f);
                    System.out.println(pursue.getTarget());
                    pursue.setEnabled(true);
                    enemigo.setBehavior(pursue);

                    System.out.println(personaje.steeringOutput.linear.toString());
                    enemigo.steeringOutput = pursue.calculateSteering(personaje.steeringOutput);
                    enemigo.update(delta);
                    System.out.println(enemigo.steeringOutput.linear.toString());
                }else {
                    System.out.println(personaje.steeringOutput.linear.toString());
                    pursue.calculateSteering(personaje.steeringOutput);
                    enemigo.update(delta);
                    System.out.println(enemigo.steeringOutput.linear.toString());
                }

//                enemigo.update(delta);
//                Seek<Vector2> seek = new Seek<>(enemigo,personaje);
//                seek.setEnabled(true);
//                System.out.println(Enemigo.steeringOutput.linear.toString());
//                enemigo.setBehavior(seek);
//                seek.calculateSteering(personaje.steeringOutput);
//
//                enemigo.update(delta);



            }
        }

        if (table != null){
            table.setPosition(personaje.getX()-100, personaje.getY()-100);
        }

        if (reloadMap) {
            clearMyWorld();
            reloadMap = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            showPausa();
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
        chat.setPosition(personaje.getX()+248, personaje.getY()+160);
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

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void showPausa() {
        Table pausa = new Table();
        TextButton resume = new TextButton("Resume",Assets.uiSkin);
        TextButton menuPrincipal = new TextButton("Menu principal",Assets.uiSkin);

        pausa.add(resume);
        pausa.row();
        pausa.add(menuPrincipal);
        addActor(pausa);

        resume.addListener(event -> {
            removeActor(pausa);
            pausaMenu = false;
            return false;
        });

        menuPrincipal.addListener(event -> {
            irAlmenuPrincipal = true;
            return false;
        });
    }
}