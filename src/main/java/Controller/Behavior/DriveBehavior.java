package Controller.Behavior;

import Controller.PlayerController;
import Model.City;
import Model.Player;

/**
 * Is implemented by DriveBehaviors.
 * @author Thimo van Velzen
 */

public interface DriveBehavior {
    PlayerController playerController = PlayerController.getInstance();

    void drive(Player currentPlayer, City chosenCity);
}
