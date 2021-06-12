package Controller;

import GameApplication.GameApplication;
import Model.FirestoreDatabase;
import Model.Player;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

public class DatabaseController {
    FirestoreDatabase database;
    static DatabaseController databaseController;

    private DatabaseController() {
        database = GameApplication.getFsDatabase();
    }

    public static DatabaseController getInstance() {
        if (databaseController == null) {
            databaseController = new DatabaseController();
        }

        return databaseController;
    }

    public void updatePlayersInLobby(ArrayList<Player> players) {
        database.updatePlayersInLobby(players);
    }

    public String makeLobby() {
        return database.makeLobby();
    }

    public void addPlayer(String lobbyCode,Player player) {
        database.listen(this, lobbyCode);
        database.addPlayerToLobby(lobbyCode, player);
    }

    public void removePlayer(String lobbyCode, Player player) {
        database.removePlayerFromLobby(lobbyCode, player);
    }

    public void update(Map<String, Object> map) {
        LobbyController.getInstance().update(map);
    }

    public DocumentSnapshot getLobbyDocument(String lobbyCode) {
        return database.getLobbyByDocumentId(lobbyCode);
    }
}
