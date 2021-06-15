package Model;

import Observers.Observable;
import Observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class Game implements Observable {
    private final List<Observer> observers = new ArrayList<>();

    private Player[] players;
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
        notifyAllObservers();
    }

    public void setLost() {
        lost = true;
        won = false;
        notifyAllObservers();
    }

    public void setWon() {
        won = true;
        lost = false;
        notifyAllObservers();
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

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
