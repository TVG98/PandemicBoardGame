package Controller;

import Exceptions.VirusNotFoundException;
import Model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerController {
    static PlayerController playerController;
    ArrayList<Player> players;
    private String currentPlayerName;

    private PlayerController() {
    }

    public static PlayerController getInstance() {
        if (playerController == null) {
            playerController = new PlayerController();
        }

        return playerController;
    }

    public HashMap<VirusType, Integer> getCardAmountOfEachVirusTypeInHand(Player player, VirusType virusType) throws VirusNotFoundException {
        ArrayList<CityCard> cityCards = new ArrayList<>();
        HashMap<VirusType, Integer> virusTypeHashMap = new HashMap<VirusType, Integer>();

        for(PlayerCard card : player.getHand()) {
            if(card instanceof CityCard) {
                cityCards.add((CityCard) card);
            }
        }

        for(CityCard card : cityCards) {
            switch (card.getVirusType()) {
                case BLUE:
                    virusTypeHashMap.put(VirusType.BLUE, virusTypeHashMap.get(VirusType.BLUE)+1);
                    break;
                case YELLOW:
                    virusTypeHashMap.put(VirusType.YELLOW, virusTypeHashMap.get(VirusType.YELLOW)+1);
                    break;
                case BLACK:
                    virusTypeHashMap.put(VirusType.BLACK, virusTypeHashMap.get(VirusType.BLACK)+1);
                    break;
                case RED:
                    virusTypeHashMap.put(VirusType.RED, virusTypeHashMap.get(VirusType.RED)+1);
                    break;
                default:
                    throw new VirusNotFoundException("Virus not found");
            }
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

//    public void registerObserver(GameView view) {
//        for (Player p : players) {
//            p.register(view);
//        }
//    }
}
