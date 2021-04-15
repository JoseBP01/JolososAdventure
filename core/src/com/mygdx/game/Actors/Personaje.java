package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.Assets;
import com.mygdx.game.MyWidgets.MyActor;
import com.mygdx.game.Config;
import com.mygdx.game.MyWidgets.MyWorld;

public class Personaje extends MyActor {
    //Sprite Base
//    private static final Animation<TextureRegion> animacionCaminarIzquierda = Assets.getAnimation("caminandoIzquierda", 0.3f, Animation.PlayMode.LOOP);
//    private static final Animation<TextureRegion> animacionCaminarDerecha = Assets.getAnimation("caminandoDerecha", 0.3f, Animation.PlayMode.LOOP);
//    private static final Animation<TextureRegion> animacionCaminarArriba = Assets.getAnimation("caminandoArriba", 0.3f, Animation.PlayMode.LOOP);
//    private static final Animation<TextureRegion> animacionCaminarAbajo = Assets.getAnimation("caminandoAbajo", 0.3f, Animation.PlayMode.LOOP);
//    private static final Animation<TextureRegion> quietoIzquierda = Assets.getAnimation("quietoIzquierda", 0.3f, Animation.PlayMode.NORMAL);
//    private static final Animation<TextureRegion> quietoDerecha = Assets.getAnimation("quietoDerecha", 0.3f, Animation.PlayMode.NORMAL);
//    private static final Animation<TextureRegion> quietoArriba = Assets.getAnimation("quietoArriba", 0.3f, Animation.PlayMode.NORMAL);
//    private static final Animation<TextureRegion> quietoAbajo = Assets.getAnimation("quietoAbajo", 0.3f, Animation.PlayMode.NORMAL);

    //Nuevo Sprite
    private static final Animation<TextureRegion> animacionCaminarIzquierda = Assets.getAnimation("direcIzquierda", 0.1f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> animacionCaminarDerecha = Assets.getAnimation("direcDerecha", 0.1f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> animacionCaminarArriba = Assets.getAnimation("direcArriba", 0.1f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> animacionCaminarAbajo = Assets.getAnimation("direcAbajo", 0.1f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> quietoIzquierda = Assets.getAnimation("quietosIzquierda", 0.1f, Animation.PlayMode.NORMAL);
    private static final Animation<TextureRegion> quietoDerecha = Assets.getAnimation("quietosDerecha", 0.1f, Animation.PlayMode.NORMAL);
    private static final Animation<TextureRegion> quietoArriba = Assets.getAnimation("quietosArriba", 0.1f, Animation.PlayMode.NORMAL);
    private static final Animation<TextureRegion> quietoAbajo = Assets.getAnimation("quietosAbajo", 0.1f, Animation.PlayMode.NORMAL);



    public enum State {
        Quieto,
        Caminando
    }
    public enum Direccion {
        Izquerda,
        Derecha,
        Abajo,
        Arriba
    }

    private float vx = 5;
    private float vy = 5;
    private State estado;
    private Direccion direccion;
    private int vidas = 4;

    public Personaje(Fixture fixture, MapObject mapObject) {
        super(fixture);

        currentAnimation = Assets.getAnimation("quietosDerecha", 0.3f, Animation.PlayMode.NORMAL);
        direccion = Direccion.Derecha;
        estado = State.Quieto;
        fixture.getFilterData().categoryBits= MyWorld.PERSONAJE_BIT;

        setHeight(((Float) mapObject.getProperties().get("height")* Config.UNIT_SCALE));
        setWidth((Float) mapObject.getProperties().get("width")* Config.UNIT_SCALE);

    }

    @Override
    public void defineBody() {
        body.setType(BodyDef.BodyType.DynamicBody);
        body.setGravityScale(0);
    }

    public void izquierda() {
        setDireccion(Direccion.Izquerda);
        setState(State.Caminando);
        body.setLinearVelocity(-300,0);
    }

    public void derecha(){
        setDireccion(Direccion.Derecha);
        setState(Personaje.State.Caminando);
        body.setLinearVelocity(300,0);
    }

    public void arriba(){
        setDireccion(Direccion.Arriba);
        setState(State.Caminando);
        body.setLinearVelocity(0,300);

    }

    public void abajo(){
        setDireccion(Direccion.Abajo);
        setState(State.Caminando);
        body.setLinearVelocity(0,-300);

    }

    public void ataque(){

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        System.out.println("dibujando");
    }

    public void manejarTeclas() {
        if(Gdx.input.isKeyPressed(Input.Keys.A)){

            izquierda();
        }else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            derecha();
            moveBy(getVx(), 0);

        }else if(Gdx.input.isKeyPressed(Input.Keys.W)){
            arriba();
            moveBy(0, getVy());
        }else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            abajo();
            moveBy(0, -getVy());
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            ataque();
//            if (getVx()==)
        }
        else{
            setState(Personaje.State.Quieto);
            body.setLinearVelocity(0,0);
        }
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

            default: currentAnimation = quietoDerecha;
            break;
        }
    }



    public void daño_recivido(){
        vidas--;
    }



    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
}
