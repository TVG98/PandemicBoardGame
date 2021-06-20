package Controller;

import Exceptions.CityNotFoundException;
import Model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerController {
    static PlayerController playerController;
    private GameBoardController gameBoardController;
    private String yourPlayerName;

    private PlayerController() {}

    /**
     * @author : Thimo van Velzen
     */
    public static PlayerController getInstance() {
        if (playerController == null) {
            playerController = new PlayerController();
        }

        return playerController;
    }

    /**
     * @author : Thimo van Velzen
     */
    public HashMap<VirusType, Integer> getCardAmountOfEachVirusTypeInHand(Player player) {

        HashMap<VirusType, Integer> virusTypeHashMap = new HashMap<>();
        //ArrayList<PlayerCard> playerHand = player.getHand();
        ArrayList<CityCard> cityCards = getCityCardFromPlayer(player);

        return getCountedVirusTypeInHand(cityCards, virusTypeHashMap);
    }

    private ArrayList<CityCard> getCityCardFromPlayer(Player player) {
        return player.createCityCardsFromPlayer();
    }

    /**
     * @author : Thimo van Velzen
     */
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

    public boolean hasActionsLeft(Player player) {
        return player.hasActionsLeft();
    }

    public void endTurn(Player player) {
        player.endTurn();
    }

    public void addCard(PlayerCard card, Player player) {
        player.addCardToHand(card);
        notifyGameObserver();
    }

    public void removeCard(PlayerCard card, Player player) {
        player.removeCardFromHand(card);
        notifyGameObserver();
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

    /**
     * @author : Tom van Gogh
     */
    public String getName(Player player) {
        return player.getPlayerName();
    }

    public String getYourPlayerName() {
        return yourPlayerName;
    }

    public void setPlayer(String currentPlayerName) {
        this.yourPlayerName = currentPlayerName;
    }

    public boolean hasRole(Player player, Role role) {
        return player.getRole().equals(role);
    }

    /**
     * @author : Tom van Gogh
     */
    public void resetActions(Player player) {
        player.resetActions();
        notifyGameObserver();
    }

    /**
     * @author : Tom van Gogh
     */
    public void setCurrentCity(Player player, City city) {
        player.setCurrentCity(city);
        notifyGameObserver();
    }

    /**
     * @author : Tom van Gogh
     */
    public void decrementActions(Player player) {
        player.decrementActions();
        notifyGameObserver();
    }

    /**
     * @author : Tom van Gogh
     */
    private void notifyGameObserver() {
        GameController.getInstance().notifyGameObserver();
    }
}
