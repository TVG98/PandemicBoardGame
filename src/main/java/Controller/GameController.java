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

    private GameController() {
        game = new Game(new ArrayList<>());
        playerController = PlayerController.getInstance();
        gameBoardController = GameBoardController.getInstance();
        lobbyController = LobbyController.getInstance();
    }

    public static GameController getInstance() {
        if (gameController == null) {
            gameController = new GameController();
        }

        return gameController;
    }

    public void startGame() {
        setPlayers();
        drawInitialInfectionCards();
    }

    private void setPlayers() {
        for(Player player : lobbyController.getPlayersInLobby()) {
            player.setRole(getRandomRole());
            try {
                player.setCurrentCity(gameBoardController.getCity("Atlanta"));
            } catch(CityNotFoundException cnfe) {
                cnfe.printStackTrace();
            }
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

        if(getCurrentPlayer().actionsPlayed()) {  // Zodra de acties gespeeld zijn
            gameBoardController.handlePlayerCardDraw(getCurrentPlayer());  // Pak twee spelerkaarten
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

    public void handleDrive() {
        gameBoardController.handleDrive(getCurrentPlayer());
    }

    public void handleDirectFlight(City city) {
        gameBoardController.handleDirectFlight(getCurrentPlayer());
    }

    public void handleCharterFlight(City city) {
        gameBoardController.handleCharterFlight(getCurrentPlayer());
    }

    public void handleShuttleFlight(City city) {

    }

    public void handleBuildResearchStation() {
        Player currentPlayer = getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
        setBuildResearchBehavior(currentPlayer);
        if (gameBoardController.canAddResearchStation()) {
            gameBoardController.handleBuildResearchStation(currentPlayer, currentCity);
        }
    }

    public void setBuildResearchBehavior(Player currentPlayer) {
        if (gameBoardController.canBuildResearchStationWithoutCard(currentPlayer)) {
            gameBoardController.setBuildResearchStationBehavior(new BuildResearchStationWithoutCard());
        } else {
            gameBoardController.setBuildResearchStationBehavior(new BuildResearchStationNormal());
        }
    }

    public void handleShareKnowledge(PlayerCard card) {
        City city = playerController.getPlayerCurrentCity(getCurrentPlayer());
        ArrayList<Player> playersInCity = game.getPlayersInCity(city);

        if (playersInCity.size() > 1) {
            Player chosenPlayer = getCurrentPlayer();//Todo choose player to share with/change this
            //game.getCurrentPlayer().getRole().shareKnowledge(card, chosenPlayer);
        }

        getCurrentPlayer().decrementActions();
    }

    public void handleTreatDisease() {
        Player currentPlayer = getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
        setTreatDiseaseBehavior(currentPlayer, currentCity);
        gameBoardController.handleTreatDisease(currentPlayer, currentCity);
    }

    public void setTreatDiseaseBehavior(Player currentPlayer, City currentCity) {
        if (gameBoardController.canRemoveAllCubesWithoutDecrementActions(currentPlayer, currentCity)) {
            gameBoardController.setTreatDiseaseBehavior(new TreatDiseaseThreeCubesWithoutActionDecrement());
        } else if (gameBoardController.canRemoveAllCubes(currentPlayer, currentCity)){
            gameBoardController.setTreatDiseaseBehavior(new TreatDiseaseThreeCubes());
        } else {
            gameBoardController.setTreatDiseaseBehavior(new TreatDiseaseOneCube());
        }
    }

    public void handleFindCure() {

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
