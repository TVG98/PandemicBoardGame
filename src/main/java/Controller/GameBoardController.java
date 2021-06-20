package Controller;

import Controller.Behavior.*;
import Exceptions.CityNotFoundException;
import Exceptions.CureNotFoundException;
import Exceptions.GameLostException;
import Model.*;
import Observers.GameBoardObserver;


import java.util.*;

/**
 * @author : Thimo van Velzen, Daniel Paans
 */

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

    /**
     * @author : Thimo van Velzen
     */
    private GameBoardController() {
        playerController = PlayerController.getInstance();
        databaseController = DatabaseController.getInstance();
    }

    /**
     * @author : Thimo van Velzen
     */
    public static GameBoardController getInstance() {
        if (gameBoardController == null) {
            gameBoardController = new GameBoardController();
        }

        return gameBoardController;
    }

    /**
     * @author : Thimo van Velzen
     */
    public void makeGameBoard() {
        gameBoard = new Gameboard();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void makeWholeGameBoard() {
        gameBoard.makeCompleteGameBoard();
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void updateServer() {
        databaseController.updateGameBoardInDatabase(gameBoard);
    }

    /**
     * @author : Daniel Paans
     * @param currentPlayer
     * @param chosenCity
     */
    public void handleDrive(Player currentPlayer, City chosenCity) {
        driveBehavior.drive(currentPlayer, chosenCity);
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void setDriveBehavior(DriveBehaviorNormal driveBehavior) {
        this.driveBehavior = driveBehavior;
    }

    /**
     * @author : Thimo van Velzen
     */
    public void handleDirectFlight(Player currentPlayer, City chosenCity) {
        directFlightBehavior.directFlight(currentPlayer, chosenCity);
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void setDirectFlightBehavior(DirectFlightBehaviorNormal directFlightBehavior) {
        this.directFlightBehavior = directFlightBehavior;
    }

    /**
     * @author : Thimo van Velzen
     */
    public void handleCharterFlight(Player currentPlayer, City chosenCity) {
        charterFlightBehavior.charterFlight(currentPlayer, chosenCity);
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void setCharterFlightBehavior(CharterFlightBehaviorNormal charterFlightBehavior) {
        this.charterFlightBehavior = charterFlightBehavior;
    }

    /**
     * @author : Thimo van Velzen
     */
    public void handleShuttleFlight(Player currentPlayer, City chosenCity) {
        shuttleFlightBehavior.shuttleFlight(currentPlayer, chosenCity);
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public boolean canShuttleFlightToAnyCity(Player currentPlayer) {
        return playerController.hasRole(currentPlayer, Role.OPERATIONSEXPERT);
    }

    /**
     * @author : Thimo van Velzen
     */
    public void setShuttleFlightBehavior(ShuttleFlightBehavior shuttleFlightBehavior) {
        this.shuttleFlightBehavior = shuttleFlightBehavior;
    }

    /**
     * @author : Thimo van Velzen
     */
    public void handleFindCure(Player currentPlayer, City chosenCity) {
        findCureBehavior.findCure(currentPlayer, chosenCity);
        updateServer();
    }

    /**
     * @author : Thimo van Velzen, Daniel Paans
     */
    public void setFindCureBehavior(FindCureBehavior findCureBehavior) {
        this.findCureBehavior = findCureBehavior;
    }

    /**
     * @author : Thimo van Velzen, Daniel Paans
     */
    public void handleCurePawn(VirusType virusType) {
        try {
            Cure cure = gameBoard.getCureWithVirusType(virusType);
            gameBoard.flipCurePawn(cure);
            updateServer();
        } catch (CureNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author : Thimo van Velzen, Daniel Paans
     */
    public void handlePlayerCardDraw(Player currentPlayer, int playersAmount) throws GameLostException {
        for (int i = 0; i < 2 + (4 - playersAmount); i++) {
            playerController.addCard(gameBoard.drawPlayerCard(), currentPlayer);
        }

        updateServer();
    }

    public PlayerCard drawPlayerCard() throws GameLostException {
        return gameBoard.drawPlayerCard();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void handleEpidemicCard() {
        gameBoard.handleEpidemicCard();
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void handleInfectionCardDraw() throws GameLostException {
        gameBoard.handleInfectionCardDraw(1);
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void handleInfectionCardDraw(int cubeAmount) throws GameLostException {
        gameBoard.handleInfectionCardDraw(cubeAmount);
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void handleOutbreak(City infectedCity) throws GameLostException {
        gameBoard.handleOutbreak(infectedCity);
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public List<City> getCities() {
        return gameBoard.getCities();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void setBuildResearchStationBehavior(BuildResearchStationBehavior buildResearchStationBehavior) {
        this.buildResearchStationBehavior = buildResearchStationBehavior;
    }

    /**
     * @author : Thimo van Velzen
     */
    public void handleBuildResearchStation(Player currentPlayer, City currentCity) {
        buildResearchStationBehavior.buildResearchStation(currentPlayer, currentCity);
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public boolean canBuildResearchStationWithoutCard(Player currentPlayer) {
        return playerController.hasRole(currentPlayer, Role.OPERATIONSEXPERT);
    }

    /**
     * @author : Daniel Paans
     */
    public void handleShareKnowledge(Player currentPlayer, Player chosenPlayer, boolean giveCard) {
        shareKnowledgeBehavior.shareKnowledge(currentPlayer, chosenPlayer, giveCard);
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void setShareKnowledgeBehavior(ShareKnowledgeBehavior shareKnowledgeBehavior) {
        this.shareKnowledgeBehavior = shareKnowledgeBehavior;
    }

    /**
     * @author : Thimo van Velzen
     */
    public void addResearchStationToCity(City city) {
        gameBoard.addResearchStationToCity(city);
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void setTreatDiseaseBehavior(TreatDiseaseBehavior treatDiseaseBehavior) {
        this.treatDiseaseBehavior = treatDiseaseBehavior;
    }

    /**
     * @author : Thimo van Velzen
     */
    public void handleTreatDisease(Player currentPlayer, City currentCity) {
        treatDiseaseBehavior.treatDisease(currentPlayer, currentCity);
        updateServer();
    }

    /**
     * @author : Thimo van Velzen
     */
    public boolean cityHasResearchStation(City city) {
        return gameBoard.cityHasResearchStation(city);
    }

    /**
     * @author : Daniel Paans
     * @param givingPlayer
     * @return
     */
    public boolean canShareAnyCard(Player givingPlayer) {
        return playerController.hasRole(givingPlayer, Role.RESEARCHER);
    }

    /**
     * @author : Thimo van Velzen
     */
    public boolean canRemoveAllCubes(Player currentPlayer, City currentCity) {
        boolean isMedic = playerController.hasRole(currentPlayer, Role.MEDIC);
        return isMedic || cureIsFound(currentCity);
    }

    /**
     * @author : Thimo van Velzen
     */
    public boolean canRemoveAllCubesWithoutDecrementActions(Player currentPlayer, City currentCity) {
        boolean isMedic = playerController.hasRole(currentPlayer, Role.MEDIC);
        return isMedic && cureIsFound(currentCity);
    }

    /**
     * @author : Thimo van Velzen
     */
    public boolean canAddResearchStation() {
        return gameBoard.gameboardHasResearchStationsLeft();
    }

    public boolean cureIsFound(City currentCity) {
        return gameBoard.cureIsFound(currentCity.getVIRUS_TYPE());
    }

    /**
     * @author : Thimo van Velzen
     */
    public boolean canFindCureWithFourCards(Player currentPlayer) {
        return playerController.hasRole(currentPlayer, Role.SCIENTIST);
    }

    /**
     * @author : Daniel Paans
     * @return
     */
    public boolean winByCures() {
        return gameBoard.getCuredDiseases().size() == 4;
    }

    public boolean cityHasCube(City city) {
        return city.getCubes().size() > 0;
    }

    /**
     * @author : Thimo van Velzen
     */
    public City getCity(String cityName) throws CityNotFoundException {
        return gameBoard.getCity(cityName);
    }

    public void notifyGameBoardObserver() {
        gameBoard.notifyAllObservers();
    }

    public void registerObserver(GameBoardObserver observer) {
        gameBoard.register(observer);
    }

    /**
     * @author : Thimo van Velzen
     */
    public void update(DatabaseData data) {
        if (data.getGameboard() != null) {
            gameBoard = data.getGameboard();
        }
    }
}
