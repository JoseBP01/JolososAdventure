package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Assets;
import com.mygdx.game.Config;
import com.mygdx.game.MyWidgets.MyActor;
import com.mygdx.game.MyWidgets.MyWorld;

public class Enemigo extends MyActor {

    private Personaje.State estado;
    private Personaje.Direccion direccion;

    float timer;

    public enum State {
        Quieto,
        Caminando,
        Ataque,
        Curar
    }
    public enum Direccion {
        Izquerda,
        Derecha,
        Abajo,
        Arriba
    }

    public Enemigo(Fixture fixture, MapObject mapObject) {
        super(fixture);
        currentAnimation = Assets.getAnimation("quietoIzquierda",0.3f, Animation.PlayMode.NORMAL);
        fixture.getFilterData().categoryBits = MyWorld.ENEMIGO_BIT;
        fixture.getFilterData().maskBits = MyWorld.PERSONAJE_BIT;
        estado = Personaje.State.Quieto;
        setHeight(((Float) mapObject.getProperties().get("height")* Config.UNIT_SCALE));
        setWidth((Float) mapObject.getProperties().get("width")* Config.UNIT_SCALE);

//        timer = MyWorld.time + 3;

    }

    @Override
    public void defineBody() {
        body.setType(BodyDef.BodyType.DynamicBody);
        body.setGravityScale(0);
    }

}
