package com.mygdx.game.Screens;

import com.mygdx.game.MyWidgets.MyScreen;
import com.mygdx.game.JadventureMain;
import com.mygdx.game.NakamaController.NakamaSessionManager;

public class SettingScreen extends MyScreen {
    NakamaSessionManager nakamaSessionManager;

    public SettingScreen(JadventureMain game, NakamaSessionManager nakamaSessionManager) {
        super(game);
        this.nakamaSessionManager = nakamaSessionManager;
    }
}
