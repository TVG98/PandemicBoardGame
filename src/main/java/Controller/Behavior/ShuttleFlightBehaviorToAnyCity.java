package Controller.Behavior;

import Model.City;
import Model.Player;

/**
 * Implements the shuttle flight functionality when the player has the Operations Expert role.
 * @author Thimo van Velzen, Daniel Paans
 */

public class ShuttleFlightBehaviorToAnyCity implements ShuttleFlightBehavior {

    @Override
    public void shuttleFlight(Player currentPlayer, City chosenCity) {
        if(gameBoardController.cityHasResearchStation(playerController.getPlayerCurrentCity(currentPlayer))) {
            currentPlayer.setCurrentCity(chosenCity);
            playerController.decrementActions(currentPlayer);
        }
    }
}
