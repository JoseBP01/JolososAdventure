package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.Assets;
import com.mygdx.game.MyWidgets.MyActor;

public class Moneda extends MyActor {

    private static final Animation<TextureRegion> animacionMoneda = Assets.getAnimation("hpBar", 0.3f, Animation.PlayMode.LOOP);


    public Moneda(Fixture fixture) {
        super(fixture);

    }
}
