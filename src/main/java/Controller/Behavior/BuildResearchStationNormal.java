package Controller.Behavior;

import Model.City;
import Model.Player;

/**
 * Implements the normal build research station functionality.
 * @author Thimo van Velzen
 */

public class BuildResearchStationNormal implements BuildResearchStationBehavior {

    @Override
    public void buildResearchStation(Player currentPlayer, City currentCity) {
        gameBoardController.addResearchStationToCity(currentCity);
        playerController.decrementActions(currentPlayer);
    }
}
