package Controller;

import Controller.Behavior.*;
import Exceptions.CityNotFoundException;
import Exceptions.CureNotFoundException;
import Exceptions.GameLostException;
import Model.*;
import Observers.GameBoardObserver;


import java.util.*;

public class GameBoardController {
    static GameBoardController gameBoardController;

    private TreatDiseaseBehavior treatDiseaseBehavior;
    private BuildResearchStationBehavior buildResearchStationBehavior;
    private DriveBehaviorNormal driveBehavior;
    private DirectFlightBehaviorNormal directFlightBehavior;
    private CharterFlightBehaviorNormal charterFlightBehavior;
    private ShuttleFlightBehavior shuttleFlightBehavior;
    private FindCureBehavior findCureBehavior;
    private ShareKnowledgeBehavior shareKnowledgeBehavior;

    private Gameboard gameBoard;
    private final PlayerController playerController;
    private final DatabaseController databaseController;

    private GameBoardController() {
        playerController = PlayerController.getInstance();
        databaseController = DatabaseController.getInstance();
    }

    public static GameBoardController getInstance() {
        if (gameBoardController == null) {
            gameBoardController = new GameBoardController();
        }

        return gameBoardController;
    }

    public void makeGameBoard() {
        gameBoard = new Gameboard();
    }

    public void makeWholeGameBoard() {
        gameBoard.makeCompleteGameBoard();
        updateServer();
    }

    public void updateServer() {
        databaseController.updateGameBoardInDatabase(gameBoard);
    }

    public void handleDrive(Player currentPlayer, City chosenCity) {
        driveBehavior.drive(currentPlayer, chosenCity);
        updateServer();
    }

    public void setDriveBehavior(DriveBehaviorNormal driveBehavior) {
        this.driveBehavior = driveBehavior;
    }

    public void handleDirectFlight(Player currentPlayer, City chosenCity) {
        directFlightBehavior.directFlight(currentPlayer, chosenCity);
        updateServer();
    }

    public void setDirectFlightBehavior(DirectFlightBehaviorNormal directFlightBehavior) {
        this.directFlightBehavior = directFlightBehavior;
    }

    public void handleCharterFlight(Player currentPlayer, City chosenCity) {
        charterFlightBehavior.charterFlight(currentPlayer, chosenCity);
        updateServer();
    }

    public void setCharterFlightBehavior(CharterFlightBehaviorNormal charterFlightBehavior) {
        this.charterFlightBehavior = charterFlightBehavior;
    }

    public void handleShuttleFlight(Player currentPlayer, City chosenCity) {
        shuttleFlightBehavior.shuttleFlight(currentPlayer, chosenCity);
        updateServer();
    }

    public boolean canShuttleFlightToAnyCity(Player currentPlayer) {
        return playerController.hasRole(currentPlayer, Role.OPERATIONSEXPERT);
    }

    public void setShuttleFlightBehavior(ShuttleFlightBehavior shuttleFlightBehavior) {
        this.shuttleFlightBehavior = shuttleFlightBehavior;
    }

    public void handleFindCure(Player currentPlayer, City chosenCity) {
        findCureBehavior.findCure(currentPlayer, chosenCity);
        updateServer();
    }

    public void setFindCureBehavior(FindCureBehavior findCureBehavior) {
        this.findCureBehavior = findCureBehavior;
    }

    public void handleCurePawn(VirusType virusType) {
        try {
            Cure cure = gameBoard.getCureWithVirusType(virusType);
            gameBoard.flipCurePawn(cure);
            updateServer();
        } catch (CureNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handlePlayerCardDraw(Player currentPlayer, int playersAmount) throws GameLostException {
        for (int i = 0; i < 2 + (4 - playersAmount); i++) {
            playerController.addCard(gameBoard.drawPlayerCard(), currentPlayer);
        }

        updateServer();
    }

    public void handleEpidemicCard() {
        gameBoard.handleEpidemicCard();
        updateServer();
    }

    public void handleInfectionCardDraw() throws GameLostException {
        gameBoard.handleInfectionCardDraw(1);
        updateServer();
    }

    public void handleInfectionCardDraw(int cubeAmount) throws GameLostException {
        gameBoard.handleInfectionCardDraw(cubeAmount);
        updateServer();
    }

    public void handleOutbreak(City infectedCity) throws GameLostException {
        gameBoard.handleOutbreak(infectedCity);
        updateServer();
    }

    public List<City> getCities() {
        return gameBoard.getCities();
    }

    public void setBuildResearchStationBehavior(BuildResearchStationBehavior buildResearchStationBehavior) {
        this.buildResearchStationBehavior = buildResearchStationBehavior;
    }

    public void handleBuildResearchStation(Player currentPlayer, City currentCity) {
        buildResearchStationBehavior.buildResearchStation(currentPlayer, currentCity);
        updateServer();
    }

    public boolean canBuildResearchStationWithoutCard(Player currentPlayer) {
        return playerController.hasRole(currentPlayer, Role.OPERATIONSEXPERT);
    }

    public void handleShareKnowledge(Player currentPlayer, Player chosenPlayer) {
        shareKnowledgeBehavior.shareKnowledge(currentPlayer, chosenPlayer);
        updateServer();
    }

    public void setShareKnowledgeBehavior(ShareKnowledgeBehavior shareKnowledgeBehavior) {
        this.shareKnowledgeBehavior = shareKnowledgeBehavior;
    }

    public void addResearchStationToCity(City city) {
        gameBoard.addResearchStationToCity(city);
        updateServer();
    }

    public void setTreatDiseaseBehavior(TreatDiseaseBehavior treatDiseaseBehavior) {
        this.treatDiseaseBehavior = treatDiseaseBehavior;
    }

    public void handleTreatDisease(Player currentPlayer, City currentCity) {
        treatDiseaseBehavior.treatDisease(currentPlayer, currentCity);
        updateServer();
    }

    public boolean cityHasResearchStation(City city) {
        return gameBoard.cityHasResearchStation(city);
    }

    public boolean canShareAnyCard(Player givingPlayer, Player receivingPlayer) {
        return givingPlayer.getRole() == Role.RESEARCHER || receivingPlayer.getRole() == Role.RESEARCHER;
    }

    public boolean canRemoveAllCubes(Player currentPlayer, City currentCity) {
        boolean isMedic = playerController.hasRole(currentPlayer, Role.MEDIC);
        return isMedic || cureIsFound(currentCity);
    }

    public boolean canRemoveAllCubesWithoutDecrementActions(Player currentPlayer, City currentCity) {
        boolean isMedic = playerController.hasRole(currentPlayer, Role.MEDIC);
        return isMedic && cureIsFound(currentCity);
    }

    public boolean canAddResearchStation() {
        return gameBoard.gameboardHasResearchStationsLeft();
    }

    public boolean cureIsFound(City currentCity) {
        return gameBoard.cureIsFound(currentCity.getVIRUS_TYPE());
    }

    public boolean canFindCureWithFourCards(Player currentPlayer) {
        return playerController.hasRole(currentPlayer, Role.SCIENTIST);
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
        return city.getCubes().size() > 0;
    }

    public City getCity(String cityName) throws CityNotFoundException {
        return gameBoard.getCity(cityName);
    }

    public void notifyGameBoardObserver() {
        gameBoard.notifyAllObservers();
    }

    public void registerObserver(GameBoardObserver observer) {
        gameBoard.register(observer);
    }

    public void update(DatabaseData data) {
        if (data.getGameboard() != null) {
            gameBoard = data.getGameboard();
        }
    }
}
