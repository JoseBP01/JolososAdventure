package com.mygdx.game.NakamaController;

import com.heroiclabs.nakama.AbstractSocketListener;
import com.heroiclabs.nakama.MatchData;
import com.heroiclabs.nakama.SocketClient;
import com.heroiclabs.nakama.SocketListener;

import java.nio.charset.StandardCharsets;

public class NakamaChat {

    private SocketClient socket;

    public NakamaChat(SocketClient socket) {
        this.socket = socket;
    }

    public void recibirMensajePrueba(){
        SocketListener listener = new AbstractSocketListener() {
            @Override
            public void onMatchData(final MatchData matchData) {
                System.out.format("Received match data %s with opcode %d", matchData.getData(), matchData.getOpCode());
            }
        };
    }

    public void enviarMensajePrueba(String idPartida){
        int opCode = 1;
        String data = "{\"message\":\"Hello world\"}";
        socket.sendMatchData(idPartida, opCode, data.getBytes(StandardCharsets.UTF_8));
    }
}
