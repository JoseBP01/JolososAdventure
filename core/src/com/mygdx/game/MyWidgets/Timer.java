package com.mygdx.game.MyWidgets;

public class Timer {
    public static float gameTime;

    public float frecuencia;
    public float alarma;
    public boolean repite;
    public boolean activo;

    public Timer(float frecuencia) {
        this.frecuencia = frecuencia;
        repite = true;
        activo = true;
    }

    public Timer(float frecuencia, boolean repite) {
        this.frecuencia = frecuencia;
        this.repite = repite;
        activo = true;
    }

    public Timer(float frecuencia, boolean repite, boolean activo) {
        this.frecuencia = frecuencia;
        this.repite = repite;
        this.activo = activo;
    }

    public boolean pita(){
        if(activo){
            if(gameTime >= alarma){
                if(repite){
                    alarma = gameTime + frecuencia;
                } else {
                    activo = false;
                }
                return true;
            }
        }
        return false;
    }
}
