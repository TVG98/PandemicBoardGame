package Controller.Behavior;

import Controller.PlayerController;
import Model.City;
import Model.Player;
import Model.VirusType;

import java.util.ArrayList;

public class DriveBehavior {

    PlayerController playerController = PlayerController.getInstance();

    public void drive(Player currentPlayer, City chosenCity) {
        playerController.setCurrentCity(currentPlayer, chosenCity);
        playerController.decrementActions(currentPlayer);
    }
}
