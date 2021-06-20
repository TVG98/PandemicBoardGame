package Controller.Behavior.EventCards;

import Controller.GameBoardController;

/**
 * @author : Daniel Paans
 */
public interface EventBehavior {

    GameBoardController gameBoardController = GameBoardController.getInstance();

    void play();
}
