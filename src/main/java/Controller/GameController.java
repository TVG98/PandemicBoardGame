package Controller;

import Controller.Behavior.*;
import Exceptions.CityNotFoundException;
import Model.*;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    static GameController gameController;

    private final Game game;
    private final PlayerController playerController;
    private final GameBoardController gameBoardController;
    private final LobbyController lobbyController;
    private final DatabaseController databaseController;

    private GameController() {
        lobbyController = LobbyController.getInstance();
        lobbyController.setServerLobbyNotJoinable();
        databaseController = DatabaseController.getInstance();
        game = new Game(lobbyController.getLobby().getPlayers());
        playerController = PlayerController.getInstance();
        gameBoardController = GameBoardController.getInstance();
        startGame();
    }

    public static GameController getInstance() {
        if (gameController == null) {
            gameController = new GameController();
        }

        return gameController;
    }

    public void startGame() {
        for (Player p : game.getPlayers()) {
            if (p != null) {
                if (p.getPlayerName().equals(playerController.getCurrentPlayerName())) {
                    setPlayer(p);
                }
            }
        }
        drawInitialInfectionCards();
    }

    private void setPlayer(Player player) {
        try {
            player.setRole(getRandomRole());
            player.setCurrentCity(gameBoardController.getCity("Atlanta"));
            databaseController.updatePlayerInServer(player);
        } catch(CityNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    private Role getRandomRole() {
        return Role.values()[new Random().nextInt(Role.values().length)];
    }

    private void drawInitialInfectionCards() {
        int drawAmount = 3;
        while(drawAmount > 0) {
            for(int x = 0; x < 3; x++) {
                gameBoardController.handleInfectionCardDraw(drawAmount);
            }
            drawAmount--;
        }
    }

    public void turn() {
        // Ik weet niet zo goed hoe we de acties gaan vormgeven in een beurt.

        if (getCurrentPlayer().actionsPlayed()) {  // Zodra de acties gespeeld zijn
            gameBoardController.handlePlayerCardDraw(getCurrentPlayer(), game.getPlayerAmount());  // Pak twee spelerkaarten
            gameBoardController.handleInfectionCardDraw();  // Doe de infections
            getCurrentPlayer().endTurn();  // Reset
            changeTurn();  // end
        }
    }

    public void changeTurn(){
        game.nextTurn();
    }

    public void checkLoss() {
        if (gameBoardController.lossByCubeAmount()) {
            game.setLost();
        } else if (gameBoardController.lossByEmptyPlayerCardStack()) {
            game.setLost();
        } else if (gameBoardController.lossByOutbreakCounter()) {
            game.setLost();
        }
    }
    // Misschien dat we de checkLoss en de checkWin method kunnen samenvoegen
    public void checkWin() {
        if (gameBoardController.winByCures()) {
            game.setWon();
        }
    }

    public void handleDrive(City city) {
        Player currentPlayer = getCurrentPlayer();
        setDriveBehavior();
        gameBoardController.handleDrive(currentPlayer, city);
    }

    private void setDriveBehavior() {
        gameBoardController.setDriveBehavior(new DriveBehaviorNormal());
    }

    public void handleDirectFlight(City city) {
        Player currentPlayer = getCurrentPlayer();
        gameBoardController.handleDirectFlight(currentPlayer, city);
    }

    private void setDirectFlightBehavior() {
        gameBoardController.setDirectFlightBehavior(new DirectFlightBehaviorNormal());
    }

    public void handleCharterFlight(City city) {
        Player currentPlayer = getCurrentPlayer();
        setCharterFlightBehavior();
        gameBoardController.handleCharterFlight(currentPlayer, city);
    }

    private void setCharterFlightBehavior() {
        gameBoardController.setCharterFlightBehavior(new CharterFlightBehaviorNormal());
    }

    public void handleShuttleFlight(City city) {
        Player currentPlayer = getCurrentPlayer();
        setBuildResearchBehavior(currentPlayer);
        gameBoardController.handleShuttleFlight(currentPlayer, city);
    }

    private void setShuttleFlightBehavior(Player currentPlayer) {
        if (gameBoardController.canShuttleFlightToAnyCity(currentPlayer)) {
            gameBoardController.setShuttleFlightBehavior(new ShuttleFlightBehaviorToAnyCity());
        } else {
            gameBoardController.setShuttleFlightBehavior(new ShuttleFlightBehaviorNormal());
        }
    }

    public void handleBuildResearchStation() {
        Player currentPlayer = getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
        setBuildResearchBehavior(currentPlayer);
        if (gameBoardController.canAddResearchStation()) {
            gameBoardController.handleBuildResearchStation(currentPlayer, currentCity);
        }
    }

    private void setBuildResearchBehavior(Player currentPlayer) {
        if (gameBoardController.canBuildResearchStationWithoutCard(currentPlayer)) {
            gameBoardController.setBuildResearchStationBehavior(new BuildResearchStationWithoutCard());
        } else {
            gameBoardController.setBuildResearchStationBehavior(new BuildResearchStationNormal());
        }
    }

    public void handleShareKnowledge(Player chosenPlayer, PlayerCard card) {
        Player currentPlayer = getCurrentPlayer();
        City city = playerController.getPlayerCurrentCity(getCurrentPlayer());
        ArrayList<Player> playersInCity = game.getPlayersInCity(city);

        setDriveBehavior();

        gameBoardController.handleShareKnowledge(currentPlayer, chosenPlayer);
    }

    private void setShareKnowledgeBehavior() {

    }

    public void handleTreatDisease() {
        Player currentPlayer = getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
        setTreatDiseaseBehavior(currentPlayer, currentCity);
        gameBoardController.handleTreatDisease(currentPlayer, currentCity);
    }

    private void setTreatDiseaseBehavior(Player currentPlayer, City currentCity) {
        if (gameBoardController.canRemoveAllCubesWithoutDecrementActions(currentPlayer, currentCity)) {
            gameBoardController.setTreatDiseaseBehavior(new TreatDiseaseThreeCubesWithoutActionDecrement());
        } else if (gameBoardController.canRemoveAllCubes(currentPlayer, currentCity)){
            gameBoardController.setTreatDiseaseBehavior(new TreatDiseaseThreeCubes());
        } else {
            gameBoardController.setTreatDiseaseBehavior(new TreatDiseaseOneCube());
        }
    }

    public void handleFindCure() {
        Player currentPlayer = getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
        setFindCureBehavior(currentPlayer);
        gameBoardController.handleFindCure(currentPlayer, currentCity);
    }

    private void setFindCureBehavior(Player currentPLayer) {
        if(gameBoardController.canFindCureWithFourCards(currentPLayer)) {
            gameBoardController.setFindCureBehavior(new FindCureWithFourCardsBehavior());
        } else {
            gameBoardController.setFindCureBehavior(new FindCureWithFiveCardsBehavior());
        }
    }

    public void checkCardInHand(PlayerCard card, Player player) {
        playerController.checkCardInHand(card, player);
    }

    public void handleGiveCard(PlayerCard card, Player player1, Player player2) {
        playerController.removeCard(card, player1);
        playerController.addCard(card, player2);
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }
}
