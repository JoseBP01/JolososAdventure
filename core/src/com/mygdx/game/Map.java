package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Actors.MyWorld;
import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

public class Map extends Actor {
    TiledMap map;
    TmxMapLoader loader;
    MapRenderer mapRenderer;
    OrthographicCamera camera;
    //https://github.com/gerardfp/MarioLibGDX/blob/master/core/src/com/mygdx/game/Map.java
    //https://github.com/gerardfp/GdxWebSockets/tree/master/core/src/com/mygdx/game


    public Map(OrthographicCamera camera, String mapLoader) {
        this.camera = camera;
        map = new TmxMapLoader().load(mapLoader);
        mapRenderer = new OrthogonalTiledMapRenderer(map, Config.UNIT_SCALE);
    }

    public void loadObjects(MyWorld myWorld){
        Box2DMapObjectParser box2DMapObjectParser = new Box2DMapObjectParser(Config.UNIT_SCALE); // TODO: UnitScale

        box2DMapObjectParser.setListener(new Box2DMapObjectParser.Listener.Adapter() {
            @Override
            public void created(Fixture fixture, MapObject mapObject) {
                super.created(fixture, mapObject);
               // System.out.println("CREANDO " + mapObject.getName());
                switch (mapObject.getName()) {
                    case "personaje":
                        myWorld.addPersonaje(fixture,mapObject);
                        break;
                    case "pozo":
                        myWorld.addPozo(fixture,mapObject);
                        break;
                    case "npcHelp":
                        myWorld.addNpc(fixture,mapObject);
                        break;
                    case "casa":
                        myWorld.addCasa(fixture);
                        break;
                    case "tierra":
                        myWorld.addTierra(fixture);
                        break;
                    case "arbol":
                        myWorld.addArbol(fixture);
                        break;
                    case "puertaCastillo":
                        myWorld.addPuerta(fixture,"maps/castillo1.tmx", mapObject.getName());
                        break;
                    case "puertamap1":
                    case "salidajesus1":
                        myWorld.addPuerta(fixture,"maps/mapa.tmx", mapObject.getName());
                        break;
                    case "salidasotano1":
                    case "puertajesus1":
                        myWorld.addPuerta(fixture,"maps/casa.tmx", mapObject.getName());
                        break;
                    case "puertasotano":
                        myWorld.addPuerta(fixture,"maps/sotano.tmx", mapObject.getName());
                        break;
                    case "agua":
                        myWorld.addAgua(fixture);
                        break;
                    case "pared":
                        myWorld.addPared(fixture);
                        break;
                    case "sillas":
                        myWorld.addSillas(fixture);
                        break;
                    case "moneda":
                        myWorld.addMoneda(fixture, mapObject);
                        break;
                    case "enemigo":
                        myWorld.addEnemigo(fixture, mapObject);
                        break;
                    case "catedral":
                        myWorld.addCatedral(fixture,mapObject);
                }
            }
        });

        box2DMapObjectParser.load(myWorld.world, map);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
}
