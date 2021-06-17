package Controller;

import Exceptions.LobbyFullException;
import Model.City;
import Model.FirestoreDatabase;
import Model.Player;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.List;

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

    public void updateGameStarted(boolean gameStarted) {
        database.updateGameStarted(gameStarted);
    }

    public void removePlayer(Player player) {
        database.removePlayerFromLobby(player);
    }

    public void update(DocumentSnapshot snapshot) {
        if (!snapshot.getBoolean("GameStarted")) {
            LobbyController.getInstance().update(snapshot);
        }

        GameBoardController.getInstance().update(snapshot);

        if (!snapshot.getBoolean("Joinable")) {
            GameController.getInstance().updatePlayersInGame(snapshot);
        }
    }

    public void updateCitiesInDatabase(List<City> cities) {
        database.updateCities(cities);
    }

    public void updateCitiesWithResearchStationsInDatabase(List<City> cities) {
        database.updateCitiesWithResearchStations(cities);
    }

    public DocumentSnapshot getLobbyDocument(String lobbyCode) {
        return database.getLobbyByDocumentId(lobbyCode);
    }
}
