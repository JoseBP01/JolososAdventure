package com.mygdx.game.NakamaController;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

class Utils {
    static JsonReader jsonReader = new JsonReader();
}

class Position {
    String id;
    float x, y;

    public Position() {
    }

    public Position(String id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    List<Position> fromJson(String json) {
        List<Position> posiciones = new ArrayList<>();
        JsonValue data = Utils.jsonReader.parse(json);

        for (int i = 0; i < data.get("pos").size; i++) {
            id = data.get("pos").get(i).name;
            x = data.get("pos").get(i).getFloat("x");
            y = data.get("pos").get(i).getFloat("y");
            posiciones.add(new Position(id, x, y));
        }

        return posiciones;
    }
}

