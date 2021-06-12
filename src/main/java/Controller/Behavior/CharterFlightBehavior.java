package Controller.Behavior;

import Controller.GameBoardController;
import Controller.PlayerController;
import Exceptions.CityNotFoundException;
import Model.City;
import Model.CityCard;
import Model.Player;
import Model.VirusType;

public class CharterFlightBehavior {

    GameBoardController gameBoardController = GameBoardController.getInstance();
    PlayerController playerController = PlayerController.getInstance();

    public void charterFlight(Player currentPlayer) {
        if(playerController.checkCardInHandBasedOnCity(currentPlayer.getCurrentCity(), currentPlayer)) {
            // Todo: Laat de speler 1 van alle steden kiezen
            City chosenCity = new City("Tokyo", VirusType.RED);  // Hier komt de gekozen stad
            currentPlayer.setCurrentCity(chosenCity);
        }
    }
}
