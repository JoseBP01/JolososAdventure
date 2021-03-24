package com.mygdx.game.Personaje;

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

    public Personaje(World world){
        setSize(40,40);
        setOrigin(Align.center);
        estado = State.Quieto;
        direccion = Direccion.Derecha;
        hitBox = new Circle(getX(),getY(), 32);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit


// Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);
        circle.dispose();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

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
