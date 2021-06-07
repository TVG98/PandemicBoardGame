package Controller;

import GameApplication.GameApplication;
import Model.FirestoreDatabase;
import Model.Lobby;
import Model.Player;
import com.google.cloud.firestore.DocumentSnapshot;

/**
 * @created May 26 2021 - 8:58 PM
 * @project testGame
 */
public class DatabaseController {
    FirestoreDatabase db = GameApplication.getFsDatabase();

    public DatabaseController() {

    }

    public void updatePlayers(String lobbyCode, Lobby lobby) {
        db.updatePlayersInLobby(lobbyCode, lobby);
    }

    public Lobby makeLobby(Player player) {
        return db.makeLobby(player);
    }

    public DocumentSnapshot getLobbyDocument(String lobbyCode) {
        return db.getLobbyByDocumentId(lobbyCode);
    }

    public void initializeDatabase() {
        try {
            db.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
