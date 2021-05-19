package com.mygdx.game.Actors;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.Assets;
import com.mygdx.game.Config;
import com.mygdx.game.IA.SteeringUtils;
import com.mygdx.game.MyWidgets.MyActor;

public class Enemigo extends MyActor implements Steerable<Vector2> {

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


    Vector2 position = new Vector2();
    float orientation;
    Vector2 linearVelocity= new Vector2();
    float angularVelocity;
    float maxSpeed;
    boolean independentFacing;

    boolean tagged;
    float boundingRadius;
    float maxLinearSpeed, maxLinearAcceleration;
    float maxAngularSpeed, maxAngularAcceleration;

    SteeringBehavior<Vector2> behavior;
    public static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());
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

    public Enemigo(Fixture fixture, MapObject mapObject, boolean independentFacing, float boundingRadius) {
        super(fixture);
        currentAnimation = Assets.getAnimation("quietoIzquierda",0.3f, Animation.PlayMode.NORMAL);
        fixture.getFilterData().categoryBits = MyWorld.ENEMIGO_BIT;
        fixture.getFilterData().maskBits = MyWorld.PERSONAJE_BIT;
        estado = State.Quieto;
        direccion = Direccion.Izquerda;
        setHeight(((Float) mapObject.getProperties().get("height")* Config.UNIT_SCALE));
        setWidth((Float) mapObject.getProperties().get("width")* Config.UNIT_SCALE);
        this.independentFacing = independentFacing;
        this.boundingRadius = boundingRadius;

        maxLinearSpeed = 400;
        maxAngularAcceleration = 500;
        maxAngularSpeed = 30;
        maxAngularSpeed = 5;

        tagged= false;
    }

    @Override
    public void defineBody() {
        body.setType(BodyDef.BodyType.DynamicBody);
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

    public void update(float deltaTime) {
        if (behavior != null) {
            // Calculate steering acceleration
            applySteering(steeringOutput, deltaTime);

            /*
             * Here you might want to add a motor control layer filtering steering accelerations.
             *
             * For instance, a car in a driving game has physical constraints on its movement: it cannot turn while stationary; the
             * faster it moves, the slower it can turn (without going into a skid); it can brake much more quickly than it can
             * accelerate; and it only moves in the direction it is facing (ignoring power slides).
             */

            // Apply steering acceleration
//            applySteering(steeringOutput, deltaTime);
        }

//        wrapAround(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
    }

    public void irAporElJugador(Personaje personaje){
//        Pursue<Vector2> pursue = new Pursue<Vector2>(this,personaje,3f);
////                System.out.println(pursue.getTarget());
//        System.out.println(personaje.steeringOutput.linear.toString());
//        pursue.calculateSteering(personaje.steeringOutput);
    }

    private void applySteering (SteeringAcceleration<Vector2> steering, float time) {
        // Update position and linear velocity. Velocity is trimmed to maximum speed
        this.position.mulAdd(linearVelocity, time);
        this.linearVelocity.mulAdd(steering.linear, time).limit(this.getMaxLinearSpeed());

        // Update orientation and angular velocity
            this.orientation += angularVelocity * time;
            this.angularVelocity += steering.angular * time;
    }


    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxLinearAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        body.setTransform(getPosition(), orientation);
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector,angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

    public Body getBody() {
        return body;
    }

    public void setBehavior(SteeringBehavior<Vector2> behavior) {
        this.behavior = behavior;
    }

    public SteeringBehavior<Vector2> getBehavior() {
        return behavior;
    }

//https://github.com/codeandcoke/jbombermanx
}
