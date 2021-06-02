package Controller;

import Model.City;
import Model.Game;
import Model.Player;
import Model.PlayerCard;

public class GameController {
    Game game;
    private PlayerController playerController;
    private GameBoardController gameBoardController;

    public void changeTurn(){

    }

    public void handleDrive(City city) {

    }

    public void handleDirectFlight(City city) {

    }

    public void handleCharterFlight(City city) {

    }

    public void handleShuttleFlight(City city) {

    }

    public void handleBuildResearchStation() {

    }

    public void handleShareKnowledge(PlayerCard card) {

    }

    public void handleTreatDisease() {

    }

    public void handleFindCure() {

    }

    public void checkCardInHand(PlayerCard card, Player player) {
        playerController.checkCardInHand(card, player);
    }

    public void handleGiveCard(PlayerCard card, Player player1, Player player2) {
        playerController.removeCard(card, player1);
        playerController.addCard(card, player2);
    }

    public void decrementActions(Player player) {

    }

}
