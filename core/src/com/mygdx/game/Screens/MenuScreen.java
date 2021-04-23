package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.MyWidgets.MyLabel;
import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.MyWidgets.MyTextButton;
import com.mygdx.game.NakamaController.NakamaMatchMaking;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class MenuScreen extends MyScreen {

    Table table;
    boolean matchCreated;
    boolean joinGame;
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

        MyTextButton create = new MyTextButton("CREATE GAME");
        MyTextButton join = new MyTextButton("JOIN GAME");
        MyLabel error = new MyLabel("", Color.RED);

        table.add(create);
        table.row();
        table.add(join);
        table.row();
        table.add(error);
        create.onClick(() -> {
            nakamaSessionManager.crearPartida(new NakamaMatchMaking.Matcheado() {
                @Override
                public void PartidaEncontrada() {
                    setScreen(new GameScreen(game,nakamaSessionManager));
                }

                @Override
                public void SinPartida() {
                    System.out.println("No se puede crear partida");
                }
            });
        });

        join.onClick(() -> {
            nakamaSessionManager.matchMaking.EntrarQueue(new NakamaMatchMaking.TicketCola() {
                @Override
                public void TicketCreado() {
                    System.out.println("Ticket Creado");
                    nakamaSessionManager.matchMaking.recibirMatchMaker();
                    nakamaSessionManager.matchMaking.unirseAlMatchMaking(new NakamaMatchMaking.Matcheado() {
                        @Override
                        public void PartidaEncontrada() {
                            setScreen(new GameScreen(game,nakamaSessionManager));
                            System.out.println("unido Partida");
                        }

                        @Override
                        public void SinPartida() {
                            System.out.println("sin partida");
                        }
                    });
                }

                @Override
                public void NoHayTicket() {
                    System.out.println("sin ticket");

                }
            });
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (matchCreated){
//            setScreen(new GameScreen());
        }
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
