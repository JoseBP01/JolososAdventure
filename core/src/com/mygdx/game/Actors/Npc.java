package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.Assets;
import com.mygdx.game.Config;
import com.mygdx.game.MyWidgets.MyActor;

public class Npc extends MyActor {

    String[] spriteNpc = {"dancerA", "dancerB"};
    int numeroSprite;

    public Npc(Fixture fixture, MapObject mapObject) {
        super(fixture);
        numeroSprite= (int) (Math.random()*1);
        setWidth(((float) mapObject.getProperties().get("width"))* Config.UNIT_SCALE);
        setHeight((Float) mapObject.getProperties().get("height")* Config.UNIT_SCALE);

        currentAnimation = Assets.getAnimation(spriteNpc[numeroSprite], 0.3f, Animation.PlayMode.NORMAL);
    }

    @Override
    public void define(Filter filter) {
        filter.categoryBits = MyWorld.NPC_BIT;
        filter.maskBits = MyWorld.TIERRA_BIT |
                MyWorld.PERSONAJE_BIT;
    }

    @Override
    public void defineBody() {
        body.setType(BodyDef.BodyType.StaticBody);
    }
}

