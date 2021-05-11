package com.mygdx.game.NakamaController;

import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroiclabs.nakama.PermissionRead;
import com.heroiclabs.nakama.PermissionWrite;
import com.heroiclabs.nakama.StorageObjectId;
import com.heroiclabs.nakama.StorageObjectWrite;
import com.heroiclabs.nakama.api.StorageObject;
import com.heroiclabs.nakama.api.StorageObjectAcks;
import com.heroiclabs.nakama.api.StorageObjectList;
import com.heroiclabs.nakama.api.StorageObjects;
import com.mygdx.game.Actors.MyWorld;
import com.mygdx.game.Actors.Objeto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NakamaStorage {
    NakamaSessionManager nakamaSessionManager;

    public NakamaStorage(NakamaSessionManager nakamaSessionManager) {
        this.nakamaSessionManager = nakamaSessionManager;
    }

    public void crearObjeto(String nombre, float precio, String descripcion){
        String data = "{\"nombre\" : \""+nombre+"\", "+"\"precio\": \""+precio+"\","+"\"descripcion\" : \""+descripcion+"\""+"}";
        StorageObjectWrite saveGameObject = new StorageObjectWrite("Objetos", nombre, data, PermissionRead.PUBLIC_READ, PermissionWrite.OWNER_WRITE);
        StorageObjectAcks acks = null;
        try {
            acks = nakamaSessionManager.client.writeStorageObjects(nakamaSessionManager.session, saveGameObject).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (acks != null){
            System.out.format("Stored objects %s", acks.getAcksList());
        }
    }

    public List<Objeto> getObjetosTienda(){
        List<Objeto> objetosTienda = new ArrayList<>();
        StorageObjectList objects = null;
        String objetoJson;
        try {
            objects = nakamaSessionManager.client.listUsersStorageObjects(nakamaSessionManager.session, "Objetos", null,100).get();
            for (StorageObject object: objects.getObjectsList()){
                objetoJson = object.getValue();
                objetosTienda.add(jsonToObjeto(objetoJson));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (objects != null){

            return objetosTienda;
        }else return null;

    }

    public List<Objeto> getObjetosInventario(){
        List<Objeto> objetosInvetario = new ArrayList<>();
        StorageObjectList objects = null;
        String objetoJson;
        try {
            objects = nakamaSessionManager.client.listUsersStorageObjects(nakamaSessionManager.session, "Inventario", nakamaSessionManager.session.getUserId(),100).get();
            for (StorageObject object: objects.getObjectsList()){
                objetoJson = object.getValue();
                objetosInvetario.add(jsonToObjeto(objetoJson));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (objects != null){

            return objetosInvetario;
        }else return null;
    }

    public Objeto comprarObjeto(String nombre){
        for (Objeto objeto: getObjetosTienda()){
            if (objeto.getNombre().equals(nombre)){
                String data = "{\"nombre\" : \""+objeto.getNombre()+"\", "+"\"descripcion\": \""+objeto.getDescripcion()+"\","+"\"precio\" : \""+objeto.getPrecio()+"\""+"}";
                StorageObjectWrite saveGameObject = new StorageObjectWrite("Inventario", objeto.getNombre(), data, PermissionRead.OWNER_READ, PermissionWrite.OWNER_WRITE);
                StorageObjectAcks acks = null;
                try {
                    acks = nakamaSessionManager.client.writeStorageObjects(nakamaSessionManager.session, saveGameObject).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                if (acks != null){
                    System.out.format("Stored objects %s", acks.getAcksList());
                    return new Objeto(objeto.getNombre(),objeto.getPrecio(),objeto.getDescripcion());
                }
            }
        }
        return null;
    }

    public Objeto jsonToObjeto(String json){
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readValue(json,Objeto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Vector2 getPosicionJugador(){
        StorageObjectId objectId = new StorageObjectId("player_data");
        objectId.setKey("position_User");
        objectId.setUserId(nakamaSessionManager.session.getUserId());
        StorageObjects objects = null;
        Vector2 posicion = null;
        try {
             objects= nakamaSessionManager.client.readStorageObjects(nakamaSessionManager.session, objectId).get();
            ObjectMapper om = new ObjectMapper();
            PosicionInicio posicionInicio = om.readValue(objects.getObjects(0).getValue(),PosicionInicio.class);
            System.out.println(posicionInicio);
            float x = posicionInicio.x;
            float y = posicionInicio.y;
            MyWorld world = nakamaSessionManager.getMyWorld();
            world.personaje.setPosition(x,y);
            world.personaje.body.setTransform(x,y,0);

        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return posicion;
    }
}