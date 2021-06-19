package Model;

import Exceptions.CardNotFoundException;

import java.util.ArrayList;

public class Player {
    private ArrayList<PlayerCard> hand = new ArrayList<>();
    private Role role;
    private City currentCity;
    private boolean readyToStart;
    private String playerName;
    private int actions = 4;
    private ArrayList<PlayerCard> cardsToShare = new ArrayList<>();

    public Player() {}

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

    public ArrayList<CityCard> createCityCardsFromPlayer() {
        ArrayList<CityCard> cityCards = new ArrayList<>();

        for (PlayerCard card : hand) {
            if (card instanceof CityCard) {
                cityCards.add((CityCard) card);
            }
        }

        return cityCards;
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
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setCardsToShare(ArrayList<PlayerCard> cardsToShare) {
        this.cardsToShare = cardsToShare;
    }

    public ArrayList<PlayerCard> getCardsToShare() {
        return cardsToShare;
    }

    public boolean actionsPlayed() {
        return actions <= 0;
    }

    public void resetActions() {
        actions = 4;
    }

    public int getActions() {
        return actions;
    }

    public void decrementActions() {
        actions--;
    }

    public PlayerCard getCardFromHandByCity(City city) throws CardNotFoundException {
        for(PlayerCard card : hand) {
            if(card instanceof CityCard) {
                String cardCityName = ((CityCard) card).getCity().getName();
                if(cardCityName.equals(city.getName())) {
                    return card;
                }
            }
        }

        throw new CardNotFoundException("Player does not have the cityCard of the current city he's in");
    }
}
