package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Assets;

public class PersonajeOnline extends Actor {

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
    private static final Animation<TextureRegion> ataqueIzquierda = Assets.getAnimation("caminandoIzquierda", 0.5f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> ataqueDerecha = Assets.getAnimation("caminandoDerecha", 0.5f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> ataqueArriba = Assets.getAnimation("caminandoArriba", 0.5f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> ataqueAbajo = Assets.getAnimation("caminandoAbajo", 0.5f, Animation.PlayMode.LOOP);
    //Animacion Curar
    private static final Animation<TextureRegion> curarIzquierda = Assets.getAnimation("animCuracionIzquierda", 0.2f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> curarDerecha = Assets.getAnimation("animCuracionDerecha", 0.2f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> curarArriba = Assets.getAnimation("animCuracionArriba", 0.2f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> curarAbajo = Assets.getAnimation("animCuracionAbajo", 0.2f, Animation.PlayMode.LOOP);

    public enum State {
        Quieto,
        Ataque, Caminando, Curar
    }
    public enum Direccion {
        Izquerda,
        Derecha,
        Abajo,
        Arriba
    }

    private String id;
    private float x;
    private float y;
    private State estado;
    private Direccion direccion;
    private Circle hitBox;
    BodyDef bodyDef = new BodyDef();
    public Body body;
    private World world;
    public float stateTime;
    public Animation<TextureRegion> currentAnimation = quietoDerecha;

    public PersonajeOnline(String id, float x, float y, World world) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.world = world;
        setSize(32,32);
        setPosition(x+getWidth(),y+getHeight());
        estado = State.Quieto;
        direccion = Direccion.Derecha;
//        hitBox = new Circle(getX(),getY(), 32);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(12f);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        body.setGravityScale(0);
        fixtureDef.density = 0.80f;
        fixtureDef.friction = 1.00f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
        fixtureDef.filter.categoryBits = 1;


// Create our fixture and attach it to the body
        body.createFixture(fixtureDef).setUserData(this);
//        circle.dispose();
    }

    public void update(float x, float y){
        if (x != 0 && y != 0){
            if (x > getX()){
                direccion = Direccion.Derecha;
                setState(State.Caminando);
            }else if (x < getX()){
                direccion = Direccion.Izquerda;
                setState(State.Caminando);
            }

            if (y > getY()){
                direccion = Direccion.Arriba;
                setState(State.Caminando);
            }else if (y < getY()){
                direccion = Direccion.Abajo;
                setState(State.Caminando);
            }

            if (x == getX() && y == getY()){
                setState(State.Quieto);
            }
            setX(x);
            setY(y);
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
        if(currentAnimation != null) {
            Color color = getColor();
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
            batch.draw(currentAnimation.getKeyFrame(getStateTime()), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
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

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public World getWorld(){
        return world;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }
}
