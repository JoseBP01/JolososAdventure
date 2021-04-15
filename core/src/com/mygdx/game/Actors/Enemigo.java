package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Assets;
import com.mygdx.game.MyWidgets.MyActor;

public class Enemigo extends MyActor {

    private static final Animation<TextureRegion> animacionCaminarIzquierda = Assets.getAnimation("caminandoIzquierda", 0.3f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> animacionCaminarDerecha = Assets.getAnimation("caminandoDerecha", 0.3f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> animacionCaminarArriba = Assets.getAnimation("caminandoArriba", 0.3f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> animacionCaminarAbajo = Assets.getAnimation("caminandoAbajo", 0.3f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> quietoIzquierda = Assets.getAnimation("quietoIzquierda", 0.3f, Animation.PlayMode.NORMAL);
    private static final Animation<TextureRegion> quietoDerecha = Assets.getAnimation("quietoDerecha", 0.3f, Animation.PlayMode.NORMAL);
    private static final Animation<TextureRegion> quietoArriba = Assets.getAnimation("quietoArriba", 0.3f, Animation.PlayMode.NORMAL);
    private static final Animation<TextureRegion> quietoAbajo = Assets.getAnimation("quietoAbajo", 0.3f, Animation.PlayMode.NORMAL);

    public Enemigo(Fixture fixture) {
        super(fixture);


    }

    @Override
    public void defineBody() {
        body.setType(BodyDef.BodyType.DynamicBody);
//        body.setGravityScale(0);
    }

}
