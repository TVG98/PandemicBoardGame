package Controller.Behavior.EventCards;

import Controller.GameBoardController;
import Controller.PlayerController;
import Model.InfectionCard;

import java.util.ArrayList;

public class ForecastBehavior {
    GameBoardController gameBoardController = GameBoardController.getInstance();

    public void play() {
        ArrayList<InfectionCard> topSixCards = gameBoardController.getTopSixCards();
        // Todo: De speler moet handmatig deze kaarten kunnen shuffelen

        gameBoardController.addTopSixCards(topSixCards);
    }

}
