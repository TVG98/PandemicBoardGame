package Controller.Behavior;

import Model.City;
import Model.Player;

/**
 * @author : Thimo van Velzen
 */

public class BuildResearchStationWithoutCard implements BuildResearchStationBehavior {

    @Override
    public void buildResearchStation(Player currentPlayer, City currentCity) {
        gameBoardController.addResearchStationToCity(currentCity);
        playerController.decrementActions(currentPlayer);
    }
}
