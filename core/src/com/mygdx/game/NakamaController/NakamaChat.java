package com.mygdx.game.NakamaController;

import com.heroiclabs.nakama.AbstractSocketListener;
import com.heroiclabs.nakama.MatchData;
import com.heroiclabs.nakama.SocketListener;

public class NakamaChat {

    public void recibirMensajePrueba(){
        SocketListener listener = new AbstractSocketListener() {
            @Override
            public void onMatchData(final MatchData matchData) {
                System.out.format("Received match data %s with opcode %d", matchData.getData(), matchData.getOpCode());
            }
        };
    }
}
