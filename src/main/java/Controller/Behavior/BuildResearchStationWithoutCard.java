package Controller.Behavior;

import Model.City;
import Model.Player;

/**
 * Implements the build research station functionality if you have the Operations Expert role.
 * @author Thimo van Velzen
 */

public class BuildResearchStationWithoutCard implements BuildResearchStationBehavior {

    @Override
    public void buildResearchStation(Player currentPlayer, City currentCity) {
        gameBoardController.addResearchStationToCity(currentCity);
        playerController.decrementActions(currentPlayer);
    }
}
