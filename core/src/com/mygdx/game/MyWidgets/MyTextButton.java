package com.mygdx.game.MyWidgets;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;

public class MyTextButton extends TextButton {

    public MyTextButton(String text){
        super(text, Assets.uiSkin);
    }

    public interface OnClickListener{
        void onClick();
    }

    public void onClick(OnClickListener listener) {
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                listener.onClick();
                return true;
            }
        });
    }
}
