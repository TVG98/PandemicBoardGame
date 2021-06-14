package Controller;

import Model.FirestoreDatabase;
import Model.Player;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;

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

    public void updateJoinable(String lobbyCode, boolean joinable) {
        database.updateJoinable(lobbyCode, joinable);
    }

    public void removePlayer(Player player) {
        database.removePlayerFromLobby(player);
    }

    public void update(DocumentSnapshot snapshot) {
        LobbyController.getInstance().update(snapshot);
    }

    public DocumentSnapshot getLobbyDocument(String lobbyCode) {
        return database.getLobbyByDocumentId(lobbyCode);
    }
}
