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
        removeCorrectCubeAmount(currentPlayer, currentCity);
        if (!canRemoveAllCubesWithoutDecrementActions(currentPlayer, currentCity)) {
            playerController.decrementActions(currentPlayer);
        }
    }

    public void removeCorrectCubeAmount(Player currentPlayer, City currentCity) {
        if (cityHasCube(currentCity)) {
            if (canRemoveAllCubes(currentPlayer, currentCity)) {
                removeAllCubes(currentCity);
            } else {
                removeCube(currentCity);
            }
        }
    }

    public boolean canRemoveAllCubes(Player currentPlayer, City currentCity) {
        return playerController.getRole(currentPlayer).getName().equals("medic") ||
               cureIsFound(currentCity.getVirusType());
    }

    public boolean canRemoveAllCubesWithoutDecrementActions(Player currentPlayer, City currentCity) {
        return playerController.getRole(currentPlayer).getName().equals("medic") &&
                cureIsFound(currentCity.getVirusType());
    }

    public boolean canAddResearchStation() {
        return gameBoard.gameboardHasResearchStationsLeft();
    }

    public boolean cityHasCube(City currentCity) {
        return currentCity.getCubeAmount() > 0;
    }

    public void removeAllCubes(City currentCity) {
        currentCity.removeAllCubes();
    }

    public void removeCube(City currentCity) {
        currentCity.removeCube();
    }

    public boolean cureIsFound(VirusType virus) {
        for (Cure cure : gameBoard.getCuredDiseases()) {
            if (cure.getType().equals(virus)) {
                return true;
            }
        }

        return false;
    }
}
