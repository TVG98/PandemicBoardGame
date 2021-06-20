package Controller.Behavior;

import Model.City;
import Model.Player;

/**
 * @author : Thimo van Velzen
 */

public class DirectFlightBehaviorNormal implements DirectFlightBehavior {

    public void directFlight(Player currentPlayer, City chosenCity) {
        playerController.setCurrentCity(currentPlayer, chosenCity);
        playerController.decrementActions(currentPlayer);
    }
}
