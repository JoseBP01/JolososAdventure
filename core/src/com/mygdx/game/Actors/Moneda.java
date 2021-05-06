package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.Assets;
import com.mygdx.game.Config;
import com.mygdx.game.MyWidgets.MyActor;
import com.mygdx.game.MyWidgets.Timer;

import java.util.Random;

public class Moneda extends MyActor {


    Timer timer;

    public Moneda(Fixture fixture, MapObject mapObject) {
        super(fixture);

        setName(""+ new Random().nextInt());

        currentAnimation = Assets.getAnimation("movMoneda", 0.3f, Animation.PlayMode.NORMAL);
        fixture.getFilterData().categoryBits= MyWorld.MONEDA_BIT;
        fixture.getFilterData().maskBits = MyWorld.PERSONAJE_BIT;

        setHeight(((Float) mapObject.getProperties().get("height")* Config.UNIT_SCALE));
        setWidth((Float) mapObject.getProperties().get("width")* Config.UNIT_SCALE);

        timer = new Timer(3);
    }

    @Override
    public void defineBody() {
        body.setType(BodyDef.BodyType.StaticBody);
    }

    //el act funciona como un while
    @Override
    public void act(float delta) {
        super.act(delta);

        if(timer.pita()){
            stateTime = 0;
        }
    }

    boolean equals(Body body){
        return getName().equals(((Moneda) body.getUserData()).getName());
    }

}
