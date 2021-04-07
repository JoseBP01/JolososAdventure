package com.mygdx.game.Base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Assets;

public class NpcBase extends MyActor {

    String[] spriteNpc = {"dancerA", "dancerB"};
    int numeroSprite;
    Circle areaInteractuar;
    private float x,y,size;
    BodyDef bodyDef = new BodyDef();
    public Body body;
    Vector2 vector2 = new Vector2();

    public NpcBase(World world,float x, float y) {
        super(world,x,y);
        this.numeroSprite = (int) (Math.random()*1);
        this.x = x;
        this.y = y;
        size = 64;
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        vector2.add(getX()+16,getY()+16);
        circle.setRadius(32f);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.80f;
        fixtureDef.friction = 1.00f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
        fixtureDef.filter.categoryBits = 2;


// Create our fixture and attach it to the body
        body.createFixture(fixtureDef).setUserData(this);
        circle.dispose();
        //poner el category bits 2
    }
    private final Animation<TextureRegion> NpcA = Assets.getAnimation(spriteNpc[numeroSprite], 0.3f, Animation.PlayMode.NORMAL);


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
