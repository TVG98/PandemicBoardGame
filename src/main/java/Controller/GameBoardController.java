package Controller;

import Controller.Behavior.*;
import Exceptions.CityNotFoundException;
import Exceptions.CureNotFoundException;
import Model.*;
import Observers.GameBoardObserver;
import com.google.cloud.firestore.DocumentSnapshot;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public void handleDrive(Player currentPlayer, City chosenCity) {
        driveBehavior.drive(currentPlayer, chosenCity);
    }

    public void setDriveBehavior(DriveBehaviorNormal driveBehavior) {
        this.driveBehavior = driveBehavior;
    }

    public void handleDirectFlight(Player currentPlayer, City chosenCity) {
        directFlightBehavior.directFlight(currentPlayer, chosenCity);
    }

    public void setDirectFlightBehavior(DirectFlightBehaviorNormal directFlightBehavior) {
        this.directFlightBehavior = directFlightBehavior;
    }

    public void handleCharterFlight(Player currentPlayer, City chosenCity) {
        charterFlightBehavior.charterFlight(currentPlayer, chosenCity);
    }

    public void setCharterFlightBehavior(CharterFlightBehaviorNormal charterFlightBehavior) {
        this.charterFlightBehavior = charterFlightBehavior;
    }

    public void handleShuttleFlight(Player currentPlayer, City chosenCity) {
        shuttleFlightBehavior.shuttleFlight(currentPlayer, chosenCity);
    }

    public boolean canShuttleFlightToAnyCity(Player currrentPlayer) {
        return playerController.hasRole(currrentPlayer, Role.OPERATIONSEXPERT);
    }

    public void setShuttleFlightBehavior(ShuttleFlightBehavior shuttleFlightBehavior) {
        this.shuttleFlightBehavior = shuttleFlightBehavior;
    }

    public void handleFindCure(Player currentPlayer, City chosenCity) {
        findCureBehavior.findCure(currentPlayer, chosenCity);
        updateGameBoardInServer();
    }

    public void setFindCureBehavior(FindCureBehavior findCureBehavior) {
        this.findCureBehavior = findCureBehavior;
    }

    public void handleCurePawn(VirusType virusType) {
        try {
            Cure cure = gameBoard.getCureWithVirusType(virusType);
            gameBoard.flipCurePawn(cure);
            updateGameBoardInServer();
        } catch (CureNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handlePlayerCardDraw(Player currentPlayer, int playersAmount) {
        for (int i = 0; i < 2 + (4 - playersAmount); i++) {
            playerController.addCard(gameBoard.drawPlayerCard(), currentPlayer);
        }

        updateGameBoardInServer();
    }

    public void handleEpidemicCard() {
        gameBoard.handleEpidemicCard();
        updateGameBoardInServer();
    }

    // Override
    public void handleInfectionCardDraw() {
        gameBoard.handleInfectionCardDraw(1);
        updateGameBoardInServer();
    }

    public void handleInfectionCardDraw(int cubeAmount) {
        gameBoard.handleInfectionCardDraw(cubeAmount);
        updateGameBoardInServer();
    }

    public void initializeStartingCubes() {
        gameBoard.initializeStartingCubes();
        updateGameBoardInServer();
    }

    public void handleOutbreak(City infectedCity) {
        gameBoard.handleOutbreak(infectedCity);
        updateGameBoardInServer();
    }

    public ArrayList<InfectionCard> getTopSixCards() {
        ArrayList<InfectionCard> infectionCards = gameBoard.getTopSixInfectionStack();
        updateGameBoardInServer();
        return infectionCards;
    }

    public void addTopSixCards(ArrayList<InfectionCard> cards) {
        gameBoard.addInfectionStack(cards);
        updateGameBoardInServer();
    }

    public void handlePlayerPawnMovement(Player player) {

    }

    public void setBuildResearchStationBehavior(BuildResearchStationBehavior buildResearchStationBehavior) {
        this.buildResearchStationBehavior = buildResearchStationBehavior;
    }

    public void handleBuildResearchStation(Player currentPlayer, City currentCity) {
        buildResearchStationBehavior.buildResearchStation(currentPlayer, currentCity);
        updateGameBoardInServer();
    }

    public boolean canBuildResearchStationWithoutCard(Player currentPlayer) {
        return playerController.hasRole(currentPlayer, Role.OPERATIONSEXPERT);
    }

    public void handleShareKnowledge(Player currentPlayer, Player chosenPlayer) {
        shareKnowledgeBehavior.shareKnowledge(currentPlayer, chosenPlayer);
        updateGameBoardInServer();
    }

    public void setShareKnowledgeBehavior(ShareKnowledgeBehavior shareKnowledgeBehavior) {
        this.shareKnowledgeBehavior = shareKnowledgeBehavior;
    }

    public void addResearchStationToCity(City city) {
        gameBoard.addResearchStationToCity(city);
        updateGameBoardInServer();
    }

    public void setTreatDiseaseBehavior(TreatDiseaseBehavior treatDiseaseBehavior) {
        this.treatDiseaseBehavior = treatDiseaseBehavior;
    }

    public void handleTreatDisease(Player currentPlayer, City currentCity) {
        treatDiseaseBehavior.treatDisease(currentPlayer, currentCity);
        updateGameBoardInServer();
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
        return gameBoard.cureIsFound(currentCity.getVirusType());
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
        return city.getCubeAmount() > 0;
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

    public void updateGameBoardInServer() {
        databaseController.updateGameBoard(gameBoard);
    }

    public void update(DocumentSnapshot snapshot) {
        if (gameBoard != null) {
            updateGameBoardLocal(snapshot);
        }
    }

    private void updateGameBoardLocal(DocumentSnapshot snapshot) {
        Map<String, Object> data = snapshot.getData();
        updateCitiesInGameBoard(data.get("cities").toString());
    }

    private void updateCitiesInGameBoard(String citiesString) {
        citiesString = getCityStringWithoutFirstAndLastChar(citiesString);
        String[] citiesArray = getSplittedCityStringsAsArray(citiesString);
        citiesArray = getCitiesWithoutCurlyBrackets(citiesArray);
        gameBoard.setCities(createAllCitiesFromDoc(citiesArray));
    }

    private String getCityStringWithoutFirstAndLastChar(String cities) {
        return cities.substring(1, cities.length() - 1);
    }

    private String[] getSplittedCityStringsAsArray(String cities) {
        return cities.split("}, ");
    }

    private String[] getCitiesWithoutCurlyBrackets(String[] citiesArray) {
        for (int i = 0; i < citiesArray.length; i++) {
            citiesArray[i] = citiesArray[i].substring(1);
        }

        return citiesArray;
    }

    private List<City> createAllCitiesFromDoc(String[] citiesArray) {
        ArrayList<City> cities = new ArrayList<>();

        for (String city : citiesArray) {
            cities.add(createCityFromDoc(city));
        }

        return cities;
    }

    private City createCityFromDoc(String cityString) {
        cityString = cityString + ",";
        String name = getCityNameFromString(cityString);
        VirusType virusType = getCityVirusTypeFromString(cityString);
        ArrayList<Cube> cubeAmount = getCityCubeAmountFromString(cityString, virusType);
        ArrayList<String> nearCities = getCityNearCitiesFromString(cityString);

        return new City(name, cubeAmount, virusType, nearCities);
    }

    private VirusType getCityVirusTypeFromString(String cityString) {
        String virusTypeString = cityString.split("virusType=")[1].split(",")[0];
        return VirusType.valueOf(virusTypeString);
    }

    private String getCityNameFromString(String cityString) {
        return cityString.split("name=")[1].split(",")[0];
    }

    private ArrayList<Cube> getCityCubeAmountFromString(String cityString, VirusType virusType) {
        String cubeAmountString = cityString.split("cubeAmount=")[1].split(",")[0];
        int cubeAmount = Integer.parseInt(cubeAmountString);
        ArrayList<Cube> cubes = new ArrayList<>();

        for (int i = 0; i < cubeAmount; i++) {
            cubes.add(new Cube(virusType));
        }

        return cubes;
    }

    private ArrayList<String> getCityNearCitiesFromString(String cityString) {
        String[] nearCities = cityString.split("\\[")[1].split("]")[0].split(", ");
        return new ArrayList<>(Arrays.asList(nearCities));
    }
}
