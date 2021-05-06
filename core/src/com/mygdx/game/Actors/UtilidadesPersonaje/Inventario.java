package com.mygdx.game.Actors.UtilidadesPersonaje;

import com.mygdx.game.Actors.Objeto;

import java.util.ArrayList;

public class Inventario {

    private ArrayList<Objeto> espacios = new ArrayList<>();
    private int espaciosMax = 12;

    public int espaciosUtilizados(){
        return espacios.size();
    }

    public boolean guardarObjeto(Objeto objeto){
        if (espacios.size() < espaciosMax){
            espacios.add(objeto);
            System.out.println("objeto guardado: "+ objeto);
            return true;
        }else {
            return false;
        }
    }

//    public Objeto  buscarObjeto(String nombre){
//        for (Objeto objeto: espacios){
//            if (objeto.nombre.equals(nombre)){
//                return objeto;
//            }else {
//                return null;
//            }
//        }
//        return null;
//    }
//
//    public boolean quitarObjeto(String nombre){
//        if (buscarObjeto(nombre) != null){
//            Objeto objeto = buscarObjeto(nombre);
//            System.out.println("El objeto: "+objeto.nombre+" ha sido borrado del inventario");
//            return true;
//        }else {
//            System.out.println("El objeto con nombre: "+nombre+" no existe");
//            return false;
//        }
//    }


}
