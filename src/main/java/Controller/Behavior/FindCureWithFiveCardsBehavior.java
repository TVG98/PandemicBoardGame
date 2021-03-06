package Controller.Behavior;

import Model.City;
import Model.Player;
import Model.VirusType;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements the normal find cure functionality.
 * @author Thimo van Velzen, Daniel Paans
 */

public class FindCureWithFiveCardsBehavior implements FindCureBehavior {

    public void findCure(Player currentPlayer, City currentCity) {
        for(Map.Entry<VirusType, Integer> entry : playerController.getCardAmountOfEachVirusTypeInHand(currentPlayer).entrySet()) {
            if(entry.getValue() >= 5 && currentCity.getVIRUS_TYPE() == entry.getKey()) {
                gameboardController.handleCurePawn(currentCity.getVIRUS_TYPE());
            }
        }
        playerController.decrementActions(currentPlayer);
    }
}
