package com.mygdx.game.Actors.UtilidadesPersonaje;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Actors.Personaje;
import com.mygdx.game.Assets;



public class HpBarra extends Actor {

    private static final Animation<TextureRegion> vida0 = Assets.getAnimation("hpBarVida0", 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private static final Animation<TextureRegion> vida1 = Assets.getAnimation("hpBarVida1", 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private static final Animation<TextureRegion> vida2 = Assets.getAnimation("hpBarVida2", 0.2f, Animation.PlayMode.LOOP_PINGPONG);
    private static final Animation<TextureRegion> vida3 = Assets.getAnimation("hpBarVida3", 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private static final Animation<TextureRegion> vida4 = Assets.getAnimation("hpBarVida4", 0.1f, Animation.PlayMode.LOOP_PINGPONG);


    public int vidas;
    BodyDef bodyDef = new BodyDef();
    public Body body;
    private World world;
    public float stateTime;
    public Animation<TextureRegion> currentAnimation = vida4;


    public HpBarra(World world, Personaje personaje){
        setSize(160,160);
        vidas = 4;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(personaje.getX(), personaje.getY());
        body = world.createBody(bodyDef);
    }

    public void setEstadoBarra(int vidas) {
        switch (vidas){
            case 3:
                currentAnimation = vida3;
                break;
            case 2:
                currentAnimation = vida2;
                break;
            case 1:
                currentAnimation = vida1;
                break;
            case 0:
                currentAnimation = vida0;
                break;
        }
    }

    public void setVidas(int vidas){
        this.vidas = vidas;
        setEstadoBarra(vidas);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
       batch.draw(currentAnimation.getKeyFrame(stateTime),getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
