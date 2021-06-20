package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Thimo van Velzen
 */

public class DatabaseData {

    private int currentPlayerIndex = 0;
    private boolean GameStarted = false;
    private boolean Joinable = true;
    private boolean gameWon = false;
    private boolean gameLost = false;

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    private final List<Player> players = Arrays.asList(new Player[4]);
    private Gameboard gameboard;

    public DatabaseData() {}

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
        players.set(0, player1);
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
        players.set(1, player2);
    }

    public Player getPlayer3() {
        return player3;
    }

    public void setPlayer3(Player player3) {
        this.player3 = player3;
        players.set(2, player3);
    }

    public Player getPlayer4() {
        return player4;
    }

    public void setPlayer4(Player player4) {
        this.player4 = player4;
        players.set(3, player4);
    }

    public boolean isGameStarted() {
        return GameStarted;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
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

    public List<Player> returnPlayers() {
        return players;
    }

    public Gameboard getGameboard() {
        return gameboard;
    }

    public void setGameboard(Gameboard gameboard) {
        this.gameboard = gameboard;
    }
}
