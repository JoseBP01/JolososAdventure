package com.mygdx.game;

public class UpdatePosition {
    String id;
    Position position;

    public UpdatePosition(String id ,float x, float y) {
        this.id = id;
        position = new Position(x, y);
    }


    static class Position {
        float x, y;

        public Position(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}