package Controller.Behavior;

import Controller.GameBoardController;
import Controller.GameController;
import Exceptions.CardNotFoundException;
import Model.City;
import Model.Player;
import Model.PlayerCard;

/**
 * @author : Thimo van Velzen, Daniel Paans
 */

public class DirectFlightBehaviorNormal implements DirectFlightBehavior {
    GameController gameController = GameController.getInstance();

    public void directFlight(Player currentPlayer, City chosenCity) {
        playerController.setCurrentCity(currentPlayer, chosenCity);
      /*  try {
            playerController.removeCard(gameController.getCurrentPlayer().getCardFromHandByCity(chosenCity), currentPlayer);
        } catch (CardNotFoundException e) {
            e.printStackTrace();
        }*/


        playerController.decrementActions(currentPlayer);
    }
}
