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

    public void updateGameStarted(boolean gameStarted) {
        database.updateGameStarted(gameStarted);
    }

    public void removePlayer(Player player) {
        database.removePlayerFromLobby(player);
    }

    public void update(DocumentSnapshot snapshot) {
        System.out.println(snapshot.getBoolean("Joinable"));
        if (!snapshot.getBoolean("GameStarted")) {
            LobbyController.getInstance().update(snapshot);
        }
        System.out.println("na de lobby controller" + snapshot.getBoolean("Joinable"));
        //GameBoardController.getInstance().update(snapshot);
        System.out.println("joinable dus? " + snapshot.getBoolean("Joinable"));
        if (!snapshot.getBoolean("Joinable")) {
            System.out.println("Kom ik hier");
            GameController.getInstance().updatePlayersInGame(snapshot);
        }
    }

    public DocumentSnapshot getLobbyDocument(String lobbyCode) {
        return database.getLobbyByDocumentId(lobbyCode);
    }
}
