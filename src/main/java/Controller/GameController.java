package Controller;

import Model.*;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class GameController {

    Game game;
    private PlayerController playerController;
    private GameBoardController gameBoardController;


    public void changeTurn(){
        game.nextTurn();
    }

    public void checkLoss() {
        if(gameBoardController.lossByCubeAmount()) {
            game.setLost();
        }

        // Todo: loss by empty playerCardStack, loss by outbreakCounter
    }

    public void handleDrive() {

    }

    public void handleDirectFlight(City city) {

    }

    public void handleCharterFlight(City city) {

    }

    public void handleShuttleFlight(City city) {

    }

    public void handleBuildResearchStation() {
        gameBoardController.handleBuildResearchStation();
    }

    public void handleShareKnowledge(PlayerCard card) {
        City city = playerController.getPlayerCurrentCity(getCurrentPlayer());
        ArrayList<Player> playersInCity = game.getPlayersInCity(city);

        if (playersInCity.size() > 1) {

            Player chosenPlayer = game.getCurrentPlayer();//Todo choose player to share with/change this
            //game.getCurrentPlayer().getRole().shareKnowledge(card, chosenPlayer);
        }

        getCurrentPlayer().decrementActions();
    }

    public void handleTreatDisease() {
        gameBoardController.handleTreatDisease();
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

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }
}
