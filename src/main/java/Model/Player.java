package Model;

import Observers.PlayerObservable;
import Observers.PlayerObserver;

import java.util.ArrayList;
import java.util.List;

public class Player implements PlayerObservable {
    private final List<PlayerObserver> observers = new ArrayList<>();

    private ArrayList<PlayerCard> hand = new ArrayList<>();
    private Role role;
    private City currentCity;
    private boolean readyToStart = false;
    private String playerName;
    private int actions = 4;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public void endTurn() {
        resetActions();
    }

    public void addCardToHand(PlayerCard card) {
        hand.add(card);
    }

    public void removeCardFromHand(PlayerCard card) {
        hand.removeIf(playerCard -> playerCard == card);
    }

    public ArrayList<PlayerCard> getHand() {
        return hand;
    }

    public boolean checkCardInHand(PlayerCard card) {
        for (PlayerCard nextCard : hand) {
            if (nextCard == card) {
                return true;
            }
        }
        return false;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City city) {
        currentCity = city;
    }

    public boolean getReadyToStart() {
        return readyToStart;
    }

    public void setReadyToStart(boolean readyToStart) {
        this.readyToStart = readyToStart;
        notifyAllObservers();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void resetActions() {
        actions = 4;
    }

    public void decrementActions() {
        actions--;
    }

    @Override
    public void register(PlayerObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(PlayerObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (PlayerObserver observer : observers) {
            observer.update(this);
        }
    }
}
