package Controller;

import Model.City;
import Model.Player;
import Model.PlayerCard;
import Model.Role;

import java.util.ArrayList;

public class PlayerController {
    ArrayList<Player> players;

    public PlayerController() {
        players = new ArrayList<>();
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

    public Role getRole(Player player) {
        return player.getRole();
    }

    public boolean checkCardInHand(PlayerCard card, Player player) {
        return player.checkCardInHand(card);
    }
}
