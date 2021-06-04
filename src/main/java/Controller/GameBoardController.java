package Controller;

import Model.*;

public class GameBoardController {
    private Gameboard gameBoard;
    private GameController gameController;
    private PlayerController playerController;

    public void handleCurePawn(Cure cure) {

    }

    public void handlePlayerCardDraw() {

    }

    public void handleEpidemicCard() {

    }

    public void handleInfection() {

    }

    public void handlePlayerPawnMovement(Player player) {

    }

    public void handleBuildResearchStation() {
        Player currentPlayer = gameController.getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);

        if (canAddResearchStation()) {
            gameBoard.addResearchStationToCity(currentCity);
        }
        //TODO: check if player needs to hand over card of city
        playerController.decrementActions(currentPlayer);
    }

    public void handleTreatDisease() {
        Player currentPlayer = gameController.getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
        treatDisease(currentPlayer, currentCity);

        if (!canRemoveAllCubesWithoutDecrementActions(currentPlayer, currentCity)) {
            playerController.decrementActions(currentPlayer);
        }
    }

    public void treatDisease(Player currentPlayer, City currentCity) {
        if (gameBoard.cityHasCube(currentCity)) {
            if (canRemoveAllCubes(currentPlayer, currentCity)) {
                currentCity.removeAllCubes();
            } else {
                currentCity.removeCube();
            }
        }
    }

    public boolean canRemoveAllCubes(Player currentPlayer, City currentCity) {
        return playerController.getRole(currentPlayer).getName().equals("medic") ||
                cureIsFound(currentCity);
    }

    public boolean canRemoveAllCubesWithoutDecrementActions(Player currentPlayer, City currentCity) {
        return playerController.getRole(currentPlayer).getName().equals("medic") &&
                cureIsFound(currentCity);
    }

    public boolean canAddResearchStation() {
        return gameBoard.gameboardHasResearchStationsLeft();
    }

    public boolean cureIsFound(City currentCity) {
        return gameBoard.cureIsFound(currentCity.getVirusType());
    }
}
