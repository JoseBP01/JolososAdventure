package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyWidgets.Timer;
import com.mygdx.game.Screens.LoadScreen;

public class JadventureMain extends Game {
	public Assets assets;

	@Override
	public void create () {

		assets = new Assets();
		assets.load();

		setScreen(new LoadScreen(this));

	}

	@Override
	public void render () {
		super.render();
		Timer.gameTime += Gdx.graphics.getDeltaTime();
	}
}
