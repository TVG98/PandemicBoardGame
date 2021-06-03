package Controller;

import GameApplication.GameApplication;
import Model.FirestoreDatabase;
import Model.Lobby;
import Model.Player;


public class LobbyController {
    Lobby lobby;
    FirestoreDatabase fsController = GameApplication.getFsDatabase();

    public void makeLobby() {
        fsController.makeLobby();
    }

    public void addPlayerToLobby() {

    }

    public void removePlayerFromLobby(Player player) {

    }

    public void startGame() {

    }
}
