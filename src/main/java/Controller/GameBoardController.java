package Controller;

import Model.*;

import java.util.ArrayList;

public class GameBoardController {
    static GameBoardController gameBoardController;

    private TreatDiseaseBehavior treatDiseaseBehavior;
    private BuildResearchStationBehavior buildResearchStationBehavior;

    private final Gameboard gameBoard = new Gameboard();
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
        playerController.setCurrentCity(currentPlayer, chosenCard.getCity());
    }

    public void handleCurePawn(Cure cure) {
        gameBoard.flipCurePawn(cure);
    }

    public void handlePlayerCardDraw(Player currentPlayer) {
        playerController.addCard(gameBoard.drawPlayerCard(), currentPlayer);
    }

    public void handleEpidemicCard() {
        int[] infectionRates = new int[]{2, 2, 2, 3, 3, 4, 4};  // Dit moet nog een betere plek krijgen, misschien als attribuut in gameBoard

        gameBoard.addDrawnEpidemicCard();
        gameBoard.increaseInfectionRate(infectionRates[gameBoard.getDrawnEpidemicCards()]);

        gameBoard.handleInfectionCardsInEpidemic();
    }

    public void handleInfectionCardDraw(int cubeAmount) {
        int drawAmount = gameBoard.getInfectionRate();
        for(int i = 0; i < drawAmount; i++) {
            gameBoard.handleInfection(gameBoard.drawInfectionCard(), cubeAmount);
        }
    }

    public void handleOutbreak(City infectedCity) {
        gameBoard.handleOutbreak(infectedCity);
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
        return playerController.hasRole(currentPlayer, Role.MEDIC) || cureIsFound(currentCity);
    }

    public boolean canRemoveAllCubesWithoutDecrementActions(Player currentPlayer, City currentCity) {
        return playerController.hasRole(currentPlayer, Role.MEDIC) && cureIsFound(currentCity);
    }

    public boolean canAddResearchStation() {
        return gameBoard.gameboardHasResearchStationsLeft();
    }

    public boolean cureIsFound(City currentCity) {
        return gameBoard.cureIsFound(currentCity.getVirusType());
    }

    public boolean lossByCubeAmount() {
        return gameBoard.lossByCubeAmount();
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
