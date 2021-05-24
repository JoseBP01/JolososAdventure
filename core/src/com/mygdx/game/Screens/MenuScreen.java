package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyLabel;
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
        MyTextButton ajustes = new MyTextButton("Ajustes");
        MyTextButton logOut = new MyTextButton("Cerrar Sesion");
        MyLabel error = new MyLabel("", Color.RED);

        table.add(unirseALaPartida);
        table.row();
        table.add(ajustes);
        table.row();
        table.add(logOut);
        unirseALaPartida.onClick(() -> {
            nakamaSessionManager.unirsePartida();
            nakamaSessionManager.unirseChat();
            setScreen(new GameScreen(game,nakamaSessionManager));
        });

        ajustes.onClick(() -> setScreen(new SettingScreen(game,nakamaSessionManager)));

        logOut.onClick(() -> System.exit(1));
    }


    @Override
    public void render(float delta) {
        super.render(delta);
    }
    //    Stage stage;

//    @Override
//    public void show() {
//        super.show();
//        ImageButton.ImageButtonStyle buttonStartStyle = new ImageButton.ImageButtonStyle();
//        buttonStartStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("boton_start.png")));
//        ImageButton buttonStart = new ImageButton(buttonStartStyle);
//
//        buttonStart.setPosition(200, 200);
//        buttonStart.setSize(64*3, 64*3);
//        buttonStart.addListener(new InputListener(){
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
////                setScreen(new GameScreen(game));
//                return true;
//            }
//        });
//
//        Gdx.input.setInputProcessor(stage = new Stage());
//        stage.addActor(buttonStart);
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        stage.act();
//        stage.draw();
//    }
}
