package com.mygdx.game;

import com.badlogic.gdx.Game;

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
	}
}
