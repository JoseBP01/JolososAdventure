package com.mygdx.game.MyWidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Assets;

;

public class MyLabel extends Label {
    public MyLabel(String text){
        super(text , Assets.uiSkin);
    }

    public MyLabel(String text, Color color){
        this(text);
        setColor(color);
    }
}
