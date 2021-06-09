package Controller;

import Model.Forecast;
import Model.Lobby;
import Model.Player;
import Model.Role;

import java.sql.SQLOutput;
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
        System.out.println(playerController.getPlayer().getPlayerName());
        for (Player p : lobby.getPlayers()) {
            if (playerController.getPlayer().getPlayerName().equals(p.getPlayerName())) {
                p.setReadyToStart(true);
            }
        }
        databaseController.updatePlayersInLobby(lobby.getPlayers());
    }

    public boolean addPlayerToServer(String passwd, String playerName) {
        lobbyCode = passwd;
        if (databaseController.getLobbyDocument(lobbyCode).getLong("PlayerAmount") < 4) {
            lobby = new Lobby(lobbyCode);
            playerName = checkPlayerName(databaseController.getLobbyDocument(lobbyCode).get("Players").toString(), playerName);
            System.out.println(playerName);
            Player player = new Player(playerName);
            playerController.setPlayer(player);
            databaseController.addPlayer(lobbyCode, player);
            return true;
        }
        return false;
    }

    public void updatePlayersFromLobbyDoc(Map<String, Object> map) {
        if (map != null) {
            Object playersObject = map.get("Players");
            String playersString = playersObject.toString();
            String[] s = playersString.split("}, \\{");
            int index = 0;

            for (String player : s) {
                if (lobby.getPlayers().size() == index) {
                    String playerName = player.split("playerName=")[1];
                    if (addPlayerToLobby(playerName.substring(0, playerName.indexOf(",")))) {
                        index++;
                    }

                } else if(lobby.getPlayers().size() > databaseController.getLobbyDocument(lobbyCode).getLong("PlayerAmount")) {
                    System.out.println("Verwijder speler");
                    for (Player p : lobby.getPlayers()) {
                        if (!databaseController.getLobbyDocument(lobbyCode).get("Players").toString().contains(p.getPlayerName() + ",")) {
                            lobby.removePlayer(p);
                        }
                    }
                } else {
                    System.out.println("updating player " + index);
                    String role = player.split("role=")[1];
                    role = role.substring(0, role.indexOf(","));
                    if (!role.equals("null")) {
                        if (!lobby.getPlayers().get(index).getRole().equals(Role.valueOf(role))) {
                            lobby.getPlayers().get(index).setRole(Role.valueOf(role));
                        }
                    }
                    boolean ready = player.split("readyToStart=")[1].startsWith("true");
                    if (lobby.getPlayers().get(index).getReadyToStart() != ready) {
                        lobby.getPlayers().get(index).setReadyToStart(ready);
                    }

                    String currentCity = player.split("currentCity")[1];
                    currentCity = currentCity.substring(0, currentCity.indexOf(","));
                    //Todo updateCity();
                    //Todo updateHand();
                    index++;
                }
            }
            for (Player p : lobby.getPlayers()) {
                System.out.println(p.getPlayerName());
            }
        }
    }

    public boolean addPlayerToLobby(String playerName) {
        for (Player p : lobby.getPlayers()) {
            if (p.getPlayerName().equals(playerName)) {
                return false;
            }
        }
        lobby.addPlayer(new Player(playerName));
        return true;
    }

    public Player getCurrentPLayer() {
        return playerController.getPlayer();
    }

    public String checkPlayerName(String playersString, String playerName) {
        int MAX_LOBBY_SIZE = 4;
        System.out.println(playersString);
        String newName = playerName;
        for (int i = 0; i < MAX_LOBBY_SIZE; i++) {
            if (playersString.contains(newName)) {
                newName = playerName += Integer.toString(i + 1);
            }
        }

        return newName;
    }

    public void removePlayerFromServer(Player player) {
        databaseController.removePlayer(lobbyCode, player);
    }

    public void startGame() {

    }

    public void update(Map<String, Object> map) {
        updatePlayersFromLobbyDoc(map);
    }

    public void registerObserver(View.InLobbyView view) {
        lobby.register(view);
    }
}
