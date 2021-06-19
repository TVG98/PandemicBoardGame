package Model;

import Observers.GameObservable;
import Observers.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class Game implements GameObservable {
    private final ArrayList<GameObserver> observers = new ArrayList<>();

    private List<Player> players;
    private int currentPlayerIndex = 0;
    private boolean lost = false;
    private boolean won = false;
    private Player currentPlayer;

    public Game(List<Player> players) {
        this.players = players;
        currentPlayer = this.players.get(currentPlayerIndex);
    }

    public void nextTurn() {
        currentPlayer = null;
        while (currentPlayer == null) {
            currentPlayerIndex++;
            currentPlayer = players.get(currentPlayerIndex % players.size());
        }
        //todo update currentplayerindex && to server
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
        return players.size();
    }

    public void updatePlayers(List<Player> players) {
        this.players = players;
        notifyAllObservers();
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public List<Player> getPlayers() {
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
        notifyAllObservers();
    }

    @Override
    public void notifyAllObservers() {
        for (GameObserver observer : observers) {
            observer.update(this);
        }
    }
}
