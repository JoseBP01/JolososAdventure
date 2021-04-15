package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets extends AssetManager {


    public static TextureAtlas atlas;
    public static Skin uiSkin;


    public void load(){
        load("miatles.atlas", TextureAtlas.class);
        load("uiskins/skin.json", Skin.class);
    }

    @Override
    public synchronized boolean update() {
        boolean update = super.update();

        if(update){
            atlas = get("miatles.atlas", TextureAtlas.class);
            uiSkin = get("uiskins/skin.json", Skin.class);
        }
        return update;
    }


    public static Animation<TextureRegion> getAnimation(String name, float time, Animation.PlayMode playMode){
        return new Animation<>(time, atlas.findRegions(name), playMode);
    }
}
