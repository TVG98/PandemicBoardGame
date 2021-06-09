package Controller;

import Model.City;
import Model.Player;
import Model.VirusType;

import java.util.ArrayList;

public class DriveBehavior {

    PlayerController playerController = PlayerController.getInstance();

    public void drive(Player currentPlayer) {
        ArrayList<City> nearCities = playerController.getPlayerCurrentCity(currentPlayer).getNearCities();
        //Todo: Vraag aan de speler om een stad te kiezen

        City chosenCity = new City("Tokyo", VirusType.RED);  // Hier komt het resultaat
        playerController.setCurrentCity(currentPlayer, chosenCity);
        playerController.decrementActions(currentPlayer);
    }
}
