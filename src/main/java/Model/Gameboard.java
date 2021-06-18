package Model;

import Exceptions.CardNotFoundException;
import Exceptions.CityNotFoundException;
import Exceptions.CureNotFoundException;
import Exceptions.VirusNotFoundException;
import Observers.GameBoardObservable;
import Observers.GameBoardObserver;

import java.io.*;
import java.util.*;

public class Gameboard implements GameBoardObservable {
    private List<GameBoardObserver> observers = new ArrayList<>();
    private final String PATH_TO_CONNECTED_CITIES = "src/main/connectedCities.txt";

    private List<City> cities; // can change
    private List<Cure> CURES = Arrays.asList(new Cure(VirusType.BLUE),
            new Cure(VirusType.YELLOW),
            new Cure(VirusType.BLACK),
            new Cure(VirusType.RED));

    private List<Virus> VIRUSES = Arrays.asList(new Virus(VirusType.BLUE),
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
    private final ArrayList<City> citiesThatHadOutbreak = new ArrayList<>();
    private final List<Integer> INFECTION_RATES = Arrays.asList(2, 2, 2, 3, 3, 4, 4);

    public Gameboard() {
        cities = Arrays.asList(new City[48]);
        initializeCities();
        infectionStack = initializeInfectionCardStack();
        citiesWithResearchStations = createCitiesWithResearchStation();
        playerStack = initializePlayerCardStack();
    }

    public void makeCompleteGameBoard() {
        initializeGameBoard();
        initializeStartingCubes();
    }

    private void initializeGameBoard() {
        shuffleAllStacks();
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
        notifyAllObservers();
    }

    public void setCitiesWithResearchStations(ArrayList<City> citiesWithResearchStations) {
        this.citiesWithResearchStations = citiesWithResearchStations;
    }

    public void setCURES(List<Cure> CURES) {
        this.CURES = CURES;
    }

    public void setDrawnEpidemicCards(int drawnEpidemicCards) {
        this.drawnEpidemicCards = drawnEpidemicCards;
    }

    public void setInfectionDiscardStack(ArrayList<InfectionCard> infectionDiscardStack) {
        this.infectionDiscardStack = infectionDiscardStack;
    }

    public void setInfectionRate(int infectionRate) {
        this.infectionRate = infectionRate;
    }

    public void setInfectionStack(ArrayList<InfectionCard> infectionStack) {
        this.infectionStack = infectionStack;
    }

    public void setOutbreakCounter(int outbreakCounter) {
        this.outbreakCounter = outbreakCounter;
    }

    public void setPlayerDiscardStack(ArrayList<PlayerCard> playerDiscardStack) {
        this.playerDiscardStack = playerDiscardStack;
    }

    public void setPlayerStack(ArrayList<PlayerCard> playerStack) {
        this.playerStack = playerStack;
    }

    public void setVIRUSES(List<Virus> viruses) {
        this.VIRUSES = viruses;
    }

    private void initializeCities() {
        String[] cityNames = getCityNames();
        assignVirusToCities(cityNames);

        try {
            assignNeighboursToCities();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

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

    private void assignVirusToCities(String[] cityNames) {
        int virusIndex = 0;

        for (int i = 0; i < cityNames.length; i++) {
            if (i % (cityNames.length/ VIRUSES.size()) == 0) {
                virusIndex++;
            }

            VirusType virusType = VIRUSES.get(virusIndex-1).getType();
            cities.set(i, new City(cityNames[i], virusType));
        }
    }

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

    private void assignNeighboursToCity(String line) throws CityNotFoundException {
        City CityOne = getCity(line.split(";")[0]);
        City CityTwo = getCity(line.split(";")[1]);

        CityOne.addNeighbour(CityTwo.getName());
        CityTwo.addNeighbour(CityOne.getName());
    }

    private BufferedReader makeBufferedReader() throws FileNotFoundException {
        File textFile = new File(PATH_TO_CONNECTED_CITIES);
        FileReader fileReader = new FileReader(textFile);

        return new BufferedReader(fileReader);
    }

    private ArrayList<InfectionCard> initializeInfectionCardStack() {
        ArrayList<InfectionCard> infectionCardStack = new ArrayList<>();

        for (City city : cities) {
            infectionCardStack.add(new InfectionCard(city));
        }

        return infectionCardStack;
    }

    private ArrayList<PlayerCard> initializePlayerCardStack() {
        ArrayList<PlayerCard> playerCardStack = new ArrayList<>();

        for (City city : cities) {
            playerCardStack.add(new CityCard(city));
        }

        playerCardStack.addAll(Arrays.asList(initializeEventCards()));
        playerCardStack.addAll(Arrays.asList(initializeEpidemicCards(6)));

        return playerCardStack;
    }

    public PlayerCard getPlayerCard(String cardName) throws CardNotFoundException {
        for (PlayerCard card : playerStack) {
            System.out.println(card.getName() + " : " + cardName);
            if (card.getName().equals(cardName)) {
                return card;
            }
        }

        for (PlayerCard card : playerDiscardStack) {
            System.out.println(card.getName() + " : " + cardName);
            if (card.getName().equals(cardName)) {
                return card;
            }
        }

        throw new CardNotFoundException("card not found: " + cardName);
    }

    private EventCard[] initializeEventCards() {
        EventCard[] eventCards = new EventCard[5];
        eventCards[0] = new OneQuietNight("One quiet night", "Skip the next Infect Cities step.");
        eventCards[1] = new GovernmentGrant("Government grant", "Add 1 research station to any city.");
        eventCards[2] = new Airlift("Airlift", "Move any 1 pawn to any city.");
        eventCards[3] = new Forecast("Forecast", "Draw, look at and rearrange the top 6 cards of the Infection Deck, put them back on top.");
        eventCards[4] = new ResilientPopulation("Resilient population", "Remove any 1 card in the Infection Discard Pile from the game.");

        return eventCards;
    }

    private EpidemicCard[] initializeEpidemicCards(int epidemicCardAmount) {
        EpidemicCard[] epidemicCards = new EpidemicCard[epidemicCardAmount];

        for (int i = 0; i < epidemicCardAmount; i++) {
            epidemicCards[i] = new EpidemicCard();
        }

        return epidemicCards;
    }

    public void flipCurePawn(Cure cure) {
        if (cure.getCureState().equals(CureState.ACTIVE)) {
            cure.setCureState(CureState.CURED);
        } else if (cure.getCureState().equals(CureState.CURED)) {
            cure.setCureState(CureState.ERADICATED);
        }
        notifyAllObservers();
    }

    public Cure getCureWithVirusType(VirusType virusType) throws CureNotFoundException {
        for(Cure cure : CURES) {
            if(cure.getType() == virusType) {
                return cure;
            }
        }
        throw new CureNotFoundException("Cure is not found" + " : " + virusType);
    }

    public PlayerCard drawPlayerCard() {
        PlayerCard playerCard = playerStack.get(0);
        playerStack.remove(0);
        return playerCard;
    }

    public void discardPlayerCard(PlayerCard card) {
        playerDiscardStack.add(0, playerStack.get(0));
        notifyAllObservers();
    }

    public InfectionCard drawInfectionCard() {
        InfectionCard infectionCard = infectionStack.get(0);
        infectionStack.remove(0);
        infectionDiscardStack.add(infectionCard);
        return infectionCard;
    }

    public void handleInfectionCardsInEpidemic() {
        for (int i = 0; i < 100; i++) {
            InfectionCard card = infectionDiscardStack.get(getRandomIndex(infectionDiscardStack));
            infectionDiscardStack.remove(card);
            infectionDiscardStack.add(card);
        }

        // Add cards to top of InfectionCardStack
        Collections.reverse(infectionStack);
        infectionStack.addAll(infectionDiscardStack);
        Collections.reverse(infectionStack);
        notifyAllObservers();
    }

    public void shuffleAllStacks() {
        for (int i = 0; i < 100; i++) {
            shufflePlayerCardStack();
            shuffleInfectionStack();
        }
        notifyAllObservers();
    }

    private void shufflePlayerCardStack() {
        PlayerCard playerCard = playerStack.get(getRandomIndex(playerStack));
        playerStack.remove(playerCard);
        playerStack.add(playerCard);
    }

    private void shuffleInfectionStack() {
        InfectionCard infectionCard = infectionStack.get(getRandomIndex(infectionStack));
        infectionStack.remove(infectionCard);
        infectionStack.add(infectionCard);
    }

    private int getRandomIndex(ArrayList arrayList) {
        return (int) (Math.random() * arrayList.size());
    }

    public void increaseOutbreakCounter() {
        outbreakCounter++;
        notifyAllObservers();
    }

    public void increaseInfectionRate(int infectionRate) {
        this.infectionRate = infectionRate;
        notifyAllObservers();
    }

    public void addCubes(City currentCity, int cubeAmount) {
        VirusType virusType = currentCity.getVirusType();

        for (int i = 0; i < cubeAmount; i++) {
            currentCity.addCube(virusType);
            tryToDecreaseCubeAmount(virusType, cubeAmount);
        }

        notifyAllObservers();
    }

    private void tryToIncreaseCubeAmount(VirusType virusType, int cubeAmount) {
        try {
            getVirusByType(virusType).increaseCubeAmount(cubeAmount);
        } catch (VirusNotFoundException vnfe) {
            vnfe.printStackTrace();
        }
    }

    public void removeCubes(City currentCity, VirusType virusType, int cubeAmount) {
        currentCity.removeCube();
        tryToIncreaseCubeAmount(virusType, cubeAmount);
        notifyAllObservers();
    }

    private void tryToDecreaseCubeAmount(VirusType virusType, int cubeAmount) {
        try {
            getVirusByType(virusType).decreaseCubeAmount(cubeAmount);
        } catch (VirusNotFoundException vnfe) {
            vnfe.printStackTrace();
        }
    }

    public Virus getVirusByType(VirusType type) throws VirusNotFoundException {
        for (Virus virus : VIRUSES) {
            if (virus.getType().equals(type)) {
                return virus;
            }
        }

        throw new VirusNotFoundException("Virus not Found");
    }

    @Override
    public List<City> getCities() {
        return cities;
    }

    @Override
    public List<Cure> getCures() {
        return CURES;
    }

    @Override
    public List<Virus> getViruses() {
        return VIRUSES;
    }

    @Override
    public ArrayList<InfectionCard> getInfectionStack() {
        return infectionStack;
    }

    @Override
    public ArrayList<InfectionCard> getInfectionDiscardStack() {
        return infectionDiscardStack;
    }

    public City getCity(String cityName) throws CityNotFoundException {
        for (City city : cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }

        throw new CityNotFoundException("City not found" + " : " + cityName);
    }

    @Override
    public int getOutbreakCounter() {
        return outbreakCounter;
    }

    @Override
    public int getInfectionRate() {
        return INFECTION_RATES.get(infectionRate);
    }

    @Override
    public ArrayList<PlayerCard> getPlayerStack() {
        return playerStack;
    }

    @Override
    public ArrayList<PlayerCard> getPlayerDiscardStack() {
        return playerDiscardStack;
    }

    @Override
    public ArrayList<City> getCitiesWithResearchStations() {
        return citiesWithResearchStations;
    }

    public boolean cityHasResearchStation(City city) {
        return citiesWithResearchStations.contains(city);
    }

    public void addResearchStationToCity(City city) {
        for (City c : cities) {
            if (c.equals(city)) {
                citiesWithResearchStations.add(c);
                break;
            }
        }
        notifyAllObservers();
    }

    public void handleEpidemicCard() {
        addDrawnEpidemicCard();
        increaseInfectionRate(INFECTION_RATES.get(drawnEpidemicCards));
        handleInfectionCardsInEpidemic();
    }

    public void handleInfectionCardDraw(int cubeAmount) {
        for (int i = 0; i < infectionRate; i++) {
            handleInfection(drawInfectionCard(), cubeAmount);
        }
    }

    public boolean cureIsFound(VirusType virus) {
        for (Cure cure : getCuredDiseases()) {
            if (cure.getType().equals(virus)) {
                return true;
            }
        }

        return false;
    }

    public void initializeStartingCubes() {
        for (int drawAmount = 3; drawAmount > 0; drawAmount--) {
            for (int i = 0; i < 3; i++) {
                assignStartingCubeToRandomCity(drawAmount);
            }
        }
    }

    private void assignStartingCubeToRandomCity(int cubeAmount) {
        City randomCity = drawInfectionCard().getCity();
        addCubes(randomCity, cubeAmount);
    }

    public void handleInfection(InfectionCard infectionCard, int cubeAmount) {
        City infectedCity = infectionCard.getCity();

        if (infectedCity.getCubeAmount() >= 3) {  // Hier moet de quarantine specialist nog toegevoegd worden
            handleOutbreak(infectedCity);
        } else {
            addCubes(infectedCity, cubeAmount);
        }
    }

    public void handleOutbreak(City infectedCity) {
        increaseOutbreakCounter();
        addCityThatHadOutbreak(infectedCity);

        ArrayList<City> nearCities = getNearCities(infectedCity);

        for (City city : nearCities) {
            if (infectedCity.getCubeAmount() >= 3 && !cityHadOutbreak(city)) {  // Hier moet de quarantine specialist nog toegevoegd worden
                handleOutbreak(city);
            } else {
                addCubes(city, 1);
            }
        }
    }

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

    public ArrayList<Cure> getCuredDiseases() {
        ArrayList<Cure> curedDiseases = new ArrayList<>();

        for (Cure cure : CURES) {
            if (cure.getCureState().equals(CureState.CURED)) {
                curedDiseases.add(cure);
            }
        }

        return curedDiseases;
    }

    public void setCuredDiseases(ArrayList<Cure> curedDiseases) {
        for (Cure curedDisease : curedDiseases) {
            for (Cure cure : CURES) {
                if (cure.getType().equals(curedDisease.getType())) {
                    cure.setCureState(CureState.CURED);
                }
            }
        }
    }

    public int getDrawnEpidemicCards() {
        return drawnEpidemicCards;
    }

    public void addDrawnEpidemicCard() {
        drawnEpidemicCards++;
        notifyAllObservers();
    }

    public boolean gameboardHasResearchStationsLeft() {
        return citiesWithResearchStations.size() < 6;
    }

    public boolean lossByCubeAmount() {
        for (Virus virus : VIRUSES) {
            if (virus.getCubeAmount() < 0) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<City> createCitiesWithResearchStation() {
        try {
            return new ArrayList<>(Collections.singletonList(getCity("Atlanta")));
        } catch (CityNotFoundException cnfe) {
            cnfe.printStackTrace();
            return getNewAtlantaCity();
        }
    }

    public ArrayList<City> getNewAtlantaCity() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Atlanta", VirusType.BLUE));
        return cities;
    }

    public ArrayList<InfectionCard> getTopSixInfectionStack() {
        ArrayList<InfectionCard> topSix = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            topSix.add(infectionStack.get(i));
        }

        for (int i = 0; i < topSix.size(); i++) {
            infectionStack.remove(0);
        }

        return topSix;
    }

    public void addInfectionStack(ArrayList<InfectionCard> infectionCards) {
        infectionStack.addAll(infectionCards);
        notifyAllObservers();
    }

    @Override
    public void register(GameBoardObserver gameBoardObserver) {
        unregisterAllObservers();
        observers.add(gameBoardObserver);
        notifyAllObservers();
    }

    @Override
    public void unregisterAllObservers() {
        observers = new ArrayList<>();
    }

    @Override
    public void notifyAllObservers() {
        for (GameBoardObserver observer : observers) {
            observer.update(this);
        }
    }
}
