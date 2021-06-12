package Controller.Behavior;

import Controller.GameBoardController;
import Controller.PlayerController;
import Model.City;
import Model.Player;

public interface ShuttleFlightBehavior {
    GameBoardController gameBoardController = GameBoardController.getInstance();
    PlayerController playerController = PlayerController.getInstance();

    void shuttleFlight(Player currentPlayer, City currentCity);
}
