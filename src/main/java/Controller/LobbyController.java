package Controller;

import Exceptions.LobbyFullException;
import Exceptions.PlayerNotFoundException;
import Model.Lobby;
import Model.Player;
import com.google.cloud.firestore.DocumentSnapshot;

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
            for (Player p : lobby.getPlayers()) {
                if (p != null) {
                    if (getCurrentPlayer().getPlayerName().equals(p.getPlayerName())) {
                        p.setReadyToStart(true);
                        databaseController.updatePlayerInServer(p);
                    }
                }
            }
        } catch (PlayerNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setServerLobbyNotJoinable() {
        databaseController.updateJoinable(lobbyCode, false);
    }

    public boolean addPlayerToServer(String lobbyCode, String playerName) {
        this.lobbyCode = lobbyCode;
        if (databaseController.getLobbyDocument(lobbyCode).getBoolean("Joinable")) {
            lobby = new Lobby(lobbyCode);
            for (int i = 0; i < 4; i++) {
                String playerId = "Player" + (i + 1);
                if (databaseController.getLobbyDocument(lobbyCode).get(playerId) != null) {
                    playerName = checkPlayerName(databaseController.getLobbyDocument(lobbyCode).get(playerId).toString(), playerName);
                }
            }
            Player player = new Player(playerName, false);

            try {
                databaseController.addPlayer(lobbyCode, player);
                playerController.setPlayer(playerName);
            } catch (LobbyFullException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public synchronized void updatePlayersFromLobbyDoc(DocumentSnapshot snapshot) {
        lobby.setJoinable(snapshot.getBoolean("Joinable"));
        String[] players = new String[4];
        for (int i = 0; i < players.length; i++) {
            Object obj = snapshot.get("Player" + (i+1));
            if (obj != null) {
                players[i] = obj.toString();
                Player player = playerController.createPlayer(players[i]);
                lobby.updatePlayer(i, player);
            }
        }
    }

    public Player[] getPlayersInLobby() {
        return lobby.getPlayers();
    }

    public Player getCurrentPlayer() throws PlayerNotFoundException {
        for (Player p : lobby.getPlayers()) {
            if (p != null) {
                if (p.getPlayerName().equals(playerController.getCurrentPlayerName())) {
                    return p;
                }
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

    public Lobby getLobby() {
        return lobby;
    }

    public void update(DocumentSnapshot snapshot) {
        updatePlayersFromLobbyDoc(snapshot);
    }

    public void registerObserver(View.InLobbyView view) {
        lobby.register(view);
    }
}
