package Controller;

import Model.*;

import java.util.ArrayList;

public class GameController {
    Game game;
    private PlayerController playerController;
    private GameBoardController gameBoardController;


    public void changeTurn(){
        game.nextTurn();
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
        City city = playerController.getPlayerCurrentCity(game.getCurrentPlayer());
        ArrayList<Player> playersInCity = game.getPlayersInCity(city);

        if (playersInCity.size() > 1) {

            Player chosenPlayer = game.getCurrentPlayer();//Todo choose player to share with/change this
            game.getCurrentPlayer().getRole().shareKnowledge(card, chosenPlayer);
        }
    }

    public void handleTreatDisease() {

    }

    public void handleFindCure() {

    }

    public void checkCardInHand(PlayerCard card, Player player) {

    }

    public void handleGiveCard(PlayerCard card, Player player1, Player player2) {

    }

    public void decrementActions(Player player) {
        player.decrementActions();
    }

}
