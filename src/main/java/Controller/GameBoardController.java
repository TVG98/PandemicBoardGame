package Controller;

import Controller.Behavior.*;
import Exceptions.CityNotFoundException;
import Exceptions.CureNotFoundException;
import Model.*;
import Observers.GameBoardObserver;
import com.google.cloud.firestore.DocumentSnapshot;


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
        writeWholeGameBoardToServer();
    }

    public void writeWholeGameBoardToServer() {
        databaseController.updateCitiesInDatabase(gameBoard.getCities());
        databaseController.updateCitiesWithResearchStationsInDatabase(gameBoard.getCitiesWithResearchStations());
        databaseController.updateCuredDiseases(gameBoard.getCuredDiseases());
        databaseController.updateCures(gameBoard.getCures());
        databaseController.updateDrawnEpidemicCards(gameBoard.getDrawnEpidemicCards());
        databaseController.updateInfectionDiscardStack(gameBoard.getInfectionDiscardStack());
        databaseController.updateInfectionRate(gameBoard.getInfectionRate());
        databaseController.updateInfectionStack(gameBoard.getInfectionStack());
        databaseController.updateOutbreakCounter(gameBoard.getOutbreakCounter());
        databaseController.updatePlayerDiscardStack(gameBoard.getPlayerDiscardStack());
        databaseController.updatePlayerStack(gameBoard.getPlayerStack());
        databaseController.updateTopSixInfectionStack(gameBoard.getTopSixInfectionStack());
        databaseController.updateViruses(gameBoard.getViruses());
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
    }

    public void setFindCureBehavior(FindCureBehavior findCureBehavior) {
        this.findCureBehavior = findCureBehavior;
    }

    public void handleCurePawn(VirusType virusType) {
        try {
            Cure cure = gameBoard.getCureWithVirusType(virusType);
            gameBoard.flipCurePawn(cure);
        } catch (CureNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handlePlayerCardDraw(Player currentPlayer, int playersAmount) {
        for (int i = 0; i < 2 + (4 - playersAmount); i++) {
            playerController.addCard(gameBoard.drawPlayerCard(), currentPlayer);
        }
    }

    public void handleEpidemicCard() {
        gameBoard.handleEpidemicCard();
    }

    // Override
    public void handleInfectionCardDraw() {
        gameBoard.handleInfectionCardDraw(1);
    }

    public void handleInfectionCardDraw(int cubeAmount) {
        gameBoard.handleInfectionCardDraw(cubeAmount);
    }

    public void initializeStartingCubes() {
        gameBoard.initializeStartingCubes();
    }

    public void handleOutbreak(City infectedCity) {
        gameBoard.handleOutbreak(infectedCity);
        databaseController.updateCitiesInDatabase(gameBoard.getCities());
    }

    public ArrayList<InfectionCard> getTopSixCards() {
        ArrayList<InfectionCard> infectionCards = gameBoard.getTopSixInfectionStack();
        return infectionCards;
    }

    public void addTopSixCards(ArrayList<InfectionCard> cards) {
        gameBoard.addInfectionStack(cards);
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

    public void handleShareKnowledge(Player currentPlayer, Player chosenPlayer) {
        shareKnowledgeBehavior.shareKnowledge(currentPlayer, chosenPlayer);
    }

    public void setShareKnowledgeBehavior(ShareKnowledgeBehavior shareKnowledgeBehavior) {
        this.shareKnowledgeBehavior = shareKnowledgeBehavior;
    }

    public void addResearchStationToCity(City city) {
        gameBoard.addResearchStationToCity(city);
        databaseController.updateCitiesWithResearchStationsInDatabase(gameBoard.getCitiesWithResearchStations());
    }

    public void setTreatDiseaseBehavior(TreatDiseaseBehavior treatDiseaseBehavior) {
        this.treatDiseaseBehavior = treatDiseaseBehavior;
    }

    public void handleTreatDisease(Player currentPlayer, City currentCity) {
        treatDiseaseBehavior.treatDisease(currentPlayer, currentCity);
        databaseController.updateCitiesInDatabase(gameBoard.getCities());
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

    public void update(DocumentSnapshot snapshot) {
        if (gameBoard != null) {
            updateGameBoardLocal(snapshot);
        }
    }

    private void updateGameBoardLocal(DocumentSnapshot snapshot) {
        Map<String, Object> data = snapshot.getData();

        updateCitiesInGameBoard(data.get("cities").toString());
        updateCitiesInGameBoard(data.get("citiesWithResearchStations").toString());
        updateCuredDiseasesInGameBoard(data.get("curedDiseases").toString());
        updateCuresInGameBoard(data.get("cures").toString());
        updateDrawnEpidemicCardsInGameBoard(data.get("drawnEpidemicCards").toString());
        updateInfectionDiscardStackInGameBoard(data.get("infectionDiscardStack").toString());
        updateInfectionRateInGameBoard(data.get("infectionRate").toString());
        updateInfectionStackInGameBoard(data.get("infectionStack").toString());
        updateOutbreakCounterInGameBoard(data.get("outbreakCounter").toString());
//        updatePlayerDiscardStackInGameBoard(data.get("playerDiscardStack").toString());
//        updatePlayerStackInGameBoard(data.get("playerStack").toString());
//        updateTopSixInfectionStackInGameBoard(data.get("topSixInfectionStack").toString());
//        updateVirusesInGameBoard(data.get("viruses").toString());
    }

    private void updateCitiesInGameBoard(String citiesString) {
        citiesString = getStringWithoutFirstAndLastChar(citiesString);
        String[] citiesArray = getSplittedStringAsArrayWithoutCurlyBrackets(citiesString);
        List<City> cities = createAllCitiesFromDoc(citiesArray);
        gameBoard.setCities(cities);
    }

    private String getStringWithoutFirstAndLastChar(String string) {
        return string.substring(1, string.length() - 1);
    }

    private String[] getSplittedStringAsArrayWithoutCurlyBrackets(String string) {
        String[] separateString = string.split("}, ");

        for (int i = 0; i < separateString.length; i++) {
            separateString[i] = separateString[i].replaceAll("[{}]", "");
        }

        return separateString;
    }

    private List<City> createAllCitiesFromDoc(String[] citiesArray) {
        ArrayList<City> cities = new ArrayList<>();

        for (String city : citiesArray) {
            City newCity = createCityFromDoc(city);
            cities.add(newCity);
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

    private void updateCuredDiseasesInGameBoard(String curedDiseasesString) {
        System.out.println("CURED DISEASES: " + curedDiseasesString);
        curedDiseasesString = getStringWithoutFirstAndLastChar(curedDiseasesString);
        String[] curedDiseasesArray = getSplittedStringAsArrayWithoutCurlyBrackets(curedDiseasesString);
        ArrayList<Cure> curesDiseases = getCuredDiseasesFromString(curedDiseasesArray);
        gameBoard.setCuredDiseases(curesDiseases);
    }

    private ArrayList<Cure> getCuredDiseasesFromString(String[] curedDiseasesStringArray) {
        ArrayList<Cure> curedDiseases = new ArrayList<>();

        for (String curedDisease : curedDiseasesStringArray) {
            curedDiseases.add(getCureFromString(curedDisease));
        }

        return curedDiseases;
    }

    private Cure getCureFromString(String curedDisease) {
        curedDisease = curedDisease + ",";
        String cureState = curedDisease.split("cureState=")[1].split(",")[0];
        String cureType = curedDisease.split("type=")[1].split(",")[0];
        Cure cure = new Cure(VirusType.valueOf(cureType));
        cure.setCureState(CureState.valueOf(cureState));

        return cure;
    }

    private void updateCuresInGameBoard(String curesString) {
        System.out.println("CURES: " + curesString);
        curesString = getStringWithoutFirstAndLastChar(curesString);
        String[] curesArray = getSplittedStringAsArrayWithoutCurlyBrackets(curesString);
        ArrayList<Cure> cures = getCuresFromString(curesArray);
        gameBoard.setCURES(cures);
    }

    private ArrayList<Cure> getCuresFromString(String[] curesArray) {
        ArrayList<Cure> cures = new ArrayList<>();

        for (String cure : curesArray) {
            cures.add(getCureFromString(cure));
        }

        return cures;
    }

    private void updateDrawnEpidemicCardsInGameBoard(String drawnEpidemicCardsString) {
        System.out.println("DRAWN EPIDEMIC CARDS: " + drawnEpidemicCardsString);
        gameBoard.setDrawnEpidemicCards(Integer.parseInt(drawnEpidemicCardsString));
    }

    private void updateInfectionDiscardStackInGameBoard(String infectionDiscardStackString) {
        System.out.println("INFECTION DISCARD STACK: " + infectionDiscardStackString);

        gameBoard.setInfectionDiscardStack(getInfectionCards(infectionDiscardStackString));
    }

    private ArrayList<InfectionCard> getInfectionCards(String string) {
        string = getStringWithoutFirstAndLastChar(string);
        String[] infectionDiscardStackArray = getSplittedStringAsArray(string);
        infectionDiscardStackArray = getArrayWithoutCityEqualsString(infectionDiscardStackArray);
        return getInfectionDiscardStackFromArray(infectionDiscardStackArray);
    }

    private ArrayList<PlayerCard> getPlayerCards(String string) {
        string = getStringWithoutFirstAndLastChar(string);
        String[] playerCardStackArray = getSplittedStringAsArray(string);
        playerCardStackArray = getArrayWithoutCityEqualsString(playerCardStackArray);
        return getInfectionDiscardStackFromArray(infectionDiscardStackArray);
    }

    private ArrayList<InfectionCard> getInfectionDiscardStackFromArray(String[] infectionDiscardStackArray) {
        ArrayList<InfectionCard> infectionCards = new ArrayList<>();

        for (String card : infectionDiscardStackArray) {
            infectionCards.add(getCardFromString(card));
        }

        return infectionCards;
    }

    private ArrayList<PlayerCard> getPlayerCardStackFromArray(String[] playerStackArray) {
        ArrayList<PlayerCard> infectionCards = new ArrayList<>();

        for (String card : playerStackArray) {
            City city = getCityForCardFromString(card);
            infectionCards.add(new CityCard(getCityForCardFromString(card));
        }

        return infectionCards;
    }

    private City getCityForCardFromString(String cardString) {
        VirusType virusType = VirusType.valueOf(cardString.split("virusType=")[1].split(",")[0]);
        String name = getCityNameFromString(cardString);
        String[] nearCitiesArray = cardString.split("nearCities=\\[")[1].split("]")[0].split(",");

        ArrayList<Cube> cubes = getCityCubeAmountFromString(cardString, virusType);
        ArrayList<String> nearCities = new ArrayList<>(Arrays.asList(nearCitiesArray));

        return new City(name, cubes, virusType, nearCities);
    }



    private String[] getSplittedStringAsArray(String string) {
        return string.split("}, ");
    }

    private String[] getArrayWithoutCityEqualsString(String[] array) {
        String[] withoutCityString = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            String without = array[i].replace("{city={", "");
            withoutCityString[i] =  without.substring(0, (i != (array.length - 1)) ? without.length() - 1 : without.length() - 2) + ",";
            System.out.println(withoutCityString[i]);
        }

        return withoutCityString;
    }

    private void updateInfectionRateInGameBoard(String infectionRate) {
        System.out.println("INFECTION RATE:" + infectionRate);
        gameBoard.setInfectionRate(Integer.parseInt(infectionRate));
    }

    private void updateInfectionStackInGameBoard(String infectionStackString) {
        System.out.println("INFECTION STACK: " + infectionStackString);
        gameBoard.setInfectionStack(getInfectionCards(infectionStackString));
    }

    private void updateOutbreakCounterInGameBoard(String outbreakCounterString) {
        System.out.println("OUTBREAK COUNTER: " + outbreakCounterString);
        gameBoard.setOutbreakCounter(Integer.parseInt(outbreakCounterString));
    }

    private void updatePlayerDiscardStackInGameBoard(String playerDiscardStackString) {
        System.out.println("PLAYER DISCARD STACK: " + playerDiscardStackString);
    }

    private void updatePlayerStackInGameBoard(String playerStackString) {
        System.out.println("PLAYER STACK: " + playerStackString);
    }

    private void updateTopSixInfectionStackInGameBoard(String topSixInfectionStackString) {
        System.out.println("TOP SIX INFECTION STACK:" + topSixInfectionStackString);
    }

    private void updateVirusesInGameBoard(String virusesString) {
        System.out.println("VIRUSES:" + virusesString);
    }
}
