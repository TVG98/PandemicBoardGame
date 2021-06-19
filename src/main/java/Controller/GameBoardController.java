package Controller;

import Controller.Behavior.*;
import Exceptions.CardNotFoundException;
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
        databaseController.updateGameBoardInDatabase(gameBoard);
//        databaseController.updateCitiesInDatabase(gameBoard.getCities());
//        databaseController.updateCitiesWithResearchStationsInDatabase(gameBoard.getCitiesWithResearchStations());
//        databaseController.updateCuredDiseases(gameBoard.getCuredDiseases());
//        databaseController.updateCures(gameBoard.getCures());
//        databaseController.updateDrawnEpidemicCards(gameBoard.getDrawnEpidemicCards());
//        databaseController.updateInfectionDiscardStack(gameBoard.getInfectionDiscardStack());
//        databaseController.updateInfectionRate(gameBoard.getInfectionRate());
//        databaseController.updateInfectionStack(gameBoard.getInfectionStack());
//        databaseController.updateOutbreakCounter(gameBoard.getOutbreakCounter());
//        databaseController.updatePlayerDiscardStack(gameBoard.getPlayerDiscardStack());
//        databaseController.updatePlayerStack(gameBoard.getPlayerStack());
//        databaseController.updateTopSixInfectionStack(gameBoard.getTopSixInfectionStack());
//        databaseController.updateViruses(gameBoard.getViruses());
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
//        databaseController.updateCitiesInDatabase(gameBoard.getCities());
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

    public List<City> getCities() {
        return gameBoard.getCities();
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

    public void handleShareKnowledge(Player currentPlayer, Player chosenPlayer, boolean giveCard) {
        shareKnowledgeBehavior.shareKnowledge(currentPlayer, chosenPlayer, giveCard);
    }

    public void setShareKnowledgeBehavior(ShareKnowledgeBehavior shareKnowledgeBehavior) {
        this.shareKnowledgeBehavior = shareKnowledgeBehavior;
    }

    public void addResearchStationToCity(City city) {
        gameBoard.addResearchStationToCity(city);
//        databaseController.updateCitiesWithResearchStationsInDatabase(gameBoard.getCitiesWithResearchStations());
    }

    public void setTreatDiseaseBehavior(TreatDiseaseBehavior treatDiseaseBehavior) {
        this.treatDiseaseBehavior = treatDiseaseBehavior;
    }

    public void handleTreatDisease(Player currentPlayer, City currentCity) {
        treatDiseaseBehavior.treatDisease(currentPlayer, currentCity);
//        databaseController.updateCitiesInDatabase(gameBoard.getCities());
    }

    public boolean cityHasResearchStation(City city) {
        return gameBoard.cityHasResearchStation(city);
    }

    public boolean canShareAnyCard(Player givingPlayer) {
        return playerController.hasRole(givingPlayer, Role.RESEARCHER);
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

    public void update(DatabaseData data) {
        if (data.getGameboard()!= null) {
            updateGameBoardLocal(data);
        }
    }

    private void updateGameBoardLocal(DatabaseData data) {
        gameBoard = data.getGameboard();

//        Map<String, Object> data = snapshot.getData();

//        System.out.println("updating gameboard....");
//        updateCitiesInGameBoard(data.get("cities").toString());
//        updateCitiesInGameBoard(data.get("citiesWithResearchStations").toString());
//        updateCuredDiseasesInGameBoard(data.get("curedDiseases").toString());
//        updateCuresInGameBoard(data.get("cures").toString());
//        updateDrawnEpidemicCardsInGameBoard(data.get("drawnEpidemicCards").toString());
//        updateInfectionDiscardStackInGameBoard(data.get("infectionDiscardStack").toString());
//        updateInfectionRateInGameBoard(data.get("infectionRate").toString());
//        updateInfectionStackInGameBoard(data.get("infectionStack").toString());
//        updateOutbreakCounterInGameBoard(data.get("outbreakCounter").toString());
//        updatePlayerDiscardStackInGameBoard(data.get("playerDiscardStack").toString());
//        updatePlayerStackInGameBoard(data.get("playerStack").toString());
//        updateVirusesInGameBoard(data.get("viruses").toString());
//        System.out.println("============================gameBoard is fully updated==========================");
//    }

//    private void updateCitiesInGameBoard(String citiesString) {
//        gameBoard.setCities(getCitiesFromString(citiesString));
//    }
//
//    private List<City> getCitiesFromString(String citiesString) {
//        citiesString = getStringWithoutFirstAndLastChar(citiesString);
//        String[] citiesArray = getSplitStringAsArrayWithoutCurlyBrackets(citiesString);
//        return createAllCitiesFromDoc(citiesArray);
//    }
//
//    private String getStringWithoutFirstAndLastChar(String string) {
//        int lastIndex = string.length() -1;
//        return (string.length() >= 2) ? string.substring(1, lastIndex) : "";
//    }
//
//    private String[] getSplitStringAsArrayWithoutCurlyBrackets(String string) {
//        String[] split = string.split("}, ");
//
//        for (int i = 0; i < split.length; i++) {
//            split[i] = getStringWithoutCurlyBrackets(split[i]);
//        }
//
//        return split;
//    }
//
//    private String getStringWithoutCurlyBrackets(String string) {
//        return string.replaceAll("[{}]", "");
//    }
//
//    private List<City> createAllCitiesFromDoc(String[] citiesArray) {
//        ArrayList<City> cities = new ArrayList<>();
//
//        for (String city : citiesArray) {
//            cities.add(createCityFromDoc(city));
//        }
//
//        return cities;
//    }
//
//    private City createCityFromDoc(String cityString) {
//        cityString = cityString + ",";
//        String name = getCityNameFromString(cityString);
//        VirusType virusType = getVirusTypeFromString(cityString);
//        ArrayList<Cube> cubeAmount = createCubeList(cityString, virusType);
//        ArrayList<String> nearCities = getCityNearCitiesFromString(cityString);
//
//        return new City(name, cubeAmount, virusType, nearCities);
//    }
//
//    private String getCityNameFromString(String cityString) {
//        return cityString.split("name=")[1].split(",")[0];
//    }
//
//    private VirusType getVirusTypeFromString(String string) {
//        String virusTypeString = string.split("virusType=")[1].split(",")[0];
//        return VirusType.valueOf(virusTypeString);
//    }
//
//    private ArrayList<Cube> createCubeList(String string, VirusType virusType) {
//        ArrayList<Cube> cubes = new ArrayList<>();
//        int cubeAmount = getCubeAmountFromString(string);
//
//        for (int i = 0; i < cubeAmount; i++) {
//            cubes.add(new Cube(virusType));
//        }
//
//        return cubes;
//    }
//
//    private int getCubeAmountFromString(String string) {
//        String cubeAmountString = string.split("cubeAmount=")[1].split(",")[0];
//        return Integer.parseInt(cubeAmountString);
//    }
//
//    private ArrayList<String> getCityNearCitiesFromString(String cityString) {
//        String[] nearCities = cityString.split("\\[")[1].split("]")[0].split(", ");
//        return new ArrayList<>(Arrays.asList(nearCities));
//    }
//
//    private void updateCuredDiseasesInGameBoard(String curedDiseasesString) {
//        gameBoard.setCuredDiseases(createCuredDiseasesListFromString(curedDiseasesString));
//    }
//
//    private ArrayList<Cure> createCuredDiseasesListFromString(String string) {
//        string = getStringWithoutFirstAndLastChar(string);
//
//        return (string.length() == 0) ? new ArrayList<>() : getCuredDiseasesFromString(string);
//    }
//
//    private ArrayList<Cure> getCuredDiseasesFromString(String string) {
//        String[] curedDiseasesArray = getSplitStringAsArrayWithoutCurlyBrackets(string);
//        ArrayList<Cure> curedDiseases = new ArrayList<>();
//
//        for (String curedDisease : curedDiseasesArray) {
//            curedDiseases.add(getCureFromString(curedDisease));
//        }
//
//        return curedDiseases;
//    }
//
//    private Cure getCureFromString(String curedDisease) {
//        curedDisease = curedDisease + ",";
//        String cureState = getCureStateFromString(curedDisease);
//        String cureType = getCureTypeFromString(curedDisease);
//
//        return createCureObject(cureType, cureState);
//    }
//
//    private String getCureStateFromString(String string) {
//        return string.split("cureState=")[1].split(",")[0];
//    }
//
//    private String getCureTypeFromString(String string) {
//        return string.split("type=")[1].split(",")[0];
//    }
//
//    private Cure createCureObject(String cureType, String cureState) {
//        Cure cure = new Cure(VirusType.valueOf(cureType));
//        cure.setCureState(CureState.valueOf(cureState));
//
//        return cure;
//    }
//
//    private void updateCuresInGameBoard(String curesString) {
//        gameBoard.setCURES(getCuresFromString(curesString));
//    }
//
//    private ArrayList<Cure> getCuresFromString(String curesString) {
//        curesString = getStringWithoutFirstAndLastChar(curesString);
//        String[] curesArray = getSplitStringAsArrayWithoutCurlyBrackets(curesString);
//        return createCureList(curesArray);
//    }
//
//    private ArrayList<Cure> createCureList(String[] curesArray) {
//        ArrayList<Cure> cures = new ArrayList<>();
//
//        for (String cure : curesArray) {
//            cures.add(getCureFromString(cure));
//        }
//
//        return cures;
//    }
//
//    private void updateDrawnEpidemicCardsInGameBoard(String drawnEpidemicCardsString) {
//        gameBoard.setDrawnEpidemicCards(toInt(drawnEpidemicCardsString));
//    }
//
//    private int toInt(String string) {
//        return Integer.parseInt(string);
//    }
//
//    private void updateInfectionDiscardStackInGameBoard(String infectionDiscardStackString) {
//        gameBoard.setInfectionDiscardStack(getInfectionCards(infectionDiscardStackString));
//    }
//
//    private ArrayList<InfectionCard> getInfectionCards(String string) {
//        string = getStringWithoutFirstAndLastChar(string);
//
//        return (string.length() == 0) ? new ArrayList<>() : createInfectionCards(string);
//    }
//
//    private ArrayList<InfectionCard> createInfectionCards(String string) {
//        String[] infectionDiscardStackArray = getSplitStringAsArray(string);
//        infectionDiscardStackArray = getArrayWithoutCityEqualsString(infectionDiscardStackArray);
//        return getInfectionDiscardStackFromArray(infectionDiscardStackArray);
//    }
//
//    private ArrayList<PlayerCard> getPlayerCards(String string) {
//        string = getStringWithoutFirstAndLastChar(string);
//
//        return (string.length() == 0) ? new ArrayList<>() : createPlayerCards(string);
//    }
//
//    private ArrayList<PlayerCard> createPlayerCards(String string) {
//        ArrayList<PlayerCard> playerCards = new ArrayList<>();
//        String[] cardNames =  string.split(", ");
//
//        try {
//            for (String cardName : cardNames) {
//                playerCards.add(gameBoard.getPlayerCard(cardName));
//            }
//        } catch (CardNotFoundException cnfe) {
//            cnfe.printStackTrace();
//        }
//
//        return playerCards;
//    }
//
//    private ArrayList<InfectionCard> getInfectionDiscardStackFromArray(String[] infectionDiscardStackArray) {
//        ArrayList<InfectionCard> infectionCards = new ArrayList<>();
//
//        for (String card : infectionDiscardStackArray) {
//            infectionCards.add(new InfectionCard(getCityForCardFromString(card)));
//        }
//
//        return infectionCards;
//    }
//
//    private City getCityForCardFromString(String cardString) {
//        VirusType virusType = getVirusTypeFromString(cardString);
//        String name = getCityNameFromString(cardString);
//        String[] nearCitiesArray = cardString.split("nearCities=\\[")[1].split("]")[0].split(",");
//
//        ArrayList<Cube> cubes = createCubeList(cardString, virusType);
//        ArrayList<String> nearCities = new ArrayList<>(Arrays.asList(nearCitiesArray));
//
//        return new City(name, cubes, virusType, nearCities);
//    }
//
//    private String[] getSplitStringAsArray(String string) {
//        return string.split("}, ");
//    }
//
//    private String[] getArrayWithoutCityEqualsString(String[] array) {
//        String[] withoutCityString = new String[array.length];
//
//        for (int i = 0; i < array.length; i++) {
//            String without = array[i].replace("{city={", "");
//            withoutCityString[i] =  without.substring(0, (i != (array.length - 1)) ? without.length() - 1 : without.length() - 2) + ",";
//        }
//
//        return withoutCityString;
//    }
//
//    private void updateInfectionRateInGameBoard(String infectionRate) {
//        gameBoard.setInfectionRate(Integer.parseInt(infectionRate));
//    }
//
//    private void updateInfectionStackInGameBoard(String infectionStackString) {
//        gameBoard.setInfectionStack(getInfectionCards(infectionStackString));
//    }
//
//    private void updateOutbreakCounterInGameBoard(String outbreakCounterString) {
//        gameBoard.setOutbreakCounter(Integer.parseInt(outbreakCounterString));
//    }
//
//    private void updatePlayerDiscardStackInGameBoard(String playerDiscardStackString) {
//        gameBoard.setPlayerDiscardStack(getPlayerCards(playerDiscardStackString));
//    }
//
//    private void updatePlayerStackInGameBoard(String playerStackString) {
//        gameBoard.setPlayerStack(getPlayerCards(playerStackString));
//    }
//
//    private void updateVirusesInGameBoard(String virusesString) {
//        gameBoard.setVIRUSES(getVirusesFromString(virusesString));
//    }
//
//    private List<Virus> getVirusesFromString(String virusesString) {
//        String string = getStringWithoutFirstAndLastChar(virusesString);
//        String[] viruses = getSplitStringAsArrayWithoutCurlyBrackets(string);
//        return getVirusListFromArray(viruses);
//    }
//
//    private List<Virus> getVirusListFromArray(String[] viruses) {
//        List<Virus> virusList = new ArrayList<>();
//
//        for (String virus : viruses) {
//            virusList.add(getVirusFromString(virus + ","));
//        }
//
//        return virusList;
//    }
//
//    private Virus getVirusFromString(String string) {
//        int cubeAmount = getCubeAmountFromString(string);
//        String virusTypeString = getCureTypeFromString(string);
//        VirusType virusType = VirusType.valueOf(virusTypeString);
//
//        Virus virus = new Virus(virusType);
//        virus.setCubeAmount(cubeAmount);
//
//        return virus;
    }
}
