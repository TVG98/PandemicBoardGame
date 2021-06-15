package Controller.Behavior.EventCards;

import Controller.GameBoardController;

public class AirLiftBehavior implements EventBehavior {

    public void play() {
        // Todo: Kies een pion en een city
        gameBoardController.handleCharterFlight(null, null);
    }
}
