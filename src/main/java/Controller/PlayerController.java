package Controller;

import Model.City;
import Model.Player;
import Model.PlayerCard;

import java.util.ArrayList;

public class PlayerController {
    static PlayerController playerController;
    ArrayList<Player> players;

    public PlayerController() {
        players = new ArrayList<>();
    }

    public static PlayerController getInstance() {
        if (playerController == null) {
            playerController = new PlayerController();
        }

        return playerController;
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

    public boolean checkCardInHand(PlayerCard card, Player player) {
        return player.checkCardInHand(card);
    }

    public String getName(Player player) {
        return player.getName();
    }

    public void decrementActions(Player player) {
        player.decrementActions();
    }
}
