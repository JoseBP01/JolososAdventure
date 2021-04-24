package com.mygdx.game.NakamaController;

import com.heroiclabs.nakama.*;

import java.util.concurrent.ExecutionException;

public class NakamaMatchMaking {

    private String[] idPartida = new String[1];
    private Session session;

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
    public void EntrarQueuePartidaCreada(TicketCola ticketCola) {
        try {
            MatchmakerTicket matchmakerTicket = socket.addMatchmaker(2,4,"*").get();
            ticket = matchmakerTicket;
            ticketCola.TicketCreado();

        } catch (InterruptedException | ExecutionException e) {
            ticketCola.NoHayTicket();
            e.printStackTrace();
        }
    }

    public void EntrarQueue(TicketCola ticketCola) {
        SocketListener listener = new AbstractSocketListener() {
            @Override
            public void onChannelMessage(final com.heroiclabs.nakama.api.ChannelMessage message) {
                System.out.format("Received a message on channel %s", message.getChannelId());
                System.out.format("Message content: %s", message.getContent());
            }
        };

        try {
            socket.connect(session,listener).get();
            System.out.println("Socket connected successfully.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            MatchmakerTicket matchmakerTicket = socket.addMatchmaker(2,4,"*").get();
            ticket = matchmakerTicket;
            ticketCola.TicketCreado();

        } catch (InterruptedException | ExecutionException e) {
            ticketCola.NoHayTicket();
            e.printStackTrace();
        }
    }

    public void unirseAlMatchMaking(Matcheado matcheado){
        SocketListener listener = new AbstractSocketListener() {
            @Override
            public void onMatchmakerMatched(final MatchmakerMatched matched) {
                try {
                    socket.joinMatchToken(matched.getToken()).get();
                    matcheado.PartidaEncontrada();

                } catch (Exception e) {
                    e.printStackTrace();
                    matcheado.SinPartida();
                }
            }
        };
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
