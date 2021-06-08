package Controller;

import Model.*;

import java.util.ArrayList;

public class GameBoardController {
    static GameBoardController gameBoardController;

    private TreatDiseaseBehavior treatDiseaseBehavior;
    private BuildResearchStationBehavior buildResearchStationBehavior;

    private Gameboard gameBoard;
    private final PlayerController playerController = PlayerController.getInstance();

    public static GameBoardController getInstance() {
        if (gameBoardController == null) {
            gameBoardController = new GameBoardController();
        }

        return gameBoardController;
    }

    public void handleDrive(Player currentPlayer) {
        ArrayList<City> nearCities = playerController.getPlayerCurrentCity(currentPlayer).getNearCities();
        //Todo: Vraag aan de speler om een stad te kiezen

        City chosenCity = new City("Tokyo", VirusType.RED);  // Hier komt het resultaat
        currentPlayer.setCurrentCity(chosenCity);


        currentPlayer.decrementActions();
    }

    public void handleDirectFlight(Player currentPlayer) {
        //Todo: Laat de speler een kaart uit zijn hand kiezen
        CityCard chosenCard = new CityCard(gameBoard.getCity("Tokyo"), VirusType.RED);  // Hier komt het resultaat
        currentPlayer.setCurrentCity(chosenCard.getCity());  // Ik stel voor om aan elke CityCard een stad en virusType te koppelen
    }

    public void handleCurePawn(Cure cure) {
        gameBoard.flipCurePawn(cure);
    }

    public void handlePlayerCardDraw(Player currentPlayer) {
        currentPlayer.addCardToHand(gameBoard.drawPlayerCard());
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
        gameBoard.addCityThatHadOutbreak(infectedCity);
        for(City city : infectedCity.getNearCities()) {
            if(infectedCity.getCubeAmount() >= 3 && !gameBoard.CityHadOutbreak(city)) {
                handleOutbreak(city);
            } else {
                gameBoard.addCubes(city, infectedCity.getVirusType());
            }
        }
    }

    public void handlePlayerPawnMovement(Player player) {

    }

    public void setBuildResearchStationBehavior(BuildResearchStationBehavior buildResearchStationBehavior) {
        this.buildResearchStationBehavior = buildResearchStationBehavior;
    }

    public void handleBuildResearchStation(Player currentPlayer, City currentCity) {
        buildResearchStationBehavior.buildResearchStation(currentPlayer, currentCity);
    }

    public boolean canBuildResearchStationWithoutCard(Player currentPlayer) {
        return currentPlayer.getRole().equals(Role.OPERATIONSEXPERT);
    }

    public void addResearchStationToCity(City city) {
        gameBoard.addResearchStationToCity(city);
    }

    public void setTreatDiseaseBehavior(TreatDiseaseBehavior treatDiseaseBehavior) {
        this.treatDiseaseBehavior = treatDiseaseBehavior;
    }

    public void handleTreatDisease(Player currentPlayer, City currentCity) {
        treatDiseaseBehavior.treatDisease(currentPlayer, currentCity);
    }

    public boolean canRemoveAllCubes(Player currentPlayer, City currentCity) {
        return currentPlayer.getRole().equals(Role.MEDIC) ||
                cureIsFound(currentCity);
    }

    public boolean canRemoveAllCubesWithoutDecrementActions(Player currentPlayer, City currentCity) {
        return currentPlayer.getRole().equals(Role.MEDIC) &&
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

    public boolean lossByEmptyPlayerCardStack() {
        return gameBoard.getPlayerStack().size() <= 0;
    }

    public boolean lossByOutbreakCounter() {
        return gameBoard.getOutbreakCounter() >= 8;
    }

    public boolean winByCures() {
        return gameBoard.getCuredDiseases().size() == 4;
    }
}
