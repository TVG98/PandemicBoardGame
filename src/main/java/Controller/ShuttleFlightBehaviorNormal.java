package Controller;

import Model.City;
import Model.Player;
import Model.VirusType;

public class ShuttleFlightBehaviorNormal implements ShuttleFlightBehavior{

    @Override
    public void shuttleFlight(Player currentPlayer, City currentCity) {

        if(gameBoardController.cityHasResearchStation(currentCity)) {
            // Todo: Kies een stad met een researchstation
            City chosenCity = new City("Tokyo", VirusType.RED); // Hier komt de gekozen stad met een researchstation
            currentPlayer.setCurrentCity(chosenCity);
        }
    }
}
