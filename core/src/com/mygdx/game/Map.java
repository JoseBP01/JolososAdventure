package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Screens.GameScreen;

public class Map extends Actor {
    TiledMap map;
    MapRenderer mapRenderer;
    OrthographicCamera camera;
    TiledMapTileLayer layerGround;
    //https://github.com/gerardfp/MarioLibGDX/blob/master/core/src/com/mygdx/game/Map.java
    //https://github.com/gerardfp/GdxWebSockets/tree/master/core/src/com/mygdx/game


    public Map(OrthographicCamera camera) {
        this.camera = camera;
        map = new TmxMapLoader().load("maps/mapa.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, Config.UNIT_SCALE);
    }

    public void loadObjects(GameScreen gameScreen){
        for (MapObject mapObject: map.getLayers().get("npc").getObjects()){
            float x = (Float) mapObject.getProperties().get("x") * Config.UNIT_SCALE;
            float y = (Float) mapObject.getProperties().get("y") * Config.UNIT_SCALE;
            gameScreen.addPersonaje(x, y);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        mapRenderer.setView(camera);
        mapRenderer.render();
    }
}
