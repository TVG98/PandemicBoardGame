package Controller.Behavior;

import Model.City;
import Model.Player;

/**
 * @author : Thimo van Velzen, Daniel Paans
 */

public class DriveBehaviorNormal implements DriveBehavior {

    public void drive(Player currentPlayer, City chosenCity) {
        playerController.setCurrentCity(currentPlayer, chosenCity);
        playerController.decrementActions(currentPlayer);
    }
}
