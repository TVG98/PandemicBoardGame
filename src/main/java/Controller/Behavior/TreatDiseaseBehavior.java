package Controller.Behavior;

import Model.City;
import Model.Player;

/**
 * @author : Thimo van Velzen
 */

public interface TreatDiseaseBehavior {
    void treatDisease(Player currentPlayer, City currentCity);
}
