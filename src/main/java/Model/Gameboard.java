package Model;

import Exceptions.CityNotFoundException;
import Exceptions.CureNotFoundException;
import Exceptions.VirusNotFoundException;
import Observers.GameBoardObservable;
import Observers.GameBoardObserver;

import java.io.*;
import java.util.*;

public class Gameboard implements GameBoardObservable {
    private List<GameBoardObserver> observers = new ArrayList<>();
    private final String pathToConnectedCities = "src/main/connectedCities.txt";

    private final City[] cities;
    private final Cure[] cures = new Cure[]{new Cure(VirusType.BLUE),
                                            new Cure(VirusType.YELLOW),
                                            new Cure(VirusType.BLACK),
                                            new Cure(VirusType.RED)};
    private final Virus[] viruses = new Virus[]{new Virus(VirusType.BLUE),
                                                 new Virus(VirusType.YELLOW),
                                                 new Virus(VirusType.BLACK),
                                                 new Virus(VirusType.RED)};
    private final ArrayList<InfectionCard> infectionStack;
    private final ArrayList<InfectionCard> infectionDiscardStack = new ArrayList<>();
    private final ArrayList<PlayerCard> playerStack;
    private final ArrayList<PlayerCard> playerDiscardStack = new ArrayList<>();

    private int outbreakCounter = 0;
    private int infectionRate = 1;
    private int drawnEpidemicCards = 0;

    private final ArrayList<City> citiesWithResearchStations;
    private final ArrayList<City> citiesThatHadOutbreak = new ArrayList<>();
    private final int[] infectionRates = new int[]{2, 2, 2, 3, 3, 4, 4};

    public Gameboard() {
        cities = initializeCities();
        infectionStack = initializeInfectionCardStack();
        playerStack = initializePlayerCardStack();
        citiesWithResearchStations = createCitiesWithResearchStation();
        initializeGameBoard();
    }

    private void initializeGameBoard() {
        shuffleAllStacks();
    }

    private City[] initializeCities() {
        City[] newCities = new City[48];
        String[] cityNames = getCityNames();
        // TODO: initialize connections between cities
        return assignVirusToCities(newCities, cityNames);
    }

    private String[] getCityNames() {
        return new String[] {"San Fransisco", "Chicago", "Atlanta",
                "Montreal", "Washington", "New York", "Madrid", "London",
                "Paris", "Essen", "Milan", "St. Petersburg",  // Blue

                "Los Angeles", "Mexico City", "Miami", "Bogota", "Lima",
                "Santiago", "Buenos Aires", "Sao Paulo", "Lagos", "Kinshasa",
                "Khartoum", "Johannesburg",  // Yellow

                "Algiers", "Istanbul", "Moscow", "Cairo", "Baghdad", "Riyadh",
                "Karachi", "Tehran", " Delhi", "Mumbai", "Kolkata",
                "Chennai",  // Black

                "Bangkok", "Jakarta", "Ho Chi Minh City", "Hong Kong",
                "Shanghai", "Beijing", "Seoul", "Tokyo", "Osaka", "Taipei",
                "Manila", "Sydney"};  // Red
    }

    private City[] assignVirusToCities(City[] newCities, String[] cityNames) {
        int virusIndex = 0;
        for (int i = 0; i < cityNames.length; i++) {
            if (i % (cityNames.length/viruses.length) == 0) {
                virusIndex++;
            }
            VirusType virusType = viruses[virusIndex-1].getType();
            newCities[i] = new City(cityNames[i], virusType);
            //todo test dit voordat je pusht, lobby werkt hierdoor niet meer
//            try {
//                assignNeighboursToCity(newCities[i]);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return newCities;
    }

    private void assignNeighboursToCity(City city) throws IOException {
        BufferedReader bufferedReader = makeBufferedReader();
        String line = bufferedReader.readLine();
        ArrayList<City> neighbours = new ArrayList<>();

        try {
            while (line != null) {
                line = bufferedReader.readLine();
                String[] cityName = line.split(";");
                if(cityName[0].equals(city.getName())) {
                    neighbours.add(getCity(cityName[1]));
                }
            }
        } catch (CityNotFoundException e) {
            e.printStackTrace();
        }

        city.initializeNeighbours(neighbours);
    }

    private BufferedReader makeBufferedReader() throws FileNotFoundException {
        File textFile = new File(pathToConnectedCities);
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

    private EventCard[] initializeEventCards() {
        EventCard[] eventCards = new EventCard[5];
        eventCards[0] = new OneQuietNight("One quiet night", "Skip the next Infect Cities step.");
        eventCards[1] = new GovernmentGrant("Government grant", "Add 1 research station to any city.");
        eventCards[2] = new OneQuietNight("Airlift", "Move any 1 pawn to any city.");
        eventCards[3] = new OneQuietNight("Forecast", "Draw, look at and rearrange the top 6 cards of the Infection Deck, put them back on top.");
        eventCards[4] = new OneQuietNight("Resilient population", "Remove any 1 card in the Infection Discard Pile from the game.");

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
        for(Cure cure : cures) {
            if(cure.getType() == virusType) {
                return cure;
            }
        }
        throw new CureNotFoundException("Cure is not found");
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

    public void addCubes(City currentCity, VirusType virusType, int cubeAmount) {
        currentCity.addCube(virusType);
        tryToDecreaseCubeAmount(virusType, cubeAmount);
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
        for (Virus virus : viruses) {
            if (virus.getType().equals(type)) {
                return virus;
            }
        }

        throw new VirusNotFoundException("Virus not Found");
    }

    @Override
    public City[] getCities() {
        return cities;
    }

    @Override
    public Cure[] getCures() {
        return cures;
    }

    @Override
    public Virus[] getViruses() {
        return viruses;
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

        throw new CityNotFoundException("City not found");
    }

    @Override
    public int getOutbreakCounter() {
        return outbreakCounter;
    }

    @Override
    public int getInfectionRate() {
        return infectionRate;
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
        increaseInfectionRate(infectionRates[drawnEpidemicCards]);
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

    public void handleInfection(InfectionCard infectionCard, int cubeAmount) {
        City infectedCity = infectionCard.getCity();

        if (infectedCity.getCubeAmount() >= 3) {  // Hier moet de quarantine specialist nog toegevoegd worden
            handleOutbreak(infectedCity);
        } else {
            addCubes(infectedCity, infectedCity.getVirusType(), cubeAmount);
        }
    }

    public void handleOutbreak(City infectedCity) {
        increaseOutbreakCounter();
        addCityThatHadOutbreak(infectedCity);

        for (City city : infectedCity.getNearCities()) {
            if (infectedCity.getCubeAmount() >= 3 && !cityHadOutbreak(city)) {  // Hier moet de quarantine specialist nog toegevoegd worden
                handleOutbreak(city);
            } else {
                addCubes(city, infectedCity.getVirusType(), 1);
            }
        }
    }

    public void addCityThatHadOutbreak(City city) {
        citiesThatHadOutbreak.add(city);
    }

    public boolean cityHadOutbreak(City city) {
        return citiesThatHadOutbreak.contains(city);
    }

    public ArrayList<Cure> getCuredDiseases() {
        ArrayList<Cure> curedDiseases = new ArrayList<>();

        for (Cure cure : cures) {
            if (cure.getCureState().equals(CureState.CURED)) {
                curedDiseases.add(cure);
            }
        }

        return curedDiseases;
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
        for (Virus virus : viruses) {
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
        ArrayList<InfectionCard> topSix = (ArrayList<InfectionCard>) infectionStack.subList(0, 6);  // Ik weet niet zeker of dit werkt, kan errors geven

        for(int i = 0; i < topSix.size(); i++) {
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
