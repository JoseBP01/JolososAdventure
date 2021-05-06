package com.mygdx.game.NakamaController;

import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroiclabs.nakama.PermissionRead;
import com.heroiclabs.nakama.PermissionWrite;
import com.heroiclabs.nakama.StorageObjectId;
import com.heroiclabs.nakama.StorageObjectWrite;
import com.heroiclabs.nakama.api.StorageObjectAcks;
import com.heroiclabs.nakama.api.StorageObjects;
import com.mygdx.game.MyWidgets.MyWorld;

import java.util.concurrent.ExecutionException;

public class NakamaStorage {
    NakamaSessionManager nakamaSessionManager;

    public NakamaStorage(NakamaSessionManager nakamaSessionManager) {
        this.nakamaSessionManager = nakamaSessionManager;
    }

    public void pruebaStorage(){
        String saveGame = "{ \"progress\": 50 }";
        String myStats = "{ \"skill\": 24 }";
        StorageObjectWrite saveGameObject = new StorageObjectWrite("saves", "savegame", saveGame, PermissionRead.OWNER_READ, PermissionWrite.OWNER_WRITE);
        StorageObjectWrite statsObject = new StorageObjectWrite("stats", "skills", myStats, PermissionRead.OWNER_READ, PermissionWrite.OWNER_WRITE);
        StorageObjectAcks acks = null;
        try {
            acks = nakamaSessionManager.client.writeStorageObjects(nakamaSessionManager.session, saveGameObject, statsObject).get();
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
