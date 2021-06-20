package Controller.Behavior;

import Controller.GameBoardController;
import Controller.PlayerController;
import Exceptions.CityNotFoundException;
import Model.City;
import Model.Player;

/**
 * @author : Thimo van Velzen
 */

public interface TreatDiseaseBehavior {

    PlayerController playerController = PlayerController.getInstance();
    GameBoardController gameBoardController = GameBoardController.getInstance();

    void treatDisease(Player currentPlayer, City currentCity) throws CityNotFoundException;
}
