package Model;

import Observers.LobbyObservable;
import Observers.LobbyObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lobby implements LobbyObservable {
    private List<LobbyObserver> observers = new ArrayList<>();

    private boolean joinable;
    private List<Player> players;
    private final String password;
    private final int MAX_LOBBY_SIZE = 4;

    public Lobby(String password) {
        this.joinable = true;
        players = Arrays.asList(new Player[4]);
        this.password = password;
        notifyAllObservers();
    }

    public List<Player> getPlayers() {
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
            }
        }

        return playerReadyToStart;
    }

    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
        notifyAllObservers();
    }

    public String getPassword() {
        return password;
    }

    public boolean checkLobbyCode(String code) {
        return password.equals(code);
    }

    public int getMaxLobbySize() {
        return MAX_LOBBY_SIZE;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
        notifyAllObservers();
    }

    public void unregister() {
        observers = new ArrayList<>();
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
