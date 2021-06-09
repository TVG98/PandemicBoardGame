package Controller;

import Model.City;
import Model.Player;

public interface TreatDiseaseBehavior {
    void treatDisease(Player currentPlayer, City currentCity);
}