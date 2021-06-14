package Controller.Behavior.EventCards;

import Controller.GameBoardController;

public class AirLiftBehavior {
    GameBoardController gameBoardController = GameBoardController.getInstance();

    public void play() {
        // Todo: Kies een pion en een city
        gameBoardController.handleCharterFlight(null, null);
    }
}
