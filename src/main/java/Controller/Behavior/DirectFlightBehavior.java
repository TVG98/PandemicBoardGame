package Controller.Behavior;

import Controller.GameBoardController;
import Controller.PlayerController;
import Exceptions.CityNotFoundException;
import Model.City;
import Model.CityCard;
import Model.Player;
import Model.VirusType;

public class DirectFlightBehavior {

    GameBoardController gameBoardController = GameBoardController.getInstance();
    PlayerController playerController = PlayerController.getInstance();

    public void directFlight(Player currentPlayer, City chosenCity) {
        playerController.setCurrentCity(currentPlayer, chosenCity);
    }
}
