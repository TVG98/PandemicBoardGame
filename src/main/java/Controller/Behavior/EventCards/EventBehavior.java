package Controller.Behavior.EventCards;

import Controller.GameBoardController;

/**
 * Is implemented by the eventCardBehaviors.
 * @author Daniel Paans
 */
public interface EventBehavior {

    GameBoardController gameBoardController = GameBoardController.getInstance();

    void play();
}
