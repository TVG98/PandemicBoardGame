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

    public void charterFlight(Player currentPlayer, City chosenCity) {
        if(playerController.checkCardInHandBasedOnCity(currentPlayer.getCurrentCity(), currentPlayer)) {
            currentPlayer.setCurrentCity(chosenCity);
        }
    }
}
