package Controller.Behavior;

import Controller.PlayerController;
import Model.City;
import Model.Player;

public interface DriveBehavior {
    PlayerController playerController = PlayerController.getInstance();

    void drive(Player currentPlayer, City chosenCity);
}
