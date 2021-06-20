package Model;

import Exceptions.*;
import Observers.GameBoardObservable;
import Observers.GameBoardObserver;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Thimo van Velzen, Daniel Paans, Tom van Gogh
 */

public class Gameboard implements GameBoardObservable {
    private List<GameBoardObserver> observers = new ArrayList<>();
    private final String PATH_TO_CONNECTED_CITIES = "src/main/connectedCities.txt";

    private List<City> cities;
    private List<Cure> cures = Arrays.asList(new Cure(VirusType.BLUE),
            new Cure(VirusType.YELLOW),
            new Cure(VirusType.BLACK),
            new Cure(VirusType.RED));

    private List<Virus> viruses = Arrays.asList(new Virus(VirusType.BLUE),
            new Virus(VirusType.YELLOW),
            new Virus(VirusType.BLACK),
            new Virus(VirusType.RED));

    private ArrayList<InfectionCard> infectionStack;
    private ArrayList<InfectionCard> infectionDiscardStack = new ArrayList<>();
    private ArrayList<PlayerCard> playerStack;
    private ArrayList<PlayerCard> playerDiscardStack = new ArrayList<>();

    private int outbreakCounter = 0;
    private int infectionRate = 1;
    private int drawnEpidemicCards = 0;

    private ArrayList<City> citiesWithResearchStations;
    private ArrayList<City> citiesThatHadOutbreak = new ArrayList<>();
    private final List<Integer> INFECTION_RATES = Arrays.asList(2, 2, 2, 3, 3, 4, 4);

    /**
     * @author : Thimo van Velzen
     */
    public Gameboard() {
        cities = Arrays.asList(new City[48]);
        initializeCities();
        infectionStack = initializeInfectionCardStack();
        citiesWithResearchStations = createCitiesWithResearchStation();
        playerStack = initializePlayerCardStack();
    }

    public ArrayList<City> getCitiesThatHadOutbreak() {
        return citiesThatHadOutbreak;
    }

    public void setCitiesThatHadOutbreak(ArrayList<City> citiesThatHadOutbreak) {
        this.citiesThatHadOutbreak = citiesThatHadOutbreak;
    }

    /**
     * @author : Thimo van Velzen
     */
    public void makeCompleteGameBoard() {
        try {
            initializeGameBoard();
            initializeStartingCubes();
        } catch (WrongInitializationException wie) {
            wie.printStackTrace();
        }
    }

    /**
     * @author : Thimo van Velzen
     */
    private void initializeGameBoard() {
        shuffleAllStacks();
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
        notifyAllObservers();
    }

    public void setCitiesWithResearchStations(ArrayList<City> citiesWithResearchStations) {
        this.citiesWithResearchStations = citiesWithResearchStations;
        notifyAllObservers();
    }

    public void setCures(List<Cure> cures) {
        this.cures = cures;
        notifyAllObservers();
    }

    public void setDrawnEpidemicCards(int drawnEpidemicCards) {
        this.drawnEpidemicCards = drawnEpidemicCards;
        notifyAllObservers();
    }

    public void setInfectionDiscardStack(ArrayList<InfectionCard> infectionDiscardStack) {
        this.infectionDiscardStack = infectionDiscardStack;
        notifyAllObservers();
    }

    public void setInfectionRate(int infectionRate) {
        this.infectionRate = infectionRate;
        notifyAllObservers();
    }

    public void setInfectionStack(ArrayList<InfectionCard> infectionStack) {
        this.infectionStack = infectionStack;
        notifyAllObservers();
    }

    public void setOutbreakCounter(int outbreakCounter) {
        this.outbreakCounter = outbreakCounter;
        notifyAllObservers();
    }

    public void setPlayerDiscardStack(ArrayList<PlayerCard> playerDiscardStack) {
        this.playerDiscardStack = playerDiscardStack;
        notifyAllObservers();
    }

    public void setPlayerStack(ArrayList<PlayerCard> playerStack) {
        this.playerStack = playerStack;
        notifyAllObservers();
    }

    public void setViruses(List<Virus> viruses) {
        this.viruses = viruses;
        notifyAllObservers();
    }

    /**
     * @author : Thimo van Velzen
     */
    private void initializeCities() {
        String[] cityNames = getCityNames();
        assignVirusToCities(cityNames);

        try {
            assignNeighboursToCities();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * @author : Thimo van Velzen, Romano Biertantie, Daniel Paans
     */
    private String[] getCityNames() {
        return new String[] {"San Francisco", "Chicago", "Atlanta",
                "Montréal", "Washington", "New York", "Madrid", "London",
                "Paris", "Essen", "Milan", "St. Petersburg",  // Blue

                "Los Angeles", "Mexico City", "Miami", "Bogota", "Lima",
                "Santiago", "Buenos Aires", "São Paulo", "Lagos", "Kinshasa",
                "Khartoum", "Johannesburg",  // Yellow

                "Algiers", "Istanbul", "Moscow", "Cairo", "Baghdad", "Riyadh",
                "Karachi", "Tehran", "Delhi", "Mumbai", "Kolkata",
                "Chennai",  // Black

                "Bangkok", "Jakarta", "Ho Chi Minh City", "H.K.",
                "Shanghai", "Beijing", "Seoul", "Tokyo", "Osaka", "Taipei",
                "Manila", "Sydney"};  // Red
    }

    /**
     * @author : Daniel Paans
     * @param cityNames
     */
    private void assignVirusToCities(String[] cityNames) {
        int virusIndex = 0;

        for (int i = 0; i < cityNames.length; i++) {
            if (i % (cityNames.length/ viruses.size()) == 0) {
                virusIndex++;
            }

            VirusType virusType = viruses.get(virusIndex-1).getVirusType();
            cities.set(i, new City(cityNames[i], virusType));
        }
    }

    /**
     * @author : Thimo van Velzen, Daniel Paans
     */
    private void assignNeighboursToCities() throws IOException {
        BufferedReader bufferedReader = makeBufferedReader();
        String line = bufferedReader.readLine();

        while (line != null) {
            try {
                assignNeighboursToCity(line);
            } catch (CityNotFoundException cnfe) {
                cnfe.printStackTrace();
            }

            line = bufferedReader.readLine();
        }

        bufferedReader.close();
    }

    /**
     * @author : Thimo van Velzen
     */
    private void assignNeighboursToCity(String line) throws CityNotFoundException {
        City CityOne = getCity(line.split(";")[0]);
        City CityTwo = getCity(line.split(";")[1]);

        CityOne.addNeighbour(CityTwo.getName());
        CityTwo.addNeighbour(CityOne.getName());
    }

    /**
     * @author : Daniel Paans
     */
    private BufferedReader makeBufferedReader() throws FileNotFoundException {
        File textFile = new File(PATH_TO_CONNECTED_CITIES);
        FileReader fileReader = new FileReader(textFile);

        return new BufferedReader(fileReader);
    }

    /**
     * @author : Thimo van Velzen
     */
    private ArrayList<InfectionCard> initializeInfectionCardStack() {
        ArrayList<InfectionCard> infectionCardStack = new ArrayList<>();

        for (City city : cities) {
            infectionCardStack.add(new InfectionCard(city));
        }

        return infectionCardStack;
    }

    /**
     * @author : Daniel Paans
     * @return
     */
    private ArrayList<PlayerCard> initializePlayerCardStack() {
        ArrayList<PlayerCard> playerCardStack = new ArrayList<>();

        for (City city : cities) {
            playerCardStack.add(new CityCard(city));
        }

        playerCardStack.addAll(Arrays.asList(initializeEventCards()));
        playerCardStack.addAll(Arrays.asList(initializeEpidemicCards(6)));

        return playerCardStack;
    }

    /**
     * @author : Daniel Paans
     * @return
     */
    private EventCard[] initializeEventCards() {
        EventCard[] eventCards = new EventCard[5];
        eventCards[0] = new OneQuietNight("One quiet night", "Skip the next Infect Cities step.");
        eventCards[1] = new GovernmentGrant("Government grant", "Add 1 research station to any city.");
        eventCards[2] = new Airlift("Airlift", "Move any 1 pawn to any city.");
        eventCards[3] = new Forecast("Forecast", "Draw, look at and rearrange the top 6 cards of the Infection Deck, put them back on top.");
        eventCards[4] = new ResilientPopulation("Resilient population", "Remove any 1 card in the Infection Discard Pile from the game.");

        return eventCards;
    }

    /**
     * @author : Daniel Paans
     * @param epidemicCardAmount
     * @return
     */
    private EpidemicCard[] initializeEpidemicCards(int epidemicCardAmount) {
        EpidemicCard[] epidemicCards = new EpidemicCard[epidemicCardAmount];

        for (int i = 0; i < epidemicCardAmount; i++) {
            epidemicCards[i] = new EpidemicCard();
        }

        return epidemicCards;
    }

    /**
     * @author : Thimo van Velzen
     */
    public void flipCurePawn(Cure cure) {
        if (cure.getCureState().equals(CureState.ACTIVE)) {
            cure.setCureState(CureState.CURED);
        } else if (cure.getCureState().equals(CureState.CURED)) {
            cure.setCureState(CureState.ERADICATED);
        }

        notifyAllObservers();
    }

    /**
     * @author : Daniel Paans
     */
    public Cure getCureWithVirusType(VirusType virusType) throws CureNotFoundException {
        for(Cure cure : cures) {
            if(cure.getVirusType() == virusType) {
                return cure;
            }
        }

        throw new CureNotFoundException("Cure is not found" + " : " + virusType);
    }

    /**
     * @author : Thimo van Velzen
     */
    public PlayerCard drawPlayerCard() throws GameLostException {
        try {
            PlayerCard playerCard = playerStack.get(0);
            playerStack.remove(0);
            notifyAllObservers();
            return playerCard;
        } catch (IndexOutOfBoundsException e) {
            throw new GameLostException("U lost: There are no playercards left!");
        }
    }

    /**
     * @author : Thimo van Velzen
     */
    public void discardPlayerCard(PlayerCard card) {
        playerDiscardStack.add(0, card);
        notifyAllObservers();
    }

    /**
     * @author : Daniel Paans
     */
    public InfectionCard drawInfectionCard() {
        InfectionCard infectionCard = infectionStack.get(0);
        infectionStack.remove(0);
        infectionDiscardStack.add(infectionCard);
        notifyAllObservers();

        return infectionCard;
    }

    /**
     * @author : Daniel Paans
     */
    public void handleInfectionCardsInEpidemic() {
        for (int i = 0; i < 100; i++) {
            InfectionCard card = infectionDiscardStack.get(getRandomIndex(infectionDiscardStack));
            infectionDiscardStack.remove(card);
            infectionDiscardStack.add(card);
        }

        Collections.reverse(infectionStack);
        infectionStack.addAll(infectionDiscardStack);
        Collections.reverse(infectionStack);
        notifyAllObservers();
    }

    /**
     * @author : Thimo van Velzen
     */
    public void shuffleAllStacks() {
        for (int i = 0; i < 100; i++) {
            shufflePlayerCardStack();
            shuffleInfectionStack();
        }
        notifyAllObservers();
    }

    /**
     * @author : Thimo van Velzen
     */
    private void shufflePlayerCardStack() {
        PlayerCard playerCard = playerStack.get(getRandomIndex(playerStack));
        playerStack.remove(playerCard);
        playerStack.add(playerCard);
    }

    /**
     * @author : Thimo van Velzen
     */
    private void shuffleInfectionStack() {
        InfectionCard infectionCard = infectionStack.get(getRandomIndex(infectionStack));
        infectionStack.remove(infectionCard);
        infectionStack.add(infectionCard);
    }

    /**
     * @author : Thimo van Velzen
     */
    private int getRandomIndex(ArrayList arrayList) {
        return (int) (Math.random() * arrayList.size());
    }

    /**
     * @author : Thimo van Velzen
     */
    public void increaseOutbreakCounter() throws GameLostException {
        outbreakCounter++;

        if (outbreakCounter >= 8) {
            throw new GameLostException("U lost: The outbreak counter is 8!");
        }

        notifyAllObservers();
    }

    /**
     * @author : Daniel Paans
     * @param infectionRate
     */
    public void increaseInfectionRate(int infectionRate) {
        this.infectionRate = infectionRate;
        notifyAllObservers();
    }

    /**
     * @author : Thimo van Velzen, Daniel Paans
     */
    public void addCubes(City currentCity, int cubeAmount) throws GameLostException {
        VirusType virusType = currentCity.getVIRUS_TYPE();

        for (int i = 0; i < cubeAmount; i++) {
            currentCity.addCube(virusType);
        }

        tryToDecreaseCubeAmount(virusType, cubeAmount);

        notifyAllObservers();
    }

    /**
     * @author : Thimo van Velzen
     */
    private void tryToIncreaseCubeAmount(VirusType virusType, int cubeAmount) {
        try {
            getVirusByType(virusType).increaseCubeAmount(cubeAmount);
        } catch (VirusNotFoundException vnfe) {
            vnfe.printStackTrace();
        }
    }

    /**
     * @author : Thimo van Velzen
     */
    public void removeCubes(City currentCity, VirusType virusType, int cubeAmount) {
        currentCity.removeCube();
        tryToIncreaseCubeAmount(virusType, cubeAmount);
        notifyAllObservers();
    }

    /**
     * @author : Thimo van Velzen
     */
    private void tryToDecreaseCubeAmount(VirusType virusType, int cubeAmount) throws GameLostException {
        try {
            getVirusByType(virusType).decreaseCubeAmount(cubeAmount);
        } catch (VirusNotFoundException vnfe) {
            vnfe.printStackTrace();
        }
    }

    /**
     * @author : Daniel Paans
     * @param type
     * @return
     * @throws VirusNotFoundException
     */
    public Virus getVirusByType(VirusType type) throws VirusNotFoundException {
        for (Virus virus : viruses) {
            if (virus.getVirusType().equals(type)) {
                return virus;
            }
        }

        throw new VirusNotFoundException("Virus not Found");
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public List<City> getCities() {
        return cities;
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public List<Cure> getCures() {
        return cures;
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public List<Virus> getViruses() {
        return viruses;
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public ArrayList<InfectionCard> getInfectionStack() {
        return infectionStack;
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public ArrayList<InfectionCard> getInfectionDiscardStack() {
        return infectionDiscardStack;
    }

    /**
     * @author : Daniel Paans
     */
    public City getCity(String cityName) throws CityNotFoundException {
        for (City city : cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }

        throw new CityNotFoundException("City not found" + " : " + cityName);
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public int getOutbreakCounter() {
        return outbreakCounter;
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public int getInfectionRate() {
        return INFECTION_RATES.get(infectionRate);
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public ArrayList<PlayerCard> getPlayerStack() {
        return playerStack;
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public ArrayList<PlayerCard> getPlayerDiscardStack() {
        return playerDiscardStack;
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public ArrayList<City> getCitiesWithResearchStations() {
        return citiesWithResearchStations;
    }

    /**
     * @author : Thimo van Velzen
     */
    public boolean cityHasResearchStation(City city) {
        return citiesWithResearchStations.contains(city);
    }

    /**
     * @author : Thimo van Velzen
     */
    public void addResearchStationToCity(City city) {
        for (City c : cities) {
            if (c.equals(city)) {
                citiesWithResearchStations.add(c);
                break;
            }
        }

        notifyAllObservers();
    }

    /**
     * @author : Daniel Paans
     */
    public void handleEpidemicCard() {
        addDrawnEpidemicCard();
        increaseInfectionRate(INFECTION_RATES.get(drawnEpidemicCards));
        handleInfectionCardsInEpidemic();
    }

    /**
     * @author : Daniel Paans
     * @param cubeAmount
     * @throws GameLostException
     */
    public void handleInfectionCardDraw(int cubeAmount) throws GameLostException {
        for (int i = 0; i < infectionRate; i++) {
            handleInfection(drawInfectionCard(), cubeAmount);
        }
    }

    /**
     * @author : Thimo van Velzen
     */
    public boolean cureIsFound(VirusType virus) {
        List<Cure> curedDiseases = getCuredDiseases();

        for (Cure cure : curedDiseases) {
            if (cure.getVirusType().equals(virus)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @author : Thimo van Velzen, Daniel Paans
     */
    public void initializeStartingCubes() throws WrongInitializationException {
        try {
            for (int drawAmount = 3; drawAmount > 0; drawAmount--) {
                for (int i = 0; i < 3; i++) {
                    System.out.println(drawAmount);
                    assignStartingCubeToRandomCity(drawAmount);
                }
            }
        } catch (GameLostException gme) {
            gme.printStackTrace();
            throw new WrongInitializationException("CubeAmount < 0");
        }
    }

    private void assignStartingCubeToRandomCity(int cubeAmount) throws GameLostException {
        City randomCity = drawInfectionCard().getCity();
        addCubes(randomCity, cubeAmount);
    }

    /**
     * @author : Thimo van Velzen, Daniel paans
     */
    public void handleInfection(InfectionCard infectionCard, int cubeAmount) throws GameLostException {
        City infectedCity = infectionCard.getCity();

        if (infectedCity.getCubes().size() >= 3) {  // Hier moet de quarantine specialist nog toegevoegd worden
            handleOutbreak(infectedCity);
        } else {
            addCubes(infectedCity, cubeAmount);
        }
    }

    /**
     * @author : Thimo van Velzen, Daniel paans
     */
    public void handleOutbreak(City infectedCity) throws GameLostException {
        increaseOutbreakCounter();
        addCityThatHadOutbreak(infectedCity);

        ArrayList<City> nearCities = getNearCities(infectedCity);

        for (City city : nearCities) {
            if (infectedCity.getCubes().size() >= 3 && !cityHadOutbreak(city)) {  // Hier moet de quarantine specialist nog toegevoegd worden
                handleOutbreak(city);
            } else {
                addCubes(city, 1);
            }
        }
    }

    /**
     * @author : Daniel Paans
     * @param city
     * @return
     */
    private ArrayList<City> getNearCities(City city) {
        ArrayList<City> nearCities = new ArrayList<>();
        ArrayList<String> nearCitiesNames = city.getNearCities();

        for (String name : nearCitiesNames) {
            try {
                nearCities.add(getCity(name));
            } catch (CityNotFoundException cnfe) {
                cnfe.printStackTrace();
            }
        }

        return nearCities;
    }

    public void addCityThatHadOutbreak(City city) {
        citiesThatHadOutbreak.add(city);
    }

    public boolean cityHadOutbreak(City city) {
        return citiesThatHadOutbreak.contains(city);
    }

    /**
     * @author : Thimo van Velzen
     */
    public List<Cure> getCuredDiseases() {
        return cures.stream().filter(cure -> cure.getCureState().equals(CureState.CURED))
                .collect(Collectors.toList());
    }

    public void setCuredDiseases(ArrayList<Cure> curedDiseases) {
        for (Cure curedDisease : curedDiseases) {
            for (Cure cure : cures) {
                if (cure.getVirusType().equals(curedDisease.getVirusType())) {
                    cure.setCureState(CureState.CURED);
                }
            }
        }
    }

    /**
     * @author : Tom van Gogh
     */
    public int getDrawnEpidemicCards() {
        return drawnEpidemicCards;
    }

    /**
     * @author : Tom van Gogh
     */
    public void addDrawnEpidemicCard() {
        drawnEpidemicCards++;
        notifyAllObservers();
    }

    public boolean gameboardHasResearchStationsLeft() {
        return citiesWithResearchStations.size() < 6;
    }

    /**
     * @author : Thimo van Velzen
     */
    public ArrayList<City> createCitiesWithResearchStation() {
        try {
            return new ArrayList<>(Collections.singletonList(getCity("Atlanta")));
        } catch (CityNotFoundException cnfe) {
            cnfe.printStackTrace();
            return createNewAtlantaCity();
        }
    }

    /**
     * @author : Thimo van Velzen
     */
    public ArrayList<City> createNewAtlantaCity() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Atlanta", VirusType.BLUE));
        return cities;
    }

    public void addInfectionStack(ArrayList<InfectionCard> infectionCards) {
        infectionStack.addAll(infectionCards);
        notifyAllObservers();
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public void register(GameBoardObserver gameBoardObserver) {
        unregisterAllObservers();
        observers.add(gameBoardObserver);
        notifyAllObservers();
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public void unregisterAllObservers() {
        observers = new ArrayList<>();
    }

    /**
     * @author : Tom van Gogh
     */
    @Override
    public void notifyAllObservers() {
        for (GameBoardObserver observer : observers) {
            observer.update(this);
        }
    }
}
