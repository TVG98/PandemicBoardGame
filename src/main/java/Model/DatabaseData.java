package Model;

import java.util.Arrays;
import java.util.List;

public class DatabaseData {

    private boolean GameStarted = false;
    private boolean Joinable = true;
    private List<Player> players = Arrays.asList(new Player[4]);
    private Gameboard gameboard;

    public DatabaseData() {}

    public boolean isGameStarted() {
        return GameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        GameStarted = gameStarted;
    }

    public boolean isJoinable() {
        return Joinable;
    }

    public void setJoinable(boolean joinable) {
        Joinable = joinable;
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void setPlayer(int index, Player player) {
        players.set(index, player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Gameboard getGameboard() {
        return gameboard;
    }

    public void setGameboard(Gameboard gameboard) {
        this.gameboard = gameboard;
    }
}
