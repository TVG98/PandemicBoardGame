package Controller;

import Model.*;

public class GameBoardController {
    static GameBoardController gameBoardController;

    private TreatDiseaseBehavior treatDiseaseBehavior;
    private BuildResearchStationBehavior buildResearchStationBehavior;
    private DriveBehavior driveBehavior;
    private DirectFlightBehavior directFlightBehavior;

    private final Gameboard gameBoard;
    private final PlayerController playerController;

    private GameBoardController() {
        gameBoard = new Gameboard();
        playerController = PlayerController.getInstance();
    }

    public static GameBoardController getInstance() {
        if (gameBoardController == null) {
            gameBoardController = new GameBoardController();
        }

        return gameBoardController;
    }

    public void handleDrive(Player currentPlayer) {
        driveBehavior.drive(currentPlayer);
    }

    public void handleDirectFlight(Player currentPlayer) {
        directFlightBehavior.directFlight(currentPlayer);
    }

    public void handleCurePawn(Cure cure) {
        gameBoard.flipCurePawn(cure);
    }

    public void handlePlayerCardDraw(Player currentPlayer) {
        playerController.addCard(gameBoard.drawPlayerCard(), currentPlayer);
    }

    public void handleEpidemicCard() {
        gameBoard.handleEpidemicCard();
    }

    public void handleInfectionCardDraw(int cubeAmount) {
        int drawAmount = gameBoard.getInfectionRate();
        for (int i = 0; i < drawAmount; i++) {
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

    public boolean cityHasCube(City city) {
        return city.getCubeAmount() > 0;
    }

    public City getCity(String cityName) {
        return gameBoard.getCity(cityName);
    }
}
