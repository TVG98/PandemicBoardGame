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

    public static PlayerController getInstance() {
        if (playerController == null) {
            playerController = new PlayerController();
        }

        return playerController;
    }

    public Player createPlayerFromDocData(String playerString) {
        String hand = playerString.split("hand=")[1];
        hand = hand.substring(0, hand.indexOf("]"));//todo check if correct
        String role = playerString.split("role=")[1];
        role = role.substring(0, role.indexOf(","));
        String readyToStart = playerString.split("readyToStart=")[1];
        readyToStart = readyToStart.substring(0, readyToStart.indexOf(","));
        String playerName = playerString.split("playerName=")[1];
        playerName = playerName.substring(0, playerName.indexOf(","));
        Player player = new Player(new ArrayList<>(), null, null, readyToStart.equals("true"), playerName);

        if (!hand.equals("[")) {
            //todo update hand
        }
        if (!role.equals("null")) {
            player.setRole(Role.valueOf(role));
        }
        gameBoardController = GameBoardController.getInstance();
        if (!playerString.contains("currentCity=null")) {
            try {
                String currentCity = playerString.split("currentCity=")[1];
                currentCity = currentCity.substring(0, currentCity.indexOf("}"));
                currentCity = currentCity.split("name=")[1];
                currentCity += ",";
                currentCity = currentCity.substring(0, currentCity.indexOf(","));
                player.setCurrentCity(gameBoardController.getCity(currentCity));
            } catch (CityNotFoundException e) {
                e.printStackTrace();
            }
        }
        return player;
    }

    public HashMap<VirusType, Integer> getCardAmountOfEachVirusTypeInHand(Player player) {

        HashMap<VirusType, Integer> virusTypeHashMap = new HashMap<>();
        //ArrayList<PlayerCard> playerHand = player.getHand();
        ArrayList<CityCard> cityCards = getCityCardFromPlayer(player);

        return getCountedVirusTypeInHand(cityCards, virusTypeHashMap);
    }

    private ArrayList<CityCard> getCityCardFromPlayer(Player player) {
        return player.createCityCardsFromPlayer();
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

    public void setCurrentCity(Player player, City city) {
        player.setCurrentCity(city);
        notifyGameObserver();
    }

    public void decrementActions(Player player) {
        player.decrementActions();
        notifyGameObserver();
    }

    private void notifyGameObserver() {
        GameController.getInstance().notifyGameObserver();
        System.out.println(GameController.getInstance());
    }
}
