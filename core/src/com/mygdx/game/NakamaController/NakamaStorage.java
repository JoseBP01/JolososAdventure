package com.mygdx.game.NakamaController;

import com.heroiclabs.nakama.PermissionRead;
import com.heroiclabs.nakama.PermissionWrite;
import com.heroiclabs.nakama.StorageObjectWrite;
import com.heroiclabs.nakama.api.StorageObjectAcks;

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
}
