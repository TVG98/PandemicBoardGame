package Controller;

import Model.City;
import Model.Cure;
import Model.Gameboard;
import Model.Player;

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
}
