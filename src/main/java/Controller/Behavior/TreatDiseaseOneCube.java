package Controller.Behavior;

import Controller.GameBoardController;
import Controller.PlayerController;
import Exceptions.CityNotFoundException;
import Model.City;
import Model.Player;

/**
 * @author : Thimo van Velzen
 */

public class TreatDiseaseOneCube implements TreatDiseaseBehavior {

    @Override
    public void treatDisease(Player currentPlayer, City currentCity) throws CityNotFoundException {
        gameBoardController.getCity(currentCity.getName()).removeCube();
        playerController.decrementActions(currentPlayer);
    }
}
