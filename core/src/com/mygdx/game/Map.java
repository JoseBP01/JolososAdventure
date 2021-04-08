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
import com.mygdx.game.MyWidgets.MyWorld;
import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

public class Map extends Actor {
    TiledMap map;
    MapRenderer mapRenderer;
    OrthographicCamera camera;
    //https://github.com/gerardfp/MarioLibGDX/blob/master/core/src/com/mygdx/game/Map.java
    //https://github.com/gerardfp/GdxWebSockets/tree/master/core/src/com/mygdx/game


    public Map(OrthographicCamera camera) {
        this.camera = camera;
        map = new TmxMapLoader().load("maps/mapa.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, Config.UNIT_SCALE);
    }

    public void loadObjects(MyWorld myWorld){
        Box2DMapObjectParser box2DMapObjectParser = new Box2DMapObjectParser(Config.UNIT_SCALE); // TODO: UnitScale

        box2DMapObjectParser.setListener(new Box2DMapObjectParser.Listener.Adapter() {
            @Override
            public void created(Fixture fixture, MapObject mapObject) {
                super.created(fixture, mapObject);
                System.out.println("CREANDO " + mapObject.getName());
                switch (mapObject.getName()) {
                    case "personaje":
                        myWorld.addPersonaje(fixture,mapObject);
                        break;
                    case "npc":
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
                    case "puerta":
                        myWorld.addPuerta(fixture);
                        break;
                    case "agua":
                        myWorld.addAgua(fixture);
                        break;
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
