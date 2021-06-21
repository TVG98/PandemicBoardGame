package Controller.Behavior;

import Controller.PlayerController;
import Model.City;
import Model.Player;

/**
 * Is implemented by the CharterFlightBehaviors.
 * @author Thimo van Velzen
 */

public interface CharterFlightBehavior {
    PlayerController playerController = PlayerController.getInstance();

    void charterFlight(Player currentPlayer, City chosenCity);
}
