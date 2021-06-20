package Controller.Behavior;

import Model.City;
import Model.Player;

/**
 * @author : Thimo van Velzen, Daniel Paans
 */

public class ShuttleFlightBehaviorNormal implements ShuttleFlightBehavior{

    @Override
    public void shuttleFlight(Player currentPlayer, City chosenCity) {

        if(gameBoardController.cityHasResearchStation(playerController.getPlayerCurrentCity(currentPlayer))) {
            currentPlayer.setCurrentCity(chosenCity);
            playerController.decrementActions(currentPlayer);
        }
    }
}
