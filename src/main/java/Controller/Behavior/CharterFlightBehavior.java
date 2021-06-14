package Controller.Behavior;

import Controller.PlayerController;
import Model.City;
import Model.Player;

public interface CharterFlightBehavior {
    PlayerController playerController = PlayerController.getInstance();

    void charterFlight(Player currentPlayer, City chosenCity);
}
