package Controller;

import Model.Lobby;
import Model.Player;


public class LobbyController {
    Lobby lobby;
    DatabaseController databaseController = new DatabaseController();


    public void makeLobby() {
        databaseController.makeLobby();
    }

    public void addPlayerToLobby() {

    }

    public void removePlayerFromLobby(Player player) {

    }

    public void startGame() {

    }
}
