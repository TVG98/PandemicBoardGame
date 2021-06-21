package Controller;

import Exceptions.LobbyFullException;
import Exceptions.LobbyNotFoundException;
import Exceptions.LobbyNotJoinableException;
import Exceptions.PlayerNotFoundException;
import Model.DatabaseData;
import Model.Lobby;
import Model.Player;
import Model.Sound;
import Observers.LobbyObserver;

import java.util.List;

/**
 * Handles all the lobby functionalities.
 * @author Thimo van Velzen, Tom van Gogh, Daniel Paans
 */

public class LobbyController {
    static LobbyController lobbyController;

    private Lobby lobby;
    private final DatabaseController databaseController;
    private final PlayerController playerController;
    private final SoundController soundController;

    private String lobbyCode;

    private LobbyController() {
        databaseController = DatabaseController.getInstance();
        playerController = PlayerController.getInstance();
        soundController = SoundController.getInstance();
    }

    /**
     * @author Thimo van Velzen
     */
    public static LobbyController getInstance() {
        if (lobbyController == null) {
            lobbyController = new LobbyController();
        }

        return lobbyController;
    }

    /**
     * @author Thimo van Velzen, Tom van Gogh
     */
    public void makeLobby(String playerName) {
        lobbyCode = databaseController.createLobbyCode();
        databaseController.makeLobby(lobbyCode);
        tryToAddPlayerToServer(playerName);
    }

    /**
     * @author Thimo van Velzen, Tom van Gogh
     */
    private void tryToAddPlayerToServer(String playerName) {
        try {
            Thread.sleep(5000);
            addPlayerToServer(lobbyCode, playerName);
        } catch (InterruptedException | LobbyNotFoundException | LobbyNotJoinableException ie) {
            ie.printStackTrace();
        }
    }

    public void tryToSetPlayerReady() {
        playSoundEffect(Sound.TREATDISEASE);
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

    /**
     * @author Thimo van Velzen
     */
    private boolean playerSpotIsEmpty(Player player) {
        return player == null;
    }

    private boolean isCurrentPlayer(Player player) throws PlayerNotFoundException {
        String currentPlayerName = getCurrentPlayer().getPlayerName();
        return currentPlayerName.equals(player.getPlayerName());
    }

    /**
     * @author Tom van Gogh
     */
    private void setPlayerReady(Player player) {
        player.setReadyToStart(true);
        databaseController.updatePlayerInServer(player);
    }

    /**
     * @author Tom van Gogh
     */
    public void setServerLobbyNotJoinable() {
        databaseController.updateJoinable(false);
    }

    /**
     * @author Thimo van Velzen, Tom van Gogh
     */
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

    /**
     * @author Thimo van Velzen
     */
    private boolean playerSlotInFirebaseIsTaken(int i) {
        return databaseController.getDatabaseData().getPlayer(i) != null;
    }

    /**
     * @author Thimo van Velzen, Tom van Gogh
     */
    private void tryToAddPlayerToDatabase(Player player, String playerName) {
        try {
            databaseController.addPlayer(lobbyCode, player);
            playerController.setPlayer(playerName);
        } catch (LobbyFullException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Daniel Paans
     * @param playerName
     * @return
     */
    private String getPlayerName(String playerName) {
        for (int i = 0; i < 4; i++) {
            if (playerSlotInFirebaseIsTaken(i)) {
                String playerString = databaseController.getDatabaseData().getPlayer(i).getPlayerName();
                playerName = checkPlayerName(playerString, playerName);
            }
        }

        return playerName;
    }

    /**
     * @author Tom van Gogh
     */
    public Player getCurrentPlayer() throws PlayerNotFoundException {
        List<Player> players = lobby.getPlayers();

        for (Player player : players) {
            if (player != null && player.getPlayerName().equals(playerController.getYourPlayerName())) {
                return player;
            }
        }

        throw new PlayerNotFoundException("Player not found.");
    }

    /**
     * @author Tom van Gogh
     */
    public String checkPlayerName(String playersString, String playerName) {
        String newName = playerName;

        for (int i = 0; i < lobby.getMaxLobbySize(); i++) {
            if (playersString.contains(newName)) {
                newName = playerName += Integer.toString(i + 1);
            }
        }

        return newName;
    }

    /**
     * @author Thimo van Velzen
     */
    public void removePlayerFromServer(Player player) {
        databaseController.removePlayer(player.getPlayerName());
    }

    /**
     * @author Tom van Gogh
     */
    public Lobby getLobby() {
        return lobby;
    }

    /**
     * @author Daniel Paans
     * @param sound
     */
    public void playSoundEffect(Sound sound) {
        soundController.playSound(sound);
    }

    /**
     * @author Thimo van Velzen, Tom van Gogh
     */
    public synchronized void update(DatabaseData data) {
        lobby.setJoinable(data.isJoinable());
        lobby.setPlayers(data.returnPlayers());
    }

    /**
     * @author Tom van Gogh
     */
    public void registerLobbyObserver(LobbyObserver lobbyObserver) {
        lobby.register(lobbyObserver);
    }
}
