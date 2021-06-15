package Controller.Behavior;

import Model.City;
import Model.Player;
import Model.VirusType;

import java.util.HashMap;
import java.util.Map;

public class FindCureWithFiveCardsBehavior implements FindCureBehavior {

    public void findCure(Player currentPlayer, City currentCity) {
        for(Map.Entry<VirusType, Integer> entry : playerController.getCardAmountOfEachVirusTypeInHand(currentPlayer).entrySet()) {
            if(entry.getValue() >= 5 && currentCity.getVirusType() == entry.getKey()) {
                gameboardController.cureIsFound(currentCity);
            }
        }
    }
}
