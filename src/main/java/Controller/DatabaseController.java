package Controller;

import Exceptions.LobbyFullException;
import Model.*;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
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
        } else {
            GameController.getInstance().updatePlayersInGame(snapshot);
        }

        //GameBoardController.getInstance().update(snapshot);
    }

    public void updateCitiesInDatabase(List<City> cities) {
        database.updateCities(cities);
    }

    public void updateCitiesWithResearchStationsInDatabase(List<City> cities) {
        database.updateCitiesWithResearchStations(cities);
    }

    public void updateCuredDiseases(List<Cure> curedDiseases) {
        database.updateCuredDiseases(curedDiseases);
    }

    public void updateCures(List<Cure> CURES) {
        database.updateCures(CURES);
    }

    public void updateDrawnEpidemicCards(int drawnEpidemicCards) {
        database.updateDrawnEpidemicCards(drawnEpidemicCards);
    }

    public void updateInfectionDiscardStack(ArrayList<InfectionCard> infectionDiscardStack) {
        database.updateInfectionDiscardStack(infectionDiscardStack);
    }

    public void updateInfectionRate(int infectionRate) {
        database.updateInfectionRate(infectionRate);
    }

    public void updateInfectionStack(ArrayList<InfectionCard> infectionStack) {
        database.updateInfectionStack(infectionStack);
    }

    public void updateOutbreakCounter(int outbreakCounter) {
        database.updateOutbreakCounter(outbreakCounter);
    }

    public void updatePlayerDiscardStack(ArrayList<PlayerCard> playerDiscardStack) {
        database.updatePlayerDiscardStack(playerDiscardStack);
    }

    public void updatePlayerStack(ArrayList<PlayerCard> playerStack) {
        database.updatePlayerStack(playerStack);
    }

    public void updateTopSixInfectionStack(ArrayList<InfectionCard> topSixInfectionStack) {
        database.updateTopSixInfectionStack(topSixInfectionStack);
    }

    public void updateViruses(List<Virus> viruses) {
        database.updateViruses(viruses);
    }

    public DocumentSnapshot getLobbyDocument(String lobbyCode) {
        return database.getLobbyByDocumentId(lobbyCode);
    }
}
