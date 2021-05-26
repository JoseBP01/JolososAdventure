package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.Assets;
import com.mygdx.game.MyWidgets.MyActor;

public class Catedral extends MyActor {
    public Catedral(Fixture fixture) {
        super(fixture);
        currentAnimation = Assets.getAnimation("spriteCatedral", 0.3f, Animation.PlayMode.NORMAL);
    }

    @Override
    public void define(Fixture fixture) {
        super.define(fixture);
        body.setType(BodyDef.BodyType.StaticBody);
    }
}
