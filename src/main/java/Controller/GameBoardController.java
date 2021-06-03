package Controller;

import Model.*;

public class GameBoardController {
    private Gameboard gameBoard;
    private GameController gameController;

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

    public void handleBuildResearchStation(City currentCity) {
        gameBoard.addResearchStationToCity(currentCity);
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
