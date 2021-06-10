package Model;

import Observers.LobbyObservable;
import Observers.LobbyObserver;

import java.util.ArrayList;
import java.util.List;

public class Lobby implements LobbyObservable {
    private final List<LobbyObserver> observers = new ArrayList<>();

    private boolean joinable;
    private final ArrayList<Player> players;
    private final String passwd;
    private final int MAX_LOBBY_SIZE = 4;

    public Lobby(Player player, String lobbyCode) {
        this.joinable = true;
        this.players = new ArrayList<>();
        addPlayer(player);
        this.passwd = lobbyCode;
        notifyAllObservers();
    }

    public Lobby(String passwd) {
        this.joinable = true;
        this.players = new ArrayList<>();
        this.passwd = passwd;
        notifyAllObservers();
    }

    public ArrayList<Player> getPlayers() {
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
            playerNames.add(player.getPlayerName());
        }
        return playerNames;
    }

    @Override
    public ArrayList<Boolean> getPlayerReadyToStart() {
        ArrayList<Boolean> playerReadyToStart = new ArrayList<>();
        for (Player player : players) {
            playerReadyToStart.add(player.getReadyToStart());
        }
        return playerReadyToStart;
    }

    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
        notifyAllObservers();
    }

    public void addPlayer(Player player) {
        if (joinable) {
            this.players.add(player);
            if (players.size() == 4) {
                setJoinable(false);
            }
        }
        notifyAllObservers();
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        notifyAllObservers();
    }

    public void startGame() {
        if (players.size() > 1) {
            for (Player player : players) {
                if (!player.getReadyToStart()) {
                    return;
                }
            }
            //Todo: start game
        }
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
