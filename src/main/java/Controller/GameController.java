package Controller;

import Model.*;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class GameController {
    static GameController gameController;

    Game game;
    private final PlayerController playerController = PlayerController.getInstance();
    private final GameBoardController gameBoardController = GameBoardController.getInstance();

    public static GameController getInstance() {
        if (gameController == null) {
            gameController = new GameController();
        }

        return gameController;
    }

    public void changeTurn(){
        game.nextTurn();
    }

    public void checkLoss() {
        if(gameBoardController.lossByCubeAmount()) {
            game.setLost();
        } else if(gameBoardController.lossByEmptyPlayerCardStack()) {
            game.setLost();
        } else if(gameBoardController.lossByOutbreakCounter()) {
            game.setLost();
        }
    }
    // Misschien dat we de checkLoss en de checkWin method kunnen samenvoegen
    public void checkWin() {
        if(gameBoardController.winByCures()) {
            game.setWon();
        }
    }

    public void handleDrive() {
        gameBoardController.handleDrive(getCurrentPlayer());
    }

    public void handleDirectFlight(City city) {

    }

    public void handleCharterFlight(City city) {

    }

    public void handleShuttleFlight(City city) {

    }

    public void handleBuildResearchStation() {
        gameBoardController.handleBuildResearchStation(getCurrentPlayer());
    }

    public void handleShareKnowledge(PlayerCard card) {
        City city = playerController.getPlayerCurrentCity(getCurrentPlayer());
        ArrayList<Player> playersInCity = game.getPlayersInCity(city);

        if (playersInCity.size() > 1) {

            Player chosenPlayer = game.getCurrentPlayer();//Todo choose player to share with/change this
            //game.getCurrentPlayer().getRole().shareKnowledge(card, chosenPlayer);
        }

        getCurrentPlayer().decrementActions();
    }

    public void handleTreatDisease() {
        Player currentPlayer = getCurrentPlayer();
        City currentCity = currentPlayer.getCurrentCity();
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
