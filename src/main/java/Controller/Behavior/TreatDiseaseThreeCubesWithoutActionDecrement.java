package Controller.Behavior;

import Model.City;
import Model.Player;

/**
 * Implements the treat disease functionality when the player has the Medic role and the cure is found.
 * @author Thimo van Velzen
 */

public class TreatDiseaseThreeCubesWithoutActionDecrement implements TreatDiseaseBehavior {

    @Override
    public void treatDisease(Player currentPlayer, City currentCity) {
        gameBoardController.removeCube(currentCity,3);
    }
}
