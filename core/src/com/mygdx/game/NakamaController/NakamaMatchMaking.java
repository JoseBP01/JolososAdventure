package com.mygdx.game.NakamaController;

import com.heroiclabs.nakama.*;

import java.util.concurrent.ExecutionException;

public class NakamaMatchMaking {

    private String[] idPartida = new String[1];

    public interface TicketCola{
        void TicketCreado();
        void NoHayTicket();
    }

    public interface Matcheado{
        void PartidaEncontrada();
        void SinPartida();
    }

    private MatchmakerTicket ticket;
    private final SocketClient socket;

    public NakamaMatchMaking(SocketClient socket) {
        this.socket = socket;

    }

    public void EntrarQueue(TicketCola ticketCola) {
        try {
            MatchmakerTicket matchmakerTicket = socket.addMatchmaker(2,4,"*").get();
            ticket = matchmakerTicket;
            ticketCola.TicketCreado();

        } catch (InterruptedException | ExecutionException e) {
            ticketCola.NoHayTicket();
            e.printStackTrace();
        }
    }

    public String unirseAlMatchMaking(Matcheado matcheado){
        SocketListener listener = new AbstractSocketListener() {
            @Override
            public void onMatchmakerMatched(final MatchmakerMatched matched) {
                try {
                    while (idPartida[0] == null){
                        socket.joinMatchToken(matched.getToken()).get();
                        idPartida[0] = socket.joinMatchToken(matched.getToken()).get().getMatchId();
                        System.out.println(idPartida[0]);
                        if (idPartida[0] != null){
                            SocketListener joinMatch = new AbstractSocketListener() {
                                @Override
                                public void onMatchmakerMatched(final MatchmakerMatched matched) {
                                    try {
                                        socket.joinMatchToken(matched.getToken()).get();
                                        matcheado.PartidaEncontrada();
                                    } catch (InterruptedException | ExecutionException e) {
                                        e.printStackTrace();
                                        matcheado.SinPartida();
                                    }
                                }
                            };
                            matcheado.PartidaEncontrada();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    matcheado.SinPartida();
                }
            }
        };
        return idPartida[0];
    }

    public void recibirMatchMaker() {
        SocketListener listener = new AbstractSocketListener() {
            @Override
            public void onMatchmakerMatched(final MatchmakerMatched matched) {
                System.out.format("Received MatchmakerMatched message: %s", matched.toString());
//                System.out.format("Matched opponents: %s", opponents.toString());
            }
        };
    }
}
