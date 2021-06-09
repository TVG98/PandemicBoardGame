package Controller;

import GameApplication.GameApplication;
import Model.FirestoreDatabase;
import Model.Lobby;
import Model.Player;

import java.util.ArrayList;
import java.util.Map;

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

    public void addPlayer(String lobbyCode, Player player) {
        database.addPlayerToLobby(lobbyCode, player);
        database.listen(this);
    }

    public void removePlayer(String lobbyCode, Player player) {
        database.removePlayerFromLobby(lobbyCode, player);
    }

    public void updatePlayersInLobby(ArrayList<Player> players) {
        database.updatePlayerInLobby(players);
    }

    public Lobby makeLobby(Player player) {
        Lobby lobby = database.makeLobby(player);
        database.listen(this);
        return lobby;
    }

    public void update(Map<String, Object> map) {
        LobbyController.getInstance().update(map);
    }
}
