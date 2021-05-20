package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyLabel;
import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.MyWidgets.MyTextButton;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class GameOverScreen extends MyScreen {
    Table table;
    NakamaSessionManager nakamaSessionManager;


    public GameOverScreen(JadventureMain game,NakamaSessionManager nakamaSessionManager) {
        super(game);
        this.nakamaSessionManager = nakamaSessionManager;

    }

    @Override
    public void show(){
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        MyTextButton create = new MyTextButton("Volver a jugar");
        MyTextButton join = new MyTextButton("Salir al menu");
        MyLabel error = new MyLabel("", Color.RED);

        table.add(create);
        table.row();
        table.add(join);
        table.row();
        table.add(error);
        create.onClick(() -> {
            setScreen(new GameScreen(game,nakamaSessionManager));
        });

        join.onClick(() -> {
            setScreen(new MenuScreen(game,nakamaSessionManager));
        });
    }
}
