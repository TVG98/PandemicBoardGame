package Model;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players = new ArrayList<Player>();
    private int currentPlayerIndex = 0;
    private boolean lost = false;
    private boolean won = false;
    private Player currentPlayer;

    public Game(ArrayList<Player> players) {
        this.players = players;
        currentPlayer = this.players.get(this.currentPlayerIndex);
    }

    public void nextTurn() {
        currentPlayerIndex++;
        currentPlayer = players.get(currentPlayerIndex);
    }

    public void setLost() {
        lost = true;
        won = false;
    }

    public void setWon() {
        won = true;
        lost = false;
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
