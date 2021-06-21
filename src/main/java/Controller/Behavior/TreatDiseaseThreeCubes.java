package Controller.Behavior;

import Controller.PlayerController;
import Exceptions.CityNotFoundException;
import Model.City;
import Model.Player;

/**
 * Implements the treat disease functionality when the player has the Medic role or the cure is found.
 * @author Thimo van Velzen
 */

public class TreatDiseaseThreeCubes implements TreatDiseaseBehavior {

    PlayerController playerController = PlayerController.getInstance();

    @Override
    public void treatDisease(Player currentPlayer, City currentCity) {
        gameBoardController.removeCube(currentCity,3);
        playerController.decrementActions(currentPlayer);
    }
}
