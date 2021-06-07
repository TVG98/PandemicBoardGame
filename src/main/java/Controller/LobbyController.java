package Controller;

import Model.Lobby;
import Model.Player;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.*;


public class LobbyController {
    Lobby lobby;
    DatabaseController dbController;


    public LobbyController() {
        dbController = new DatabaseController();
    }

    public void makeLobby(String playerName) {
        lobby = dbController.makeLobby(new Player(playerName));//Todo: create player via playerController
        System.out.println("lobby aangemaakt " + lobby.getPassword());
    }

    public void setPlayerReady() {
        //Todo: implement
    }


    public void addPlayerToLobby(String passwd) {
        lobby = new Lobby(passwd);
        getPlayerFromLobbyDoc(dbController.getLobbyDocument(passwd));
        //Todo add this player
        dbController.addPlayer();
    }

    public void getPlayerFromLobbyDoc(DocumentSnapshot docSnap) {
        Map<String, Object> map = docSnap.getData();
        if (map != null) {
            Object playersObject = map.get("Players");
            String playersString = playersObject.toString();
            String[] s;
            s = playersString.split("}, \\{");
            for (String player : s) {
                String[] playerName = player.split("name=");
                lobby.addPlayer(new Player(playerName[1].substring(0, playerName[1].indexOf(","))));
            }
        }
    }

    public void removePlayerFromLobby(Player player) {

    }

    public void startGame() {

    }

    public void registerObserver(View.InLobbyView view) {
        lobby.register(view);
    }
}
