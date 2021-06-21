package Controller.Behavior.EventCards;

import Controller.GameBoardController;

/**
 * Implements the airlift functionality in to the game.
 * @author Daniel Paans
 */
public class AirLiftBehavior implements EventBehavior {

    public void play() {
        // Todo: Kies een pion en een city
        gameBoardController.handleCharterFlight(null, null);
    }
}
