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
    private boolean readyToStart;
    private String playerName;
    private int actions = 4;

    public Player(String playerName, boolean readyToStart) {
        this.playerName = playerName;
        this.readyToStart = readyToStart;
    }

    public Player(ArrayList<PlayerCard> hand, Role role, City currentCity, boolean readyToStart, String playerName) {
        this.hand = hand;
        this.role = role;
        this.currentCity = currentCity;
        this.readyToStart = readyToStart;
        this.playerName = playerName;
    }

    public void endTurn() {
        resetActions();
        notifyAllObservers();
    }

    public void addCardToHand(PlayerCard card) {
        hand.add(card);
        notifyAllObservers();
    }

    public void removeCardFromHand(PlayerCard card) {
        hand.removeIf(playerCard -> playerCard == card);
        notifyAllObservers();
    }

    public ArrayList<PlayerCard> getHand() {
        return hand;
    }

    public boolean checkCardInHandBasedOnCity(City city) {
        for (PlayerCard nextCard : hand) {
            if (nextCard.getName().equals(city.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCardInHand(PlayerCard card) {
        for (PlayerCard nextCard : hand) {
            if (nextCard.equals(card)) {
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
        notifyAllObservers();
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City city) {
        currentCity = city;
        notifyAllObservers();
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

    public boolean actionsPlayed() {
        return actions <= 0;
    }

    public void resetActions() {
        actions = 4;
        notifyAllObservers();
    }

    public int getActionsLeft() {
        return actions;
    }

    public void decrementActions() {
        actions--;
        notifyAllObservers();
    }

    @Override
    public void register(PlayerObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (PlayerObserver observer : observers) {
            observer.update(this);
        }
    }
}
