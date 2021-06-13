package Controller.Behavior;

import Model.City;
import Model.Player;
import Model.VirusType;

public class ShuttleFlightBehaviorNormal implements ShuttleFlightBehavior{

    @Override
    public void shuttleFlight(Player currentPlayer) {

        if(gameBoardController.cityHasResearchStation(playerController.getPlayerCurrentCity(currentPlayer))) {
            // Todo: Kies een stad met een researchstation
            City chosenCity = new City("Tokyo", VirusType.RED); // Hier komt de gekozen stad met een researchstation
            currentPlayer.setCurrentCity(chosenCity);
        }
    }
}
