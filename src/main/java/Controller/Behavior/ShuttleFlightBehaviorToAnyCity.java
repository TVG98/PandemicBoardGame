package Controller.Behavior;

import Model.City;
import Model.Player;
import Model.VirusType;

public class ShuttleFlightBehaviorToAnyCity implements ShuttleFlightBehavior {

    @Override
    public void shuttleFlight(Player currentPlayer, City currentCity) {
        if(gameBoardController.cityHasResearchStation(currentCity)) {
            // Todo: Kiezen uit elke stad
            City chosenCity = new City("Tokyo", VirusType.RED); // Hier komt de gekozen stad
            currentPlayer.setCurrentCity(chosenCity);
        }
    }
}
