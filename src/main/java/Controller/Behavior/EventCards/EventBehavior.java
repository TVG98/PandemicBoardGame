package Controller.Behavior.EventCards;

import Controller.GameBoardController;

public interface EventBehavior {

    GameBoardController gameBoardController = GameBoardController.getInstance();

    void play();
}
