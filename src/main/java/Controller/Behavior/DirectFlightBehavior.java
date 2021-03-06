package Controller.Behavior;

import Controller.PlayerController;
import Model.City;
import Model.Player;

/**
 * Is implemented by the DirectFlightBehaviors.
 * @author Thimo van Velzen
 */

public interface DirectFlightBehavior {
    PlayerController playerController = PlayerController.getInstance();

    void directFlight(Player currentPlayer, City chosenCity);
}
