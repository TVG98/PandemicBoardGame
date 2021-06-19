package Controller;

import Exceptions.LobbyFullException;
import Exceptions.LobbyNotFoundException;
import Exceptions.LobbyNotJoinableException;
import Exceptions.PlayerNotFoundException;
import Model.DatabaseData;
import Model.Lobby;
import Model.Player;
import Observers.LobbyObserver;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.List;

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
        tryToAddPlayerToServer(playerName);
    }

    private void tryToAddPlayerToServer(String playerName) {
        try {
            Thread.sleep(5000);
            addPlayerToServer(lobbyCode, playerName);
        } catch (InterruptedException | LobbyNotFoundException | LobbyNotJoinableException ie) {
            ie.printStackTrace();
        }
    }

    public void tryToSetPlayerReady() {
        try {
            setCorrectPlayerReady();
        } catch (PlayerNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCorrectPlayerReady() throws PlayerNotFoundException {
        List<Player> players = lobby.getPlayers();

        for (Player player : players) {
            if (!playerSpotIsEmpty(player) && isCurrentPlayer(player)) {
                setPlayerReady(player);
            }
        }
    }

    private boolean playerSpotIsEmpty(Player player) {
        return player == null;
    }

    private boolean isCurrentPlayer(Player player) throws PlayerNotFoundException {
        String currentPlayerName = getCurrentPlayer().getPlayerName();
        return currentPlayerName.equals(player.getPlayerName());
    }

    private void setPlayerReady(Player player) {
        player.setReadyToStart(true);
        databaseController.updatePlayerInServer(player);
    }

    public void setServerLobbyNotJoinable() {
        databaseController.updateJoinable(lobbyCode, false);
    }

    public void addPlayerToServer(String lobbyCode, String playerName)
            throws LobbyNotFoundException, LobbyNotJoinableException {
        this.lobbyCode = lobbyCode;
        if (canJoinLobby()) {
            lobby = new Lobby(lobbyCode);
            playerName = getPlayerName(playerName);
            Player player = new Player(playerName, false);
            tryToAddPlayerToDatabase(player, playerName);
        } else {
            throw new LobbyNotJoinableException("Lobby not Joinable");
        }
    }

    private boolean canJoinLobby() throws LobbyNotFoundException {
        try {
            System.out.println(lobbyCode);
            return databaseController.getDatabaseData().isJoinable();
        } catch (NullPointerException npe) {
            throw new LobbyNotFoundException("Lobby not found");
        }
    }

    private boolean playerSlotInFirebaseIsTaken(int i) {
        return databaseController.getDatabaseData().getPlayer(i) != null;
//        return databaseController.getLobbyDocument(lobbyCode).get(playerId) != null;
    }

    private void tryToAddPlayerToDatabase(Player player, String playerName) {
        try {
            databaseController.addPlayer(lobbyCode, player);
            playerController.setPlayer(playerName);
        } catch (LobbyFullException e) {
            e.printStackTrace();
        }
    }

    private String getPlayerName(String playerName) {
        for (int i = 0; i < 4; i++) {
            if (playerSlotInFirebaseIsTaken(i)) {
                String playerString = databaseController.getDatabaseData().getPlayer(i).getPlayerName();
//                String playerString = databaseController.getLobbyDocument(lobbyCode).get(playerId).toString();
                playerName = checkPlayerName(playerString, playerName);
            }
        }

        return playerName;
    }

    public synchronized void updatePlayersFromLobbyDoc(DatabaseData data) {
        lobby.setJoinable(data.isJoinable());
        lobby.setPlayers(data.getPlayers());
    }

//    private void tryToUpdatePlayerInLobby(DocumentSnapshot snapshot, int i) {
//        Object object = snapshot.get("Player" + (i + 1));
//
//        if (object != null) {
//            updatePlayerInLobby(object, i);
//        }
//    }
//
//    private void updatePlayerInLobby(Object object, int i) {
//        String playerString = object.toString();
//        Player player = playerController.createPlayerFromDocData(playerString);
//        lobby.updatePlayer(i, player);
//    }

    public Player getCurrentPlayer() throws PlayerNotFoundException {
        List<Player> players = lobby.getPlayers();

        for (Player player : players) {
            if (player != null && player.getPlayerName().equals(playerController.getCurrentPlayerName())) {
                return player;
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

    public void update(DatabaseData data) {
        updatePlayersFromLobbyDoc(data);
    }

    public void registerLobbyObserver(LobbyObserver lobbyObserver) {
        lobby.register(lobbyObserver);
    }

    public void unregisterLobbyObserver() {
        lobby.unregister();
    }
}
