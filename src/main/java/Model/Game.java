package Model;

import Observers.Observable;
import Observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class Game implements Observable {
    private final List<Observer> observers = new ArrayList<>();

    private ArrayList<Player> players;
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
        currentPlayer = players.get(currentPlayerIndex % players.size());
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
        return this.players.size();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Player> getPlayers() {
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
