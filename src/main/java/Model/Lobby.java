package Model;

import java.util.ArrayList;

public class Lobby {

    private boolean joinable;
    private ArrayList<Player> players;

    public Lobby(boolean joinable, ArrayList<Player> players) {
        this.joinable = joinable;
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public void startGame() {

    }

    public boolean checkLobbyCode() {
        return false;
    }
}
