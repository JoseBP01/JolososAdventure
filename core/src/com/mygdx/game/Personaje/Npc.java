package com.mygdx.game.Personaje;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.Assets;
import com.mygdx.game.Base.MyActor;
import com.mygdx.game.Base.MyWorld;

public class Npc extends MyActor {

    String[] spriteNpc = {"dancerA", "dancerB"};
    int numeroSprite;

    public Npc(Fixture fixture) {
        super(fixture);
        numeroSprite= (int) (Math.random()*1);
        setSize(32,32);

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

