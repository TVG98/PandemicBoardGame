package Controller;

import Model.City;
import Model.Player;

public class TreatDiseaseThreeCubesWithoutActionDecrement implements TreatDiseaseBehavior {

    @Override
    public void treatDisease(Player currentPlayer, City currentCity) {
        currentCity.removeAllCubes();
    }
}
