package com.mygdx.game.Actors;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemigo extends Actor {

    public Enemigo(Fixture fixture) {


    }


//    public Vector2 position = new Vector2();
//    public Vector2 velocity = new Vector2();
//    public float width;
//    public float height;
//    public boolean grounded;
//
//    public EnemigoBase(World world, float x, float y) {
//        setSize(40, 40);
//        setVelocity(4,0);
//
//    }
//    public void setVelocity(float x, float y){
//        velocity.x = x;
//        velocity.y = y;
//    }
//
//    public void onCollision(Collider.Side side, Vector2 point){
//        if (side == Collider.Side.LEFT) {
//            velocity.x *= -1;
//            position.x = point.x - width;
//        } else if (side == Collider.Side.RIGHT) {
//            velocity.x *= -1;
//            position.x = point.x;
//        } else if (side == Collider.Side.BOTTOM) {
//            velocity.y = 0;
//            position.y = point.y - height;
//        } else if (side == Collider.Side.TOP) {
//            velocity.y = 0;
//            position.y = point.y;
//            grounded = true;
//        }
//    }
}
