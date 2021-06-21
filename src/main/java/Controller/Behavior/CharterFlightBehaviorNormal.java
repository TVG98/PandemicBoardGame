package Controller.Behavior;

import Model.City;
import Model.Player;

/**
 * Implements the normal charterflight functionality.
 * @author Thimo van Velzen, Daniel Paans
 */

public class CharterFlightBehaviorNormal implements CharterFlightBehavior {

    public void charterFlight(Player currentPlayer, City chosenCity) {
        if (playerController.checkCardInHandBasedOnCity(currentPlayer.getCurrentCity(), currentPlayer)) {
            currentPlayer.setCurrentCity(chosenCity);
            playerController.decrementActions(currentPlayer);
        }
    }
}
