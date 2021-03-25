package com.mygdx.game.Personaje;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;

public class Personaje extends Actor {

    private static Animation<TextureRegion> animacionCaminarIzquierda = Assets.getAnimation("caminandoIzquierda", 0.3f, Animation.PlayMode.LOOP);
    private static Animation<TextureRegion> animacionCaminarDerecha = Assets.getAnimation("caminandoDerecha", 0.3f, Animation.PlayMode.LOOP);
    private static Animation<TextureRegion> animacionCaminarArriba = Assets.getAnimation("caminandoArriba", 0.3f, Animation.PlayMode.LOOP);
    private static Animation<TextureRegion> animacionCaminarAbajo = Assets.getAnimation("caminandoAbajo", 0.3f, Animation.PlayMode.LOOP);
    private static Animation<TextureRegion> quietoIzquierda = Assets.getAnimation("quietoIzquierda", 0.3f, Animation.PlayMode.NORMAL);
    private static Animation<TextureRegion> quietoDerecha = Assets.getAnimation("quietoDerecha", 0.3f, Animation.PlayMode.NORMAL);
    private static Animation<TextureRegion> quietoArriba = Assets.getAnimation("quietoArriba", 0.3f, Animation.PlayMode.NORMAL);
    private static Animation<TextureRegion> quietoAbajo = Assets.getAnimation("quietoAbajo", 0.3f, Animation.PlayMode.NORMAL);

    private Animation<TextureRegion> currentAnimation = Assets.getAnimation("quietoDerecha", 0.3f, Animation.PlayMode.NORMAL);

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

    private float stateTime;
    private float vx = 5;
    private float vy = 5;
    private State estado;
    private Direccion direccion;
    private int vidas = 4;
    private Circle hitBox;
    BodyDef bodyDef = new BodyDef();
    public Body body;


    public Personaje(World world, float x, float y){
        setSize(40,40);
        setOrigin(Align.center);
        setPosition(x,y);
        estado = State.Quieto;
        direccion = Direccion.Derecha;
        hitBox = new Circle(getX(),getY(), 32);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.80f;
        fixtureDef.friction = 1.00f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
        fixtureDef.filter.categoryBits = 1;


// Create our fixture and attach it to the body
        body.createFixture(fixtureDef).setUserData(this);
        circle.dispose();
    }

    public void izquierda() {
        setDireccion(Direccion.Izquerda);
        setState(State.Caminando);
//        body.applyForce(-1.0f, 0.0f, bodyDef.position.x, bodyDef.position.y, true);
//        body.applyLinearImpulse(-20.00f, 0, body.getPosition().x, body.getPosition().y, true);
        body.setLinearVelocity(-300,0);
    }

    public void derecha(){
        setDireccion(Direccion.Derecha);
        setState(Personaje.State.Caminando);
//        body.applyForce(1.0f, 0.0f, bodyDef.position.x, bodyDef.position.y, true);
//        body.applyLinearImpulse(20.00f, 0, body.getPosition().x, body.getPosition().y, true);
        body.setLinearVelocity(300,0);
    }

    public void arriba(){
        setDireccion(Direccion.Arriba);
        setState(State.Caminando);
//        body.applyForce(0.0f, -1.0f,bodyDef.position.x, bodyDef.position.y, true);
        body.setLinearVelocity(0,300);

    }

    public void abajo(){
        setDireccion(Direccion.Abajo);
        setState(State.Caminando);
//        body.applyForce(0.0f, 1.0f,bodyDef.position.x, bodyDef.position.y, true);
        body.setLinearVelocity(0,-300);

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
        }else{
            setState(Personaje.State.Quieto);
            body.setLinearVelocity(0,0);
        }

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

        System.out.println("POS PERSONAJE " + getX() + " : " + getY());
        batch.draw(currentAnimation.getKeyFrame(stateTime), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
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

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
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

    public Circle getHitBox() {
        return hitBox;
    }
}
