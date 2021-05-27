package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
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
    private static final Animation<TextureRegion> animacionCaminarIzquierda = Assets.getAnimation("animIzquirdaEnemy", 0.2f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> animacionCaminarDerecha = Assets.getAnimation("animDerechaEnemy", 0.2f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> animacionCaminarArriba = Assets.getAnimation("animArribaEnemy", 0.2f, Animation.PlayMode.LOOP);
    private static final Animation<TextureRegion> animacionCaminarAbajo = Assets.getAnimation("animAbajoEnemy", 0.2f, Animation.PlayMode.LOOP);
    //Animacion Ataque
    private static final Animation<TextureRegion> ataqueIzquierda = Assets.getAnimation("animAtaqueEnemy", 0.1f, Animation.PlayMode.NORMAL);

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
    public static SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());
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

        currentAnimation = animacionCaminarAbajo;
        fixture.getFilterData().categoryBits = MyWorld.ENEMIGO_BIT;
        fixture.getFilterData().maskBits = MyWorld.PERSONAJE_BIT;
        estado = State.Caminando;
        direccion = Direccion.Abajo;
        setHeight(((Float) mapObject.getProperties().get("height")* Config.UNIT_SCALE));
        setWidth((Float) mapObject.getProperties().get("width")* Config.UNIT_SCALE);

        this.independentFacing = independentFacing;
        this.boundingRadius = boundingRadius;

        maxLinearSpeed = 100;
        maxLinearAcceleration=100;
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
            case Ataque:
                switch (direccion){
                    case Izquerda:
                    case Abajo:
                    case Arriba:
                    case Derecha:
                        currentAnimation = ataqueIzquierda;
                        break;
                }
                break;
            default: currentAnimation = animacionCaminarIzquierda;
                break;
        }
    }

    public void update(float deltaTime) {
        if (behavior != null) {
            // Calculate steering acceleration
            applySteering(steeringOutput, deltaTime);

            // Apply steering acceleration
            applySteering(steeringOutput, deltaTime);
        }

        wrapAround(Gdx.graphics.getWidth() / Config.PPM, Gdx.graphics.getHeight() / Config.PPM);
    }

    protected void applySteering(SteeringAcceleration<Vector2> steering, float deltaTime) {
        boolean anyAccelerations = false;

        if (!steeringOutput.linear.isZero()) {
            body.applyForceToCenter(steeringOutput.linear, true);
            anyAccelerations = true;
        }
        if (independentFacing) {
            if (steeringOutput.angular != 0) {
                body.applyTorque(steeringOutput.angular, true);
                anyAccelerations = true;
            }
        } else {
            Vector2 linVel = getLinearVelocity();
            if (!linVel.isZero(getZeroLinearSpeedThreshold())) {
                float newOrientation = vectorToAngle(linVel);
                body.setAngularVelocity((newOrientation - getAngularVelocity()) * deltaTime); // this is superfluous if independentFacing is always true
                body.setTransform(body.getPosition(), newOrientation);
            }
        }

        if (anyAccelerations) {

            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            float maxLinearSpeed = getMaxLinearSpeed();
            if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
                body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
            }

            float maxAngVelocity = getMaxAngularSpeed();
            if (body.getAngularVelocity() > maxAngVelocity) {
                body.setAngularVelocity(maxAngVelocity);
            }
        }
    }

    protected void wrapAround(float maxX, float maxY) {
        float k = Float.POSITIVE_INFINITY;
        Vector2 pos = body.getPosition();

        if (pos.x > maxX) {
            k = pos.x = 0.0f;
        }

        if (pos.x < 0){
            k = pos.x = maxX;
        }

        if (pos.y < 0){
            k = pos.y = maxY;
        }

        if (pos.y > maxY){
            k = pos.y = 0.0f;
        }

        if (k != Float.POSITIVE_INFINITY){
            body.setTransform(pos, body.getAngle());
        }
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
        this.maxAngularAcceleration = maxAngularAcceleration;
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
