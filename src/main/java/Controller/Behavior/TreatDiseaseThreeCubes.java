package Controller.Behavior;

import Controller.PlayerController;
import Exceptions.CityNotFoundException;
import Model.City;
import Model.Player;

/**
 * @author : Thimo van Velzen
 */

public class TreatDiseaseThreeCubes implements TreatDiseaseBehavior {

    PlayerController playerController = PlayerController.getInstance();

    @Override
    public void treatDisease(Player currentPlayer, City currentCity) throws CityNotFoundException {
        gameBoardController.getCity(currentCity.getName()).removeAllCubes();
        playerController.decrementActions(currentPlayer);
    }
}
