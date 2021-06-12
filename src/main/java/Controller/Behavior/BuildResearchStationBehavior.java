package Controller.Behavior;

import Controller.GameBoardController;
import Controller.PlayerController;
import Model.City;
import Model.Player;

public interface BuildResearchStationBehavior {
    GameBoardController gameBoardController = GameBoardController.getInstance();
    PlayerController playerController = PlayerController.getInstance();

    void buildResearchStation(Player currentPlayer, City currentCity);
}
