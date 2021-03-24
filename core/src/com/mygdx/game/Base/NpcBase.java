package com.mygdx.game.Base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Assets;
import com.mygdx.game.Personaje.Personaje;

public class NpcBase extends Actor {

    String[] spriteNpc = {"dancerA", "dancerB"};
    int numeroSprite;
    Circle areaInteractuar;
    private float x,y,size;

    public NpcBase() {
        this.numeroSprite = (int) (Math.random()*1);
        x = (int) (Math.random()*640+1);
        y = (int) (Math.random()*480+1);
        size = 64;
        areaInteractuar = new Circle(x,y, 50);
    }

    public void comprobarDialogo(Personaje personaje){
        if (Intersector.overlaps(areaInteractuar,personaje.getHitBox())){
            System.out.println("interaccion");
        }else System.out.println("nada");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(NpcA.getKeyFrame(0), x, y, size, size);
    }

    private final Animation<TextureRegion> NpcA = Assets.getAnimation(spriteNpc[numeroSprite], 0.3f, Animation.PlayMode.NORMAL);


}
