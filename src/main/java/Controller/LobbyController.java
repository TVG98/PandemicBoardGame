package Controller;

import Model.Lobby;
import Model.Player;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.*;

public class LobbyController {
    static LobbyController lobbyController;

    Lobby lobby;
    DatabaseController databaseController = DatabaseController.getInstance();
    PlayerController playerController = PlayerController.getInstance();

    private String lobbyCode;

    public LobbyController() {

    }

    public static LobbyController getInstance() {
        if (lobbyController == null) {
            lobbyController = new LobbyController();
        }

        return lobbyController;
    }

    public void makeLobby(String playerName) {

        Player player = new Player(playerName);
        lobby = databaseController.makeLobby(player);//Todo: create player via playerController
        playerController.setPlayer(player);
        System.out.println("lobby aangemaakt " + lobby.getPassword());
    }

    public void setPlayerReady() {
        playerController.getPlayer().setReadyToStart();
        databaseController.updatePlayers(lobbyCode, lobby);
    }


    public void addPlayerToLobby(String passwd, String playerName) {
        lobbyCode = passwd;
        lobby = new Lobby(lobbyCode);
        getPlayersFromLobbyDoc(databaseController.getLobbyDocument(lobbyCode));
        Player player = new Player(playerName);
        lobby.addPlayer(player);
        playerController.setPlayer(player);
        System.out.println(lobby.getPlayers().size());
        databaseController.updatePlayers(lobbyCode, lobby);
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

    public Player getCurrentPLayer() {
        return playerController.getPlayer();
    }

    public void removePlayerFromLobby(Player player) {
        lobby.removePlayer(player);
        databaseController.updatePlayers(lobbyCode, lobby);
    }

    public void startGame() {

    }

    public void registerObserver(View.InLobbyView view) {
        lobby.register(view);
    }
}
