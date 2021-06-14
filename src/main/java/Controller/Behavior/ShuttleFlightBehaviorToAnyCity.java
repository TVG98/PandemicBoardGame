package Controller.Behavior;

import Model.City;
import Model.Player;

public class ShuttleFlightBehaviorToAnyCity implements ShuttleFlightBehavior {

    @Override
    public void shuttleFlight(Player currentPlayer, City chosenCity) {
        if(gameBoardController.cityHasResearchStation(playerController.getPlayerCurrentCity(currentPlayer))) {
            currentPlayer.setCurrentCity(chosenCity);
            playerController.decrementActions(currentPlayer);
        }
    }
}
