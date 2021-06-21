package Model;

import Exceptions.CardNotFoundException;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Describes a player.
 * @author Thimo van Velzen, Daniel Paans, Tom van Gogh
 */

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

    /**
     * @author Tom van Gogh
     */
    public Player(ArrayList<PlayerCard> hand, Role role, City currentCity, boolean readyToStart, String playerName) {
        this.hand = hand;
        this.role = role;
        this.currentCity = currentCity;
        this.readyToStart = readyToStart;
        this.playerName = playerName;
    }

    /**
     * @author Tom van Gogh
     */
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

    public void setHand(ArrayList<PlayerCard> hand) {
        this.hand = hand;
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

    /**
     * @author Daniel Paans
     * @return
     */
    public ArrayList<CityCard> createCityCardsFromPlayer() {

        ArrayList<CityCard> cityCards = new ArrayList<>();

        List<String> notCityCards = Arrays.asList("EpidemicCard", "Airlift", "Forecast", "Resilient population", "Government grant", "One quiet night");

        System.out.println(hand.size());
        for (PlayerCard card : hand) {
            System.out.println(card);
            System.out.println(card.getName());
        }

        for (PlayerCard card : hand) {
            if (!notCityCards.contains(card.getName())) {
                cityCards.add((CityCard) card);
            }
        }

        return cityCards;
    }

    public ArrayList<String> getCityCardNames() {
        ArrayList<String> cityCardNames = new ArrayList<>();
        List<String> notCityCards = Arrays.asList("EpidemicCard", "Airlift", "Forecast", "Resilient population", "Government grant", "One quiet night");
        for (PlayerCard card : hand) {
            if (!notCityCards.contains(card.getName())) {
                cityCardNames.add(card.getName());
            }
        }
        return cityCardNames;
    }

    /**
     * @author Tom van Gogh
     */
    public Role getRole() {
        return role;
    }

    /**
     * @author Tom van Gogh
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @author Tom van Gogh
     */
    public City getCurrentCity() {
        return currentCity;
    }

    /**
     * @author Tom van Gogh
     */
    public void setCurrentCity(City city) {
        currentCity = city;
    }

    /**
     * @author Tom van Gogh
     */
    public boolean getReadyToStart() {
        return readyToStart;
    }

    /**
     * @author Tom van Gogh
     */
    public void setReadyToStart(boolean readyToStart) {
        this.readyToStart = readyToStart;
    }

    /**
     * @author Tom van Gogh
     */
    public String getPlayerName() {
        return playerName;
    }

    public void setCardsToShare(ArrayList<PlayerCard> cardsToShare) {
        this.cardsToShare = cardsToShare;
    }

    public ArrayList<PlayerCard> getCardsToShare() {
        return cardsToShare;
    }

    public boolean hasActionsLeft() {
        return actions > 0;
    }

    /**
     * @author Tom van Gogh
     */
    public void resetActions() {
        actions = 4;
    }

    public int getActions() {
        return actions;
    }

    /**
     * @author Tom van Gogh
     */
    public void decrementActions() {
        actions--;
    }

    /**
     * @author Daniel Paans
     * @param city
     * @return
     * @throws CardNotFoundException
     */
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
