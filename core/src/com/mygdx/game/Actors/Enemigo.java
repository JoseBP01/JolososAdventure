package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.Assets;
import com.mygdx.game.Config;
import com.mygdx.game.MyWidgets.MyActor;

public class Enemigo extends MyActor{

    //Animacion Movimiento
    private static final Animation<TextureRegion> animacionCaminarIzquierda = Assets.getAnimation("direcIzquierda", 0.1f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> animacionCaminarDerecha = Assets.getAnimation("direcDerecha", 0.1f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> animacionCaminarArriba = Assets.getAnimation("direcArriba", 0.1f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> animacionCaminarAbajo = Assets.getAnimation("direcAbajo", 0.1f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> quietoIzquierda = Assets.getAnimation("quietosIzquierda", 0.1f, Animation.PlayMode.NORMAL);
    private static final Animation<TextureRegion> quietoDerecha = Assets.getAnimation("quietosDerecha", 0.1f, Animation.PlayMode.NORMAL);
    private static final Animation<TextureRegion> quietoArriba = Assets.getAnimation("quietosArriba", 0.1f, Animation.PlayMode.NORMAL);
    private static final Animation<TextureRegion> quietoAbajo = Assets.getAnimation("quietosAbajo", 0.1f, Animation.PlayMode.NORMAL);
    //Animacion Ataque
    private static final Animation<TextureRegion> ataqueIzquierda = Assets.getAnimation("ataqueIzquierda", 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private static final Animation<TextureRegion> ataqueDerecha = Assets.getAnimation("ataqueDerecha", 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private static final Animation<TextureRegion> ataqueArriba = Assets.getAnimation("ataqueArriba", 0.2f, Animation.PlayMode.LOOP_PINGPONG);
    private static final Animation<TextureRegion> ataqueAbajo = Assets.getAnimation("ataqueAbajo", 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    //Animacion Curar
    private static final Animation<TextureRegion> curarIzquierda = Assets.getAnimation("animCuracionIzquierda", 0.2f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> curarDerecha = Assets.getAnimation("animCuracionDerecha", 0.2f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> curarArriba = Assets.getAnimation("animCuracionArriba", 0.2f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> curarAbajo = Assets.getAnimation("animCuracionAbajo", 0.2f, Animation.PlayMode.LOOP);

    private State estado;
    private Direccion direccion;

    private final int vX = 300;
    private final int vY = 300;

//    Timer timer;

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
        estado = State.Quieto;
        direccion = Direccion.Izquerda;
        setHeight(((Float) mapObject.getProperties().get("height")* Config.UNIT_SCALE));
        setWidth((Float) mapObject.getProperties().get("width")* Config.UNIT_SCALE);
//        timer = new Timer(3);
    }

    public void irHaciaEljugador(float x, float y,float maxX, float maxY){
                if (x < getX()){
                    if (getX() > maxX){
                    }else {
                        direccion = Direccion.Derecha;
                        setState(State.Caminando);
                        while (getX() != x && getY() != y){
                            moveBy(vX,0);
                        }

//                        body.setLinearVelocity(vX,0);
                    }


                }else if (x > getX()){
                    if (getX() < maxX){
                    }else {
                        direccion = Direccion.Izquerda;
                        setState(State.Caminando);
//                        body.setLinearVelocity(-vX,0);
                        while (getX() != x && getY() != y) {
                            moveBy(-vX,0);

                        }
                    }
                }

                if (y < getY()){
                    if (getY() > maxY){
                    }else{
                        direccion = Direccion.Arriba;
                        setState(State.Caminando);
//                        body.setLinearVelocity(0,vY);
                        while (getX() != x && getY() != y) {
                            moveBy(0, vY);
                        }
                    }
                }else if (y > getY()){
                    if (getY() < maxY){
                    }else {
                        direccion = Direccion.Abajo;
                        setState(State.Caminando);
//                        body.setLinearVelocity(0,-vY);
                        while (getX() != x && getY() != y) {
                            moveBy(0, -vY);
                        }
                    }
                }

                if (x == getX() && y == getY()){
                    setState(State.Quieto);
                    moveBy(0, 0);
                    body.setLinearVelocity(0,0);
                }
            System.out.println("x: "+getX()+" y: "+getY());

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(body.getPosition().x, body.getPosition().y);
        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(currentAnimation != null) {
            Color color = getColor();
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
            batch.draw(currentAnimation.getKeyFrame(getStateTime()), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    @Override
    public void defineBody() {
        body.setType(BodyDef.BodyType.StaticBody);
        body.setGravityScale(0);
    }

    public void setState(State state){
        estado = state;
        switch (estado){
            case Caminando:
                switch (direccion){
                    case Izquerda:
                        currentAnimation = animacionCaminarIzquierda;
                        break;
                    case Derecha:
                        currentAnimation = animacionCaminarDerecha;
                        break;

                    case Arriba:
                        currentAnimation=animacionCaminarArriba;
                        break;

                    case Abajo:
                        currentAnimation=animacionCaminarAbajo;
                        break;
                }
                break;
            case Quieto:
                switch (direccion){
                    case Izquerda:
                        currentAnimation = quietoIzquierda;
                        break;

                    case Derecha:
                        currentAnimation = quietoDerecha;
                        break;

                    case Arriba:
                        currentAnimation = quietoArriba;
                        break;

                    case Abajo:
                        currentAnimation = quietoAbajo;
                        break;
                }
                break;
            case Ataque:
                switch (direccion){
                    case Izquerda:
                        currentAnimation = ataqueIzquierda;
                        break;
                    case Derecha:
                        currentAnimation = ataqueDerecha;
                        break;
                    case Arriba:
                        currentAnimation = ataqueArriba;
                        break;
                    case Abajo:
                        currentAnimation = ataqueAbajo;
                        break;
                }
                break;
            case Curar:
                switch (direccion){
                    case Izquerda:
                        currentAnimation = curarIzquierda;
                        break;

                    case Derecha:
                        currentAnimation = curarDerecha;
                        break;

                    case Arriba:
                        currentAnimation = curarArriba;
                        break;

                    case Abajo:
                        currentAnimation = curarAbajo;
                        break;
                }
                break;

            default: currentAnimation = quietoDerecha;
                break;
        }
    }
//https://github.com/codeandcoke/jbombermanx
}
