package Controller;

import Model.City;
import Model.Player;

public class TreatDiseaseThreeCubes implements TreatDiseaseBehavior {

    PlayerController playerController = PlayerController.getInstance();

    @Override
    public void treatDisease(Player currentPlayer, City currentCity) {
        currentCity.removeAllCubes();
        playerController.decrementActions(currentPlayer);
    }
}
