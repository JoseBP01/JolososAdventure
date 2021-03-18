package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets extends AssetManager {


    public TextureAtlas atlas;
    public static Animation<TextureRegion> caminar;
    public static Animation<TextureRegion> quieto;
    public static Animation<TextureRegion> barraHp;

    public void load(){
        load("miatles.atlas", TextureAtlas.class);
    }

    @Override
    public synchronized boolean update() {
        boolean update = super.update();

        if(update){
            atlas = get("miatles.atlas", TextureAtlas.class);

            loadAnimations();
        }
        return update;
    }

    void loadAnimations(){
        caminar = new Animation<TextureRegion>(0.1f, atlas.findRegions("walking"));
        quieto = new Animation<TextureRegion>(0.1f, atlas.findRegions("walking"));
        barraHp = new Animation<TextureRegion>(0.1f, atlas.findRegions("hpBar"));


    }
}
