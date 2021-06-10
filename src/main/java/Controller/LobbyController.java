package Controller;

import Exceptions.PlayerNotFoundException;
import Model.Lobby;
import Model.Player;
import Model.Role;

import java.util.*;

public class LobbyController {
    static LobbyController lobbyController;

    private Lobby lobby;
    private final DatabaseController databaseController;
    private final PlayerController playerController;

    private String lobbyCode;

    private LobbyController() {
        databaseController = DatabaseController.getInstance();
        playerController = PlayerController.getInstance();
    }

    public static LobbyController getInstance() {
        if (lobbyController == null) {
            lobbyController = new LobbyController();
        }

        return lobbyController;
    }

    public void makeLobby(String playerName)  {
        lobbyCode = databaseController.makeLobby();

        try {
            Thread.sleep(5000);
            addPlayerToServer(lobbyCode, playerName);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public void setPlayerReady() {
        try {
            System.out.println(getCurrentPLayer().getPlayerName());
            for (Player p : lobby.getPlayers()) {
                if (getCurrentPLayer().getPlayerName().equals(p.getPlayerName())) {
                    p.setReadyToStart(true);
                }
            }
            databaseController.updatePlayersInLobby(lobby.getPlayers());
        } catch (PlayerNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean addPlayerToServer(String lobbyCode, String playerName) {
        this.lobbyCode = lobbyCode;
        playerController.setPlayer(playerName);
        System.out.println(this.lobbyCode);
        if (databaseController.getLobbyDocument(lobbyCode).getLong("PlayerAmount") < 4) {
            lobby = new Lobby(lobbyCode);
            playerName = checkPlayerName(databaseController.getLobbyDocument(lobbyCode).get("Players").toString(), playerName);
            System.out.println(playerName);
            Player player = new Player(playerName, false);
            databaseController.addPlayer(lobbyCode, player);
            return true;
        }
        return false;
    }

    public synchronized void updatePlayersFromLobbyDoc(Map<String, Object> map) {
        if (map != null) {
            Object playersObject = map.get("Players");
            String playersString = playersObject.toString();
            String[] s = playersString.split("}, \\{");
            int index = 0;

            for (String player : s) {
                System.out.println("updating players " + lobby.getPlayers().size());
                System.out.println("lobbyCode: " + lobbyCode);
                System.out.println("serverPlayerAmount: " + databaseController.getLobbyDocument(lobbyCode).getLong("PlayerAmount"));
                if (lobby.getPlayers().size() < databaseController.getLobbyDocument(lobbyCode).getLong("PlayerAmount")) {
                    System.out.println("playerString: " + player);
                    boolean readyToStart = player.contains("readyToStart=true");
                    String playerName = player.split("playerName=")[1];
                    System.out.println("adding player: " + playerName.substring(0, playerName.indexOf(",")));
                    addPlayerToLobby(playerName.substring(0, playerName.indexOf(",")), readyToStart);

                } else if(lobby.getPlayers().size() > databaseController.getLobbyDocument(lobbyCode).getLong("PlayerAmount")) {
                    for (Player p : lobby.getPlayers()) {
                        if (!databaseController.getLobbyDocument(lobbyCode).get("Players").toString().contains(p.getPlayerName() + ",")) {
                            lobby.removePlayer(p);
                        }
                    }
                } else {
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
                }
                index++;
            }
            for (Player p : lobby.getPlayers()) {
                System.out.println(p.getPlayerName());
            }
        }
    }

    public boolean addPlayerToLobby(String playerName, boolean readyToStart) {
        for (Player p : lobby.getPlayers()) {
            if (p.getPlayerName().equals(playerName)) {
                return false;
            }
        }

        lobby.addPlayer(new Player(playerName, readyToStart));
        return true;
    }

    public Player getCurrentPLayer() throws PlayerNotFoundException{
        for (Player p : lobby.getPlayers()) {
            if (p.getPlayerName().equals(playerController.getCurrentPlayerName())) {
                return p;
            }
        }
        throw new PlayerNotFoundException("Player not found.");
    }

    public String checkPlayerName(String playersString, String playerName) {
        System.out.println(playersString);
        String newName = playerName;

        for (int i = 0; i < lobby.getMaxLobbySize(); i++) {
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
        for (Player p : lobby.getPlayers()) {
            p.register(view);
        }
    }
}
