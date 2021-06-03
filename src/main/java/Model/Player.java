package Model;

import java.util.ArrayList;

public class Player {
    private ArrayList<PlayerCard> hand;
    private Role role;
    private City currentCity;
    private boolean readyToStart;
    private String name;
    private int actions;

    public Player(String name) {
        this.name = name;
        actions = 4;
        readyToStart = false;
        hand = new ArrayList<PlayerCard>();
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
