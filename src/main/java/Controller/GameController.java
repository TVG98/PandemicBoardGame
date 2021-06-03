package Controller;

import Model.*;

import java.util.ArrayList;

public class GameController {

    Game game;
    private PlayerController playerController;
    private GameBoardController gameBoardController;


    public void changeTurn(){
        game.nextTurn();
    }

    public void handleDrive(City city) {

    }

    public void handleDirectFlight(City city) {

    }

    public void handleCharterFlight(City city) {

    }

    public void handleShuttleFlight(City city) {

    }

    public void handleBuildResearchStation() {
        Player currentPlayer = getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
        if (gameBoardController.canAddResearchStation()) {
            gameBoardController.handleBuildResearchStation(currentCity);
        }

        currentPlayer.decrementActions();
    }

    public void handleShareKnowledge(PlayerCard card) {
        City city = playerController.getPlayerCurrentCity(getCurrentPlayer());
        ArrayList<Player> playersInCity = game.getPlayersInCity(city);

        if (playersInCity.size() > 1) {

            Player chosenPlayer = game.getCurrentPlayer();//Todo choose player to share with/change this
            game.getCurrentPlayer().getRole().shareKnowledge(card, chosenPlayer);
        }

        getCurrentPlayer().decrementActions();
    }

    public void handleTreatDisease() {
        Player currentPlayer = getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
        if (gameBoardController.cityHasCube(currentCity)) {
            if (canRemoveAllCubes(currentPlayer, currentCity)) {
                gameBoardController.removeAllCubes(currentCity);
            } else {
                gameBoardController.removeCube(currentCity);
            }
        }

        playerController.decrementActions(currentPlayer);
    }

    public boolean canRemoveAllCubes(Player currentPlayer, City currentCity) {
        return playerController.getRole(currentPlayer).getName().equals("medic") ||
               gameBoardController.cureIsFound(currentCity.getVirusType());
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

    public void decrementActions(Player player) {
        player.decrementActions();
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }
}
