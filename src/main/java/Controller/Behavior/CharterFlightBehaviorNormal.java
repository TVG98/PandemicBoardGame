package Controller.Behavior;

import Model.City;
import Model.Player;

public class CharterFlightBehaviorNormal implements CharterFlightBehavior {

    public void charterFlight(Player currentPlayer, City chosenCity) {
        if (playerController.checkCardInHandBasedOnCity(currentPlayer.getCurrentCity(), currentPlayer)) {
            currentPlayer.setCurrentCity(chosenCity);
        }
    }
}
