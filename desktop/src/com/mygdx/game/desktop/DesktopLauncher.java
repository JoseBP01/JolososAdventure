package com.mygdx.game.desktop;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.JadventureMain;


public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Joloso's Adventure The Game");
		config.setWindowIcon("sprites/ataqueIzquierda_2.png");
		new Lwjgl3Application(new JadventureMain(), config);

	}
}
