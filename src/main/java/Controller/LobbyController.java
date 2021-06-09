package Controller;

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
        playerController.getPlayer().setReadyToStart(true);
        System.out.println(playerController.getPlayer().getReadyToStart());
        databaseController.updatePlayersInLobby(lobby.getPlayers());
    }


    public void addPlayerToLobby(String passwd, String playerName) {
        lobbyCode = passwd;
        lobby = new Lobby(lobbyCode);
        Player player = new Player(playerName);
        playerController.setPlayer(player);
        System.out.println(lobby.getPlayers().size());
        databaseController.addPlayer(lobbyCode, player);
    }

    public void updatePlayersFromLobbyDoc(Map<String, Object> map) {
        if (map != null) {
            Object playersObject = map.get("Players");
            String playersString = playersObject.toString();
            String[] s = playersString.split("}, \\{");
            for (int i = 0; i < s.length; i++) {
                System.out.println(s[i]);
            }
            int index = 0;

            for (String player : s) {
                System.out.println(lobby.getPlayers());
                if (lobby.getPlayers().size() != index) {
                    System.out.println("adding player");
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
                } else {
                    System.out.println("voeg nieuwe player toe");
                    String playerName = player.split("playerName=")[1];
                    System.out.println(playerName);
                    lobby.addPlayer(new Player(playerName.substring(0, playerName.indexOf(","))));
                    for (Player p : lobby.getPlayers()) {
                        System.out.println(p.getPlayerName() + " " + index);
                    }
                }
                index++;
            }
        }
    }

    public Player getCurrentPLayer() {
        return playerController.getPlayer();
    }

    public void removePlayerFromLobby(Player player) {
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
