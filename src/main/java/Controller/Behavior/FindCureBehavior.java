package Controller.Behavior;

import Controller.PlayerController;
import Model.City;
import Model.Player;

public interface FindCureBehavior {
    PlayerController playerController = PlayerController.getInstance();

    void findCure(Player currentPlayer, City currentCity);
}
