package com.mygdx.game.NakamaController;

import com.fasterxml.jackson.annotation.JsonProperty;

class Mensaje{

    @JsonProperty("message")
    private String message;

    public Mensaje() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}