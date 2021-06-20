package Controller.Behavior;

import Model.City;
import Model.Player;

/**
 * @author : Thimo van Velzen
 */

public class TreatDiseaseThreeCubesWithoutActionDecrement implements TreatDiseaseBehavior {

    @Override
    public void treatDisease(Player currentPlayer, City currentCity) {
        gameBoardController.removeCube(currentCity,3);
    }
}
