package Model;

import Observers.Observable;
import Observers.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Gameboard implements Observable {
    private final List<Observer> observers = new ArrayList<>();
    private final City[] cities = this.initializeCities();
    private final Cure[] cures = new Cure[]{new Cure(VirusType.BLUE),
                                            new Cure(VirusType.YELLOW),
                                            new Cure(VirusType.BLACK),
                                            new Cure(VirusType.RED)};
    private final Virus[] viruses = new Virus[]{new Virus(VirusType.RED),
                                                 new Virus(VirusType.BLUE),
                                                 new Virus(VirusType.YELLOW),
                                                 new Virus(VirusType.BLACK)};
    private final ArrayList<InfectionCard> infectionStack = this.initializeInfectionCardStack();
    private final ArrayList<InfectionCard> infectionDiscardStack = new ArrayList<>();
    private final ArrayList<PlayerCard> playerStack = this.initializePlayerCardStack();
    private final ArrayList<PlayerCard> playerDiscardStack = new ArrayList<>();
    private int outbreakCounter = 0;
    private int infectionRate = 1;
    private int drawnEpidemicCards = 0;
    private final ArrayList<City> citiesWithResearchStations = new ArrayList<>(Arrays.asList(this.getCity("Atlanta")));
    private ArrayList<City> citiesThatHadOutbreak;
    private final int[] infectionRates = new int[]{2, 2, 2, 3, 3, 4, 4};

    public Gameboard() {
        initializeGameBoard();
    }

    public void initializeGameBoard() {
        shuffleAllStacks();
    }

    private City[] initializeCities() {
        City[] cities = new City[48];
        String[] cityNames = new String[]{"San Fransisco", "Chicago", "Atlanta", "Montreal", "Washington", "New York", "Madrid", "London", "Paris", "Essen", "Milan", "St. Petersburg",  // Blue
            "Los Angeles", "Mexico City", "Miami", "Bogota", "Lima", "Santiago", "Buenos Aires", "Sao Paulo", "Lagos", "Kinshasa", "Khartoum", "Johannesburg",  // Yellow
            "Algiers", "Istanbul", "Moscow", "Cairo", "Baghdad", "Riyadh", "Karachi", "Tehran", " Delhi", "Mumbai", "Kolkata", "Chennai",  // Black
            "Bangkok", "Jakarta", "Ho Chi Minh City", "Hong Kong", "Shanghai", "Beijing", "Seoul", "Tokyo", "Osaka", "Taipei", "Manila", "Sydney"};  // Red

        int x = 0;

        for (int i = 0; i < cityNames.length; i++){
            if (i % (cityNames.length/viruses.length) == 0) {
                x++;
            }
            cities[i] = new City(cities[i].getName(), viruses[x-1].getType());
        }

        return cities;
    }

    private ArrayList<InfectionCard> initializeInfectionCardStack() {
        ArrayList<InfectionCard> infectionCardStack = new ArrayList<>();

        for (City city : this.cities) {
            infectionCardStack.add(new InfectionCard(city));
        }

        return infectionCardStack;
    }

    private ArrayList<PlayerCard> initializePlayerCardStack() {
        ArrayList<PlayerCard> playerCardStack = new ArrayList<>();

        for (City city : this.cities) {
            playerCardStack.add(new CityCard(city, city.getVirusType()));
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
    }

    public PlayerCard drawPlayerCard() {
        PlayerCard playerCard = playerStack.get(0);
        playerStack.remove(0);
        return playerCard;
    }

    public void discardPlayerCard(PlayerCard card) {
        playerDiscardStack.add(0, playerStack.get(0));
    }

    public InfectionCard drawInfectionCard() {
        InfectionCard infectionCard = infectionStack.get(0);
        infectionStack.remove(0);
        infectionDiscardStack.add(infectionCard);
        return infectionCard;
    }

    public void handleInfectionCardsInEpidemic() {
        for (int i = 0; i < 100; i++) {
            InfectionCard card = infectionDiscardStack.get((int) (Math.random()) * infectionDiscardStack.size());
            infectionDiscardStack.remove(card);
            infectionDiscardStack.add(card);
        }

        // Add cards to top of InfectionCardStack
        Collections.reverse(infectionStack);
        infectionStack.addAll(infectionDiscardStack);
        Collections.reverse(infectionStack);
    }

    public void shuffleAllStacks() {
        for (int i = 0; i < 100; i++) {
            PlayerCard playerCard = playerStack.get((int) (Math.random()) * playerStack.size());
            playerStack.remove(playerCard);
            playerStack.add(playerCard);

            InfectionCard infectionCard = infectionStack.get((int) (Math.random()) * infectionStack.size());
            infectionStack.remove(infectionCard);
            infectionStack.add(infectionCard);
        }
    }

    public void increaseOutbreakCounter() {
        outbreakCounter++;
    }

    public void increaseInfectionRate(int infectionRate) {
        this.infectionRate = infectionRate;
    }

    public void addCubes(City currentCity, VirusType type, int cubeAmount) {
        currentCity.addCube(type);
        this.getVirusByType(type).decreaseCubeAmount(cubeAmount);
    }

    public void removeCubes(City currentCity, VirusType type, int cubeAmount) {
        currentCity.removeCube();
        this.getVirusByType(type).increaseCubeAmount(cubeAmount);
    }

    public Virus getVirusByType(VirusType type) {
        for (Virus virus : viruses) {
            if(virus.getType() == type) {
                return virus;
            }
        }

        return null;
    }

    public Virus[] getViruses() {
        return viruses;
    }

    public City getCity(String cityName) {
        for (City city : cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }

        return null;
    }

    public int getOutbreakCounter() {
        return outbreakCounter;
    }

    public int getInfectionRate() {
        return infectionRate;
    }

    public ArrayList<PlayerCard> getPlayerStack() {
        return playerStack;
    }

    public ArrayList<City> getCitiesWithResearchStations() {
        return citiesWithResearchStations;
    }

    public void addResearchStationToCity(City city) {
        for (City c : cities) {
            if (c.equals(city)) { ;
                citiesWithResearchStations.add(c);
                break;
            }
        }
    }

    public void handleEpidemicCard() {addDrawnEpidemicCard();
        increaseInfectionRate(infectionRates[getDrawnEpidemicCards()]);
        handleInfectionCardsInEpidemic();
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
            if(cure.getCureState().equals(CureState.CURED)){
                curedDiseases.add(cure);
            }
        }

        return curedDiseases;
    }

    public ArrayList<InfectionCard> getInfectionDiscardStack() {
        return infectionDiscardStack;
    }

    public int getDrawnEpidemicCards() {
        return drawnEpidemicCards;
    }

    public void addDrawnEpidemicCard() {
        drawnEpidemicCards++;
    }

    public boolean gameboardHasResearchStationsLeft() {
        return citiesWithResearchStations.size() < 6;
    }

    public boolean lossByCubeAmount() {
        for (Virus virus : getViruses()) {
            if(virus.getCubeAmount() < 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
