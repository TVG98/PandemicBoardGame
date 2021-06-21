package Controller.Behavior;

import Controller.GameBoardController;
import Controller.PlayerController;
import Model.City;
import Model.Player;

/**
 * Is implemented by the ShuttleFlightBehaviors.
 * @author Thimo van Velzen
 */

public interface ShuttleFlightBehavior {
    GameBoardController gameBoardController = GameBoardController.getInstance();
    PlayerController playerController = PlayerController.getInstance();

    void shuttleFlight(Player currentPlayer, City chosenCity);
}
