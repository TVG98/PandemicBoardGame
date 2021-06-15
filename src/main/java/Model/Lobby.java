package Model;

import Observers.LobbyObservable;
import Observers.LobbyObserver;

import java.util.ArrayList;
import java.util.List;

public class Lobby implements LobbyObservable {
    private final List<LobbyObserver> observers = new ArrayList<>();

    private boolean joinable;
    private final Player[] players;
    private final String passwd;
    private final int MAX_LOBBY_SIZE = 4;

    public Lobby(String passwd) {
        this.joinable = true;
        this.players = new Player[4];
        this.passwd = passwd;
        notifyAllObservers();
    }

    public Player[] getPlayers() {
        return players;
    }

    @Override
    public boolean getJoinable() {
        return joinable;
    }

    @Override
    public ArrayList<String> getPlayerNames() {
        ArrayList<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            if (player != null) {
                playerNames.add(player.getPlayerName());
            } else {
                playerNames.add("");
            }
        }
        return playerNames;
    }

    @Override
    public ArrayList<Boolean> getPlayerReadyToStart() {
        ArrayList<Boolean> playerReadyToStart = new ArrayList<>();
        for (Player player : players) {
            if (player != null) {
                playerReadyToStart.add(player.getReadyToStart());
            } else {
                playerReadyToStart.add(null);
            }
        }
        return playerReadyToStart;
    }

    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
        notifyAllObservers();
    }

    public String getPassword() {
        return passwd;
    }

    public boolean checkLobbyCode(String code) {
        return this.passwd.equals(code);
    }

    public int getMaxLobbySize() {
        return MAX_LOBBY_SIZE;
    }

    public void updatePlayer(int loc, Player player) {
        System.out.println(player.getPlayerName());
        players[loc] = player;
        notifyAllObservers();
    }

    @Override
    public void register(LobbyObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(LobbyObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (LobbyObserver observer : observers) {
            observer.update(this);
        }
    }
}
