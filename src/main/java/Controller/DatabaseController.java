package Controller;

import Exceptions.LobbyFullException;
import Model.FirestoreDatabase;
import Model.Gameboard;
import Model.Player;
import com.google.cloud.firestore.DocumentSnapshot;

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
        database.updatePlayerInServer(player);
    }

    public void updateGameBoard(Gameboard gameboard) {
        database.updateCities(gameboard.getCities());
    }

    public String makeLobby() {
        return database.makeLobby();
    }

    public void addPlayer(String lobbyCode, Player player) throws LobbyFullException {
        database.listen(this, lobbyCode);
        database.addPlayerToLobby(lobbyCode, player);
    }

    public void updateJoinable(String lobbyCode, boolean joinable) {
        database.updateJoinable(lobbyCode, joinable);
    }

    public void removePlayer(Player player) {
        database.removePlayerFromLobby(player);
    }

    public void update(DocumentSnapshot snapshot) {
        LobbyController.getInstance().update(snapshot);
        GameBoardController.getInstance().update(snapshot);
    }

    public DocumentSnapshot getLobbyDocument(String lobbyCode) {
        return database.getLobbyByDocumentId(lobbyCode);
    }
}
