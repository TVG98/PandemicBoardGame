package Controller.Behavior;

import Controller.GameBoardController;
import Controller.PlayerController;
import Model.City;
import Model.Player;

/**
 * Is implemented by the FindCureBehaviors.
 * @author Thimo van Velzen
 */

public interface FindCureBehavior {
    PlayerController playerController = PlayerController.getInstance();
    GameBoardController gameboardController = GameBoardController.getInstance();

    void findCure(Player currentPlayer, City currentCity);
}
