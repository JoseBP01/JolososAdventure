package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
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
import com.mygdx.game.NakamaController.NakamaStorage;

public class Personaje extends MyActor implements Steerable<Vector2> {

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

    private float vx = 5;
    private float vy = 5;
    private State estado;
    private Direccion direccion;
    private int vidas = 4;
    private NakamaStorage ns;
    private MyWorld myWorld;
    private boolean inventarioShow = false;

    SteeringAcceleration<Vector2>  steeringOutput = new SteeringAcceleration<>(new Vector2());

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

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    //MOVIMIENTO
    public void izquierda() {
        setDireccion(Direccion.Izquerda);
        setState(State.Caminando);
        body.setLinearVelocity(-300,0);
        steeringOutput.linear.x = -300;
        steeringOutput.linear.y = -0;

    }
    public void derecha(){
        setDireccion(Direccion.Derecha);
        setState(Personaje.State.Caminando);
        body.setLinearVelocity(300,0);
        steeringOutput.linear.x = 300;
        steeringOutput.linear.y = 0;


    }
    public void arriba(){
        setDireccion(Direccion.Arriba);
        setState(State.Caminando);
        body.setLinearVelocity(0,300);
        steeringOutput.linear.y = 300;
        steeringOutput.linear.x = 0;

    }
    public void abajo(){
        setDireccion(Direccion.Abajo);
        setState(State.Caminando);
        body.setLinearVelocity(0,-300);
        steeringOutput.linear.y = -300;
        steeringOutput.linear.x = 0;
    }

    //ATAQUE
    public void ataqueIzquierda(){
        setDireccion(Direccion.Izquerda);
        setState(State.Ataque);
        body.setLinearVelocity(-0,0);
    }
    public void ataqueDerecha(){
        setDireccion(Direccion.Derecha);
        setState(State.Ataque);
        body.setLinearVelocity(0,0);
    }
    public void ataqueArriba(){
        setDireccion(Direccion.Arriba);
        setState(State.Ataque);
        body.setLinearVelocity(0,0);

    }
    public void ataqueAbajo(){
        setDireccion(Direccion.Abajo);
        setState(State.Ataque);
        body.setLinearVelocity(0,0);

    }

    //CURAR
    public void curarIzquierda(){
        setDireccion(Direccion.Izquerda);
        setState(State.Curar);
        body.setLinearVelocity(0,0);
    }
    public void curarDerecha(){
        setDireccion(Direccion.Derecha);
        setState(State.Curar);
        body.setLinearVelocity(0,0);
    }
    public void curarArriba(){
        setDireccion(Direccion.Arriba);
        setState(State.Curar);
        body.setLinearVelocity(0,0);
    }
    public void curarAbajo(){
        setDireccion(Direccion.Abajo);
        setState(State.Curar);
        body.setLinearVelocity(0,0);
    }

    //Teclas asignadas
    public void manejarTeclas() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            setPosition(549.45f, 1010.21f,0);
            body.setTransform(549.45f, 1010.21f,0);
//            setPosition(0, 0,0);
//            body.setTransform(0, 0,0);
        }

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
        }else if (Gdx.input.isKeyJustPressed(Input.Keys.I)){
            System.out.println("i");
            if (ns != null && myWorld != null && !inventarioShow){
                myWorld.showObjetosInventario(ns.getObjetosInventario());
                inventarioShow = true;
            }else if (ns != null && myWorld != null && inventarioShow){
                myWorld.hideObjetos();
                inventarioShow = false;
            }
        }else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            if (direccion==Direccion.Izquerda){
                ataqueIzquierda();
            }
            else if (direccion==Direccion.Derecha){
                ataqueDerecha();
            }
            else if (direccion==Direccion.Arriba){
                ataqueArriba();
            }
            else if (direccion==Direccion.Abajo){
                ataqueAbajo();
            }
        }else if (Gdx.input.isKeyPressed(Input.Keys.E)){
            if (direccion==Direccion.Izquerda){
                curarIzquierda();
            }
            else if (direccion==Direccion.Derecha){
                curarDerecha();
            }
            else if (direccion==Direccion.Arriba){
                curarArriba();
            }
            else if (direccion==Direccion.Abajo){
                curarAbajo();
            }
        }else{
            setState(Personaje.State.Quieto);
            body.setLinearVelocity(0,0);
            steeringOutput.linear.x = 0;
            steeringOutput.linear.y = 0;

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

    public NakamaStorage getNs() {

        return ns;
    }

    public void setNs(NakamaStorage ns) {
        this.ns = ns;
    }

    public MyWorld getMyWorld() {
        return myWorld;
    }

    public void setMyWorld(MyWorld myWorld) {
        this.myWorld = myWorld;
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
        return 0;
    }

    @Override
    public boolean isTagged() {
        return false;
    }

    @Override
    public void setTagged(boolean tagged) {

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
        return 0;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {

    }

    @Override
    public float getMaxLinearAcceleration() {
        return 0;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
    }

    @Override
    public float getMaxAngularSpeed() {
        return 0;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
    }

    @Override
    public float getMaxAngularAcceleration() {
        return 0;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
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


    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }


    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector,angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return this;
    }

    public Body getBody() {
        return body;
    }

    public State getState() {
        return estado;
    }
}
