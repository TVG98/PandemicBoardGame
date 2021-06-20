package Controller.Behavior;

import Controller.PlayerController;
import Model.City;
import Model.Player;

/**
 * @author : Thimo van Velzen
 */

public class TreatDiseaseThreeCubes implements TreatDiseaseBehavior {

    PlayerController playerController = PlayerController.getInstance();

    @Override
    public void treatDisease(Player currentPlayer, City currentCity) {
        currentCity.removeAllCubes();
        playerController.decrementActions(currentPlayer);
    }
}
