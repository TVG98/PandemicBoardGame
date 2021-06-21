package Model;

import Observers.LobbyObservable;
import Observers.LobbyObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Describes a lobby in the application.
 * @author Thimo van Velzen, Tom van Gogh
 */

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

    /**
     * @author Tom van Gogh
     */
    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
        notifyAllObservers();
    }

    /**
     * @author Tom van Gogh
     */
    public String getPassword() {
        return password;
    }

    /**
     * @author Thimo van Velzen
     */
    public boolean checkLobbyCode(String code) {
        return password.equals(code);
    }

    /**
     * @author Thimo van Velzen
     */
    public int getMaxLobbySize() {
        return MAX_LOBBY_SIZE;
    }

    /**
     * @author Thimo van Velzen
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
        notifyAllObservers();
    }

    /**
     * @author Tom van Gogh
     */
    @Override
    public void register(LobbyObserver observer) {
        observers.add(observer);
    }

    /**
     * @author Tom van Gogh
     */
    @Override
    public void unregister(LobbyObserver observer) {
        observers.remove(observer);
    }

    /**
     * @author Tom van Gogh
     */
    @Override
    public void notifyAllObservers() {
        for (LobbyObserver observer : observers) {
            observer.update(this);
        }
    }
}
