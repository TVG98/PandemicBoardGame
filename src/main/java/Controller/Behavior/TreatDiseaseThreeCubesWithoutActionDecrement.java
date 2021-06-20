package Controller.Behavior;

import Exceptions.CityNotFoundException;
import Model.City;
import Model.Player;

/**
 * @author : Thimo van Velzen
 */

public class TreatDiseaseThreeCubesWithoutActionDecrement implements TreatDiseaseBehavior {

    @Override
    public void treatDisease(Player currentPlayer, City currentCity) throws CityNotFoundException {
        gameBoardController.getCity(currentCity.getName()).removeAllCubes();
    }
}
