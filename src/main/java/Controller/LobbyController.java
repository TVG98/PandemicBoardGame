package Controller;

import Model.Lobby;
import Model.Player;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.*;


public class LobbyController {
    static LobbyController lobbyController;

    Lobby lobby;
    DatabaseController databaseController = DatabaseController.getInstance();

    public LobbyController() {

    }

    public static LobbyController getInstance() {
        if (lobbyController == null) {
            lobbyController = new LobbyController();
        }

        return lobbyController;
    }

    public void makeLobby(String playerName) {
        lobby = databaseController.makeLobby(new Player(playerName));//Todo: create player via playerController
        System.out.println("lobby aangemaakt " + lobby.getPassword());
    }

    public void setPlayerReady() {
        //Todo: implement
    }


    public void addPlayerToLobby(String passwd, String playerName) {
        lobby = new Lobby(passwd);
        getPlayersFromLobbyDoc(databaseController.getLobbyDocument(passwd));
        lobby.addPlayer(new Player(playerName));
        System.out.println(lobby.getPlayers().size());
        databaseController.updatePlayers(passwd, lobby);
    }

    public void getPlayersFromLobbyDoc(DocumentSnapshot docSnap) {
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
