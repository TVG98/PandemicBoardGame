package Controller.Behavior;

import Exceptions.CityNotFoundException;
import Model.City;
import Model.Player;

/**
 * @author : Thimo van Velzen
 */

public class TreatDiseaseOneCube implements TreatDiseaseBehavior {

    @Override
    public void treatDisease(Player currentPlayer, City currentCity) {
        gameBoardController.removeCube(currentCity,1);
        playerController.decrementActions(currentPlayer);
    }
}
