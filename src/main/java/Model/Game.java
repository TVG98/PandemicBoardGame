package Model;

import java.util.ArrayList;

public class Game {

    private final Player[] players;
    private int currentPlayerIndex = 0;
    private boolean lost = false;
    private boolean won = false;
    private Player currentPlayer;

    public Game(Player[] players) {
        this.players = players;
        currentPlayer = this.players[this.currentPlayerIndex];
    }

    public void nextTurn() {
        currentPlayerIndex++;
        currentPlayer = players[currentPlayerIndex % players.length];
    }

    public void setLost() {
        lost = true;
    }

    public void setWon() {
        won = true;
    }

    public ArrayList<Player> getPlayersInCity(City city) {
        ArrayList<Player> playersInCity = new ArrayList<>();

        for (Player nextPlayer : players) {
            if (nextPlayer.getCurrentCity() == city) {
                playersInCity.add(nextPlayer);
            }
        }

        return playersInCity;
    }

    public int getPlayerAmount() {
        return this.players.length;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player[] getPlayers() {
        return players;
    }

    public boolean getLost() {
        return lost;
    }

    public boolean getWon() {
        return won;
    }
}
