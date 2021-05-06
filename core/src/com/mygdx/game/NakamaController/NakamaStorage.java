package com.mygdx.game.NakamaController;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroiclabs.nakama.PermissionRead;
import com.heroiclabs.nakama.PermissionWrite;
import com.heroiclabs.nakama.StorageObjectId;
import com.heroiclabs.nakama.StorageObjectWrite;
import com.heroiclabs.nakama.api.StorageObjectAcks;
import com.heroiclabs.nakama.api.StorageObjects;
import com.mygdx.game.Actors.MyWorld;
import com.mygdx.game.Actors.Objeto;

import java.util.concurrent.ExecutionException;

public class NakamaStorage {
    NakamaSessionManager nakamaSessionManager;

    public NakamaStorage(NakamaSessionManager nakamaSessionManager) {
        this.nakamaSessionManager = nakamaSessionManager;
    }

    public void crearObjeto(String nombre, float precio, String descripcion){
        Objeto objeto= new Objeto(nombre,precio,descripcion);
        String  json = new Json().toJson(objeto);
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
