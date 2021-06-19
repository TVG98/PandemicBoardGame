package Controller;

import Exceptions.LobbyFullException;
import Model.*;

public class DatabaseController {
    FirestoreDatabase database;
    static DatabaseController databaseController;

    private DatabaseController() {
        database = FirestoreDatabase.getInstance();
    }

    public static DatabaseController getInstance() {
        if (databaseController == null) {
            databaseController = new DatabaseController();
        }

        return databaseController;
    }

    public void updatePlayerInServer(Player player) {
        database.makePlayerInServer(player);
    }

    public void makeLobby(String lobbyCode) {
        database.makeLobby(lobbyCode);
    }

    public String createLobbyCode() {
        return database.generateLobbyCode();
    }

    public void addPlayer(String lobbyCode, Player player) throws LobbyFullException {
        database.listen(this, lobbyCode);
        database.addPlayerToLobby(lobbyCode, player);
    }

    public void updateJoinable(boolean joinable) {
        database.updateJoinable(joinable);
    }

    public void updateGameStarted(boolean gameStarted) {
        database.updateGameStarted(gameStarted);
    }

    public void removePlayer(Player player) {
        database.removePlayerFromLobby(player);
    }

    public void update(DatabaseData data) {
        if (!data.isGameStarted()) {
            LobbyController.getInstance().update(data);
        } else {
            GameController.getInstance().update(data);
        }

        GameBoardController.getInstance().update(data);
    }

    public void updateIndexInDatabase(int index) {
        database.updateIndex(index);
    }

    public void updateGameBoardInDatabase(Gameboard gameboard) {
        database.updateGameBoard(gameboard);
    }

    public DatabaseData getDatabaseData() {
        return database.getDatabaseData();
    }
}
