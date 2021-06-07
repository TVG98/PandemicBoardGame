package Controller;

import Model.*;

import java.util.ArrayList;

public class GameBoardController {
    private Gameboard gameBoard;
    private GameController gameController;
    private PlayerController playerController;

    public void handleDrive() {
        Player currentPlayer = gameController.getCurrentPlayer();
        ArrayList<City> nearCities = playerController.getPlayerCurrentCity(currentPlayer).getNearCities();
        //Todo: Vraag aan de speler om een stad te kiezen

        City chosenCity = new City("Tokyo", VirusType.RED);  // Hier komt het resultaat
        currentPlayer.setCurrentCity(chosenCity);


        currentPlayer.decrementActions();
    }

    public void handleDirectFlight() {
        Player currentPlayer = gameController.getCurrentPlayer();
        //Todo: Laat de speler een kaart uit zijn hand kiezen
        CityCard chosenCard = new CityCard(gameBoard.getCity("Tokyo"), VirusType.RED);  // Hier komt het resultaat
        currentPlayer.setCurrentCity(chosenCard.getCity());  // Ik stel voor om aan elke CityCard een stad en virusType te koppelen
    }

    public void handleCurePawn(Cure cure) {
        gameBoard.flipCurePawn(cure);
    }

    public void handlePlayerCardDraw() {
        gameController.getCurrentPlayer().addCardToHand(gameBoard.drawPlayerCard());
    }

    public void handleEpidemicCard() {

    }

    public void handleInfection() {
        City infectedCity = gameBoard.drawInfectionCard().getCity();
        if(infectedCity.getCubeAmount() >= 3) {  // Hier moet de quarantine specialist nog toegevoegd worden
                handleOutbreak(infectedCity);
        } else {
            gameBoard.addCubes(infectedCity, infectedCity.getVirusType());
        }
    }

    public void handleOutbreak(City infectedCity) {
        gameBoard.increaseOutbreakCounter();
        for(City city : infectedCity.getNearCities()) {
            if(infectedCity.getCubeAmount() >= 3) {
                handleOutbreak(city);
            } else {
                gameBoard.addCubes(city, infectedCity.getVirusType());
            }
        }
    }

    public void handlePlayerPawnMovement(Player player) {

    }

    public void handleBuildResearchStation() {
        Player currentPlayer = gameController.getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);

        if (canAddResearchStation()) {
            gameBoard.addResearchStationToCity(currentCity);
        }
        //TODO: check if player needs to hand over card of city
        playerController.decrementActions(currentPlayer);
    }

    public void handleTreatDisease() {
        Player currentPlayer = gameController.getCurrentPlayer();
        City currentCity = playerController.getPlayerCurrentCity(currentPlayer);
        treatDisease(currentPlayer, currentCity);

        if (!canRemoveAllCubesWithoutDecrementActions(currentPlayer, currentCity)) {
            playerController.decrementActions(currentPlayer);
        }
    }

    public void treatDisease(Player currentPlayer, City currentCity) {
        if (gameBoard.cityHasCube(currentCity)) {
            if (canRemoveAllCubes(currentPlayer, currentCity)) {
                currentCity.removeAllCubes();
            } else {
                currentCity.removeCube();
            }
        }
    }

    public boolean canRemoveAllCubes(Player currentPlayer, City currentCity) {
        return currentPlayer.getRole() == Role.MEDIC ||
                cureIsFound(currentCity);
    }

    public boolean canRemoveAllCubesWithoutDecrementActions(Player currentPlayer, City currentCity) {
        return currentPlayer.getRole() == Role.MEDIC &&
                cureIsFound(currentCity);
    }

    public boolean canAddResearchStation() {
        return gameBoard.gameboardHasResearchStationsLeft();
    }

    public boolean cureIsFound(City currentCity) {
        return gameBoard.cureIsFound(currentCity.getVirusType());
    }

    public boolean lossByCubeAmount() {
        for(Virus virus : gameBoard.getViruses()) {
            if(virus.getCubeAmount() < 0) {
                return true;
            }
        }

        return false;
    }
}
