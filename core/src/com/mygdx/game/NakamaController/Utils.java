package com.mygdx.game.NakamaController;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

class Utils {
    static JsonReader jsonReader = new JsonReader();
}

class Position {
    String id;
    float x, y;

    Position fromJson(String json){
        JsonValue data = Utils.jsonReader.parse(json);
        id = data.get("pos").child().name;
        x = data.get("pos").child().getFloat("x");
        y = data.get("pos").child().getFloat("y");
        return this;
    }
}