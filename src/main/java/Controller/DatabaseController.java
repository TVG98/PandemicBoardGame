package Controller;

import GameApplication.GameApplication;
import Model.FirestoreDatabase;
import Model.Lobby;
import Model.Player;
import com.google.cloud.firestore.DocumentSnapshot;

public class DatabaseController {
    FirestoreDatabase database = GameApplication.getFsDatabase();
    static DatabaseController databaseController;

    public DatabaseController() {

    }

    public static DatabaseController getInstance() {
        if (databaseController == null) {
            databaseController = new DatabaseController();
        }

        return databaseController;
    }

    public void updatePlayers(String lobbyCode, Lobby lobby) {
        database.updatePlayersInLobby(lobbyCode, lobby);
    }

    public Lobby makeLobby(Player player) {
        return database.makeLobby(player);
    }

    public DocumentSnapshot getLobbyDocument(String lobbyCode) {
        return database.getLobbyByDocumentId(lobbyCode);
    }

    public void initializeDatabase() {
        try {
            database.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
