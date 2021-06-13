package Controller;

import Exceptions.PlayerNotFoundException;
import Model.Lobby;
import Model.Player;

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

            for (String player : s) {
                String playerName = player.split("playerName=")[1];
                if (lobby.getPlayers().size() < databaseController.getLobbyDocument(lobbyCode).getLong("PlayerAmount")) {
                    boolean readyToStart = player.contains("readyToStart=true");
                    addPlayerToLobby(playerName.substring(0, playerName.indexOf(",")), readyToStart);

                } else if(lobby.getPlayers().size() > databaseController.getLobbyDocument(lobbyCode).getLong("PlayerAmount")) {
                    for (Player p : lobby.getPlayers()) {
                        if (!databaseController.getLobbyDocument(lobbyCode).get("Players").toString().contains(p.getPlayerName() + ",")) {
                            lobby.removePlayer(p);
                        }
                    }
                } else {
                    lobby.updatePlayers(playersString);
                }
            }
        }
    }

    public ArrayList<Player> getPlayersInLobby() {
        return lobby.getPlayers();
    }

    public void addPlayerToLobby(String playerName, boolean readyToStart) {
        for (Player p : lobby.getPlayers()) {
            if (p.getPlayerName().equals(playerName)) {
                return;
            }
        }
        lobby.addPlayer(new Player(playerName, readyToStart));
    }

    public Player getCurrentPLayer() throws PlayerNotFoundException {
        for (Player p : lobby.getPlayers()) {
            if (p.getPlayerName().equals(playerController.getCurrentPlayerName())) {
                return p;
            }
        }
        throw new PlayerNotFoundException("Player not found.");
    }

    public String checkPlayerName(String playersString, String playerName) {
        String newName = playerName;

        for (int i = 0; i < lobby.getMaxLobbySize(); i++) {
            if (playersString.contains(newName)) {
                newName = playerName += Integer.toString(i + 1);
            }
        }
        return newName;
    }

    public void removePlayerFromServer(Player player) {
        databaseController.removePlayer(player);
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
