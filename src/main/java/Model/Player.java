package Model;

import java.util.ArrayList;

public class Player {
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
}
