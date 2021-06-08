package Model;

import Observers.Observable;
import Observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class Player implements Observable {
    private final List<Observer> observers = new ArrayList<>();

    private ArrayList<PlayerCard> hand = new ArrayList<>();
    private Role role;
    private City currentCity;
    private boolean readyToStart = false;
    private String name;
    private int actions = 4;

    public Player(String name) {
        this.name = name;
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

    public void setReadyToStart() {
        readyToStart = true;
        System.out.println("player is ready");
        notifyAllObservers();
    }

    public String getName() {
        return name;
    }

    public void resetActions() {
        actions = 4;
    }

    public void decrementActions() {
        actions--;
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
