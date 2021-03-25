package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Base.MyScreen;
import com.mygdx.game.Base.NpcBase;
import com.mygdx.game.JContactListener;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.Map;
import com.mygdx.game.Personaje.HpBarra;
import com.mygdx.game.Personaje.Personaje;

public class GameScreen extends MyScreen {
    Personaje personaje;
    HpBarra hpBarra;
    Map map;
    NpcBase npc;
    private World world;
    private SpriteBatch spriteBatch;

    public GameScreen(JadventureMain game) {
        super(game);
    }

    @Override
    public void show () {
        super.show();

        stage.addActor(map = new Map(camera));
        world = new World(new Vector2(0,0), true);

        stage.addActor(hpBarra = new HpBarra());
        map.loadObjects(this);

        world.setContactListener(new JContactListener());
    }

    public void addPersonaje(float x, float y) {
        personaje = new Personaje(world, x,y);
        stage.addActor(personaje);
    }

    @Override
    public void render (float delta) {
        super.render(delta);

        world.step(delta, 6, 2);

        camera.position.set(personaje.getX(),personaje.getY(),0);
        camera.update();

        personaje.manejarTeclas();

    }

    public void addNpc(float x, float y) {
        npc = new NpcBase(world,x, y);
        stage.addActor(npc);
    }
}
