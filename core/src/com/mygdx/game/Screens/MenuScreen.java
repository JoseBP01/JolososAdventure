package com.mygdx.game.Screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.MyWidgets.MyTextButton;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class MenuScreen extends MyScreen {

    Table table;
    NakamaSessionManager nakamaSessionManager;

    public MenuScreen(JadventureMain game, NakamaSessionManager nakamaSessionManager) {
        super(game);
        this.nakamaSessionManager = nakamaSessionManager;
    }

    @Override
    public void show(){
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        MyTextButton unirseALaPartida = new MyTextButton("Unirse a la partida");
        MyTextButton logOut = new MyTextButton("Cerrar Sesion");

        table.add(unirseALaPartida);
        table.row();
        table.add(logOut);
        unirseALaPartida.onClick(() -> {
            nakamaSessionManager.unirsePartida();
            nakamaSessionManager.unirseChat();
            setScreen(new GameScreen(game,nakamaSessionManager));
        });

        logOut.onClick(() -> System.exit(1));
    }


    @Override
    public void render(float delta) {
        super.render(delta);
    }
}