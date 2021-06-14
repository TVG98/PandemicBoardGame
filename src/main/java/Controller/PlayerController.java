package Controller;

import Model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerController {

    static PlayerController playerController;
    private String currentPlayerName;
    private PlayerController() {
    }

    public static PlayerController getInstance() {
        if (playerController == null) {
            playerController = new PlayerController();
        }

        return playerController;
    }

    public HashMap<VirusType, Integer> getCardAmountOfEachVirusTypeInHand(
            Player player) {

        HashMap<VirusType, Integer> virusTypeHashMap = new HashMap<>();
        ArrayList<PlayerCard> playerHand = player.getHand();
        ArrayList<CityCard> cityCards = getCityCardFromPlayer(playerHand);

        return getCountedVirusTypeInHand(cityCards, virusTypeHashMap);
    }

    private ArrayList<CityCard> getCityCardFromPlayer(ArrayList<PlayerCard> playerHand) {
        ArrayList<CityCard> cityCards = new ArrayList<>();

        for (PlayerCard card : playerHand) {
            if (card instanceof CityCard) {
                cityCards.add((CityCard) card);
            }
        }

        return cityCards;
    }

    private HashMap<VirusType, Integer> getCountedVirusTypeInHand(
            ArrayList<CityCard> cityCards,
            HashMap<VirusType, Integer> virusTypeHashMap) {

        for (CityCard card : cityCards) {
            VirusType virusType = card.getVirusType();
            int countIncreasedByOne = virusTypeHashMap.get(virusType) + 1;
            virusTypeHashMap.put(virusType, countIncreasedByOne);
        }

        return virusTypeHashMap;
    }

    public void addCard(PlayerCard card, Player player) {
        player.addCardToHand(card);
    }

    public void removeCard(PlayerCard card, Player player) {
        player.removeCardFromHand(card);
    }

    public City getPlayerCurrentCity(Player player) {
        return player.getCurrentCity();
    }

    public boolean checkCardInHandBasedOnCity(City city, Player player) {
        return player.checkCardInHandBasedOnCity(city);
    }
    public boolean checkCardInHand(PlayerCard card, Player player) {
        return player.checkCardInHand(card);
    }

    public String getName(Player player) {
        return player.getPlayerName();
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public void setPlayer(String currentPlayerName) {
        this.currentPlayerName = currentPlayerName;
    }

    public boolean hasRole(Player player, Role role) {
        return player.getRole().equals(role);
    }

    public void setCurrentCity(Player player, City city) {
        player.setCurrentCity(city);
    }

    public void decrementActions(Player player) {
        player.decrementActions();
    }
}
