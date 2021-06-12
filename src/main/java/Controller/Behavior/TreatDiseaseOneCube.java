package Controller.Behavior;

import Controller.PlayerController;
import Model.City;
import Model.Player;

public class TreatDiseaseOneCube implements TreatDiseaseBehavior {

    PlayerController playerController = PlayerController.getInstance();

    @Override
    public void treatDisease(Player currentPlayer, City currentCity) {
        currentCity.removeCube();
        playerController.decrementActions(currentPlayer);
    }
}
