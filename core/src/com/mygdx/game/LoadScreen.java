package com.mygdx.game;

import com.mygdx.game.mywidgets.MyScreen;

public class LoadScreen extends MyScreen {

    public LoadScreen(JadventureMain si) {
        super(si);
    }

    @Override
    public void render(float delta) {
        if(!assets.update()){
            return;
        }

        setScreen(new GameScreen(game));
    }
}
