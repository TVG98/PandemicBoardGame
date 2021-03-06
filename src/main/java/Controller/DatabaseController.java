package Controller;

import Exceptions.LobbyFullException;
import Model.*;

/**
 * Retrieves data, creates and updates the firestore database.
 * @author Thimo van Velzen, Tom van Gogh
 */

public class DatabaseController {
    FirestoreDatabase database;
    static DatabaseController databaseController;

    /**
     * @author Thimo van Velzen
     */
    private DatabaseController() {
        database = FirestoreDatabase.getInstance();
    }

    /**
     * @author Thimo van Velzen
     */
    public static DatabaseController getInstance() {
        if (databaseController == null) {
            databaseController = new DatabaseController();
        }

        return databaseController;
    }

    /**
     * @author Tom van Gogh
     */
    public void updatePlayerInServer(Player player) {
        database.updatePlayerInServer(player);
    }

    /**
     * @author Thimo van Velzen, Tom van Gogh
     */
    public void makeLobby(String lobbyCode) {
        database.makeLobby(lobbyCode);
    }

    /**
     * @author Tom van Gogh
     */
    public String createLobbyCode() {
        return database.generateLobbyCode();
    }

    /**
     * @author Tom van Gogh
     */
    public void addPlayer(String lobbyCode, Player player) throws LobbyFullException {
        database.listen(this, lobbyCode);
        database.addPlayerToLobby(player);
    }

    /**
     * @author Tom van Gogh
     */
    public void updateJoinable(boolean joinable) {
        database.updateJoinable(joinable);
    }

    /**
     * @author Tom van Gogh
     */
    public void updateGameStarted(boolean gameStarted) {
        database.updateGameStarted(gameStarted);
    }

    /**
     * @author Thimo van Velzen
     */
    public void updateGameWon(boolean gameWon) {
        database.updateGameWon(gameWon);
    }

    /**
     * @author Thimo van Velzen
     */
    public void updateGameLost(boolean gameLost) {
        database.updateGameLost(gameLost);
    }

    /**
     * @author Thimo van Velzen
     */
    public void removePlayer(String name) {
        database.removeMeFromLobby(name);
    }


    /**
     * @author Thimo van Velzen, Tom van Gogh
     */
    public void update(DatabaseData data) {
        if (!data.isGameStarted()) {
            LobbyController.getInstance().update(data);
        } else {
            GameController gameController = GameController.getInstance();
            gameController.update(data);
        }

        GameBoardController.getInstance().update(data);
    }

    /**
     * @author Thimo van Velzen
     */
    public void updateIndexInDatabase(int index) {
        database.updateIndex(index);
    }

    /**
     * @author Thimo van Velzen
     */
    public void updateGameBoardInDatabase(Gameboard gameboard) {
        database.updateGameBoard(gameboard);
    }

    /**
     * @author Thimo van Velzen
     */
    public DatabaseData getDatabaseData() {
        return database.getDatabaseData();
    }
}
