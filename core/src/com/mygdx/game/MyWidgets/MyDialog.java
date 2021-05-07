package com.mygdx.game.MyWidgets;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Assets;

public class MyDialog extends Dialog {

    public MyDialog(String title, String text, String yess, boolean yesb, String nos, boolean nob, float width, float height){
        super(title, Assets.uiSkin, "dialog");
        text(text);
        button(yess, yesb); //sends "true" as the result
        button(nos, nob); //sends "false" as the result
        setSize(width, height);

    }

    public MyDialog(String title, Skin skin, float width, float height) {
        super(title, skin);
        setSize(width, height);

    }

    public MyDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public MyDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    public void update(OrthographicCamera camera) {
        setPosition(camera.position.x-getWidth()/2, 0);

    }
}
