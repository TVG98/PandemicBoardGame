package Controller.Behavior;

import Model.City;
import Model.Player;
import Model.VirusType;

public class ShuttleFlightBehaviorNormal implements ShuttleFlightBehavior{

    @Override
    public void shuttleFlight(Player currentPlayer, City chosenCity) {

        if(gameBoardController.cityHasResearchStation(playerController.getPlayerCurrentCity(currentPlayer))) {
            currentPlayer.setCurrentCity(chosenCity);
        }
    }
}
