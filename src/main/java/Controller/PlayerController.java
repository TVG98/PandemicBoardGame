package Controller;

import Model.City;
import Model.Player;
import Model.PlayerCard;
import Model.Role;

import java.util.ArrayList;

public class PlayerController {
    static PlayerController playerController;
    ArrayList<Player> players;
    private int playerLoc;

    private PlayerController() {
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
        return player.getPlayerName();
    }

    public int getPlayerLoc() {
        return playerLoc;
    }

    public void setPlayer(int loc) {
        this.playerLoc = loc;
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

    public void registerObserver(View.InLobbyView view) {
        for (Player p : players) {
            p.register(view);
        }
    }
}
