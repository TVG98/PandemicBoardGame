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
        playerController.setCurrentCity(currentPlayer, chosenCity);
        playerController.decrementActions(currentPlayer);
    }

    public void handleDirectFlight(Player currentPlayer) {
        //Todo: Laat de speler een kaart uit zijn hand kiezen
        CityCard chosenCard = new CityCard(gameBoard.getCity("Tokyo"), VirusType.RED);  // Hier komt het resultaat
        playerController.setCurrentCity(currentPlayer, chosenCard.getCity());// Ik stel voor om aan elke CityCard een stad en virusType te koppelen
    }

    public void handleCurePawn(Cure cure) {
        gameBoard.flipCurePawn(cure);
    }

    public void handlePlayerCardDraw(Player currentPlayer) {
        playerController.addCard(gameBoard.drawPlayerCard(), currentPlayer);
    }

    public void handleEpidemicCard() {

    }

    // overloading
    public void handleInfectionCardDraw() {
        handleInfection(1, gameBoard.drawInfectionCard());
    }
    public void handleInfectionCardDraw(int cubeAmount) {
        handleInfection(cubeAmount, gameBoard.drawInfectionCard());
    }

    private void handleInfection(int cubeAmount, InfectionCard infectionCard) {
        City infectedCity = infectionCard.getCity();
        if(infectedCity.getCubeAmount() >= 3) {  // Hier moet de quarantine specialist nog toegevoegd worden
                handleOutbreak(infectedCity, cubeAmount);
        } else {
            gameBoard.addCubes(infectedCity, infectedCity.getVirusType(), cubeAmount);
        }
    }

    private void handleOutbreak(City infectedCity, int cubeAmount) {
        gameBoard.increaseOutbreakCounter();
        gameBoard.addCityThatHadOutbreak(infectedCity);
        for(City city : infectedCity.getNearCities()) {
            if(infectedCity.getCubeAmount() >= 3 && !gameBoard.CityHadOutbreak(city)) {  // Hier moet de quarantine specialist nog toegevoegd worden
                handleOutbreak(city, 1);
            } else {
                gameBoard.addCubes(city, infectedCity.getVirusType(), cubeAmount);
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
        return playerController.hasRole(currentPlayer, Role.OPERATIONSEXPERT);
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
        return playerController.hasRole(currentPlayer, Role.MEDIC) ||
                cureIsFound(currentCity);
    }

    public boolean canRemoveAllCubesWithoutDecrementActions(Player currentPlayer, City currentCity) {
        return playerController.hasRole(currentPlayer, Role.MEDIC) &&
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
