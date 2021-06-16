package Model;

import Observers.GameObservable;
import Observers.GameObserver;

import java.util.ArrayList;

public class Game implements GameObservable {
    private final ArrayList<GameObserver> observers = new ArrayList<>();

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
        currentPlayer = null;
        while (currentPlayer == null) {
            currentPlayerIndex++;
            currentPlayer = players[currentPlayerIndex % players.length];
        }
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

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public Player[] getPlayers() {
        return players;
    }

    @Override
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    @Override
    public boolean getLost() {
        return lost;
    }

    @Override
    public boolean getWon() {
        return won;
    }

    @Override
    public void register(GameObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (GameObserver observer : observers) {
            observer.update(this);
        }
    }
}
