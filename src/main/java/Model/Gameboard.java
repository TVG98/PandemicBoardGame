package Model;

import Controller.PlayerController;
import Observers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Gameboard implements Observable {
    private final List<Observer> observers = new ArrayList<>();
    private final City[] cities = this.initializeCities();
    private final Cure[] cures = new Cure[]{new Cure(VirusType.RED),
                                            new Cure(VirusType.BLUE),
                                            new Cure(VirusType.YELLOW),
                                            new Cure(VirusType.BLACK)};
    private final Virus[] viruses = new Virus[]{new Virus(VirusType.RED),
                                                 new Virus(VirusType.BLUE),
                                                 new Virus(VirusType.YELLOW),
                                                 new Virus(VirusType.BLACK)};
    private ArrayList<InfectionCard> infectionStack = this.initializeInfectionCardStack();
    private ArrayList<InfectionCard> infectionDiscardStack = new ArrayList<>();
    private ArrayList<PlayerCard> playerStack = this.initializePlayerCardStack();
    private ArrayList<PlayerCard> playerDiscardStack = new ArrayList<>();
    private int outbreakCounter = 0;
    private int infectionRate = 0;
    private ArrayList<City> citiesWithResearchStations = new ArrayList<>(Arrays.asList(this.getCity("Atlanta")));
    private ArrayList<City> citiesToAddCubesTo;

    public Gameboard() {  // Mischien is het mooier om een initializeGameBoard() method te maken die je in de constructor aanroept
        shuffleInfectionCards();
        shufflePlayerCards();
    }

    private City[] initializeCities() {
        City[] cities = new City[48];
        String[] cityNames = new String[]{"San Fransisco", "Chicago", "Atlanta", "Montreal", "Washington", "New York", "Madrid", "London", "Paris", "Essen", "Milan", "St. Petersburg",  // Blue
            "Los Angeles", "Mexico City", "Miami", "Bogota", "Lima", "Santiago", "Buenos Aires", "Sao Paulo", "Lagos", "Kinshasa", "Khartoum", "Johannesburg",  // Yellow
            "Algiers", "Istanbul", "Moscow", "Cairo", "Baghdad", "Riyadh", "Karachi", "Tehran", " Delhi", "Mumbai", "Kolkata", "Chennai",  // Black
            "Bangkok", "Jakarta", "Ho Chi Minh City", "Hong Kong", "Shanghai", "Beijing", "Seoul", "Tokyo", "Osaka", "Taipei", "Manila", "Sydney"};  // Red

        int x = 0;
        for(int i = 0; i < cityNames.length; i++){
            if(i % cityNames.length/viruses.length == 0) {
                x++;
            }
            cities[i] = new City(cities[i].getName(), viruses[x].getType());
        }

        return cities;
    }

    private ArrayList<InfectionCard> initializeInfectionCardStack() {
        ArrayList<InfectionCard> infectionCardStack = new ArrayList<InfectionCard>();

        for(City city : this.cities) {
            infectionCardStack.add(new InfectionCard(city));
        }

        return infectionCardStack;
    }

    private ArrayList<PlayerCard> initializePlayerCardStack() {
        ArrayList<PlayerCard> playerCardStack = new ArrayList<>();

        for(City city : this.cities) {
            playerCardStack.add(new CityCard(city, city.getVirusType()));
        }

        playerCardStack.addAll(Arrays.asList(initializeEventCards()));
        playerCardStack.addAll(Arrays.asList(initializeEpidemicCards(6)));

        return playerCardStack;
    }

    private EventCard[] initializeEventCards() {  // De Eventcards kunnen we misschien ook zonder parameters doen
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

        for(int i = 0; i < epidemicCardAmount; i++) {
            epidemicCards[i] = new EpidemicCard();
        }

        return epidemicCards;
    }

    public void flipCurePawn(Cure cure) {
        if(cure.getCureState().equals(CureState.ACTIVE)) {
            cure.setCureState(CureState.CURED);
        } else if(cure.getCureState().equals(CureState.CURED)) {
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
        return infectionCard;
    }

    public void discardInfectionCard(InfectionCard card) {
        infectionDiscardStack.add(0, infectionStack.get(0));
    }

    public void shuffleInfectionCards() {
        for (int i = 0; i < 100; i++) {
            InfectionCard card = infectionDiscardStack.get((int) (Math.random()) * infectionDiscardStack.size());
            infectionDiscardStack.remove(card);
            infectionDiscardStack.add(card);
        }
    }

    public void shufflePlayerCards() {
        for (int i = 0; i < 100; i++) {
            PlayerCard card = playerDiscardStack.get((int) (Math.random()) * playerDiscardStack.size());
            playerDiscardStack.remove(card);
            playerDiscardStack.add(card);
        }
    }

    public void increaseOutbreakCounter() {
        outbreakCounter++;
    }

    public void increaseInfectionRate() {
        infectionRate++;
    }

    public void addCubes(City currentCity, VirusType type) {
        currentCity.addCube(type);
        this.getVirusByType(type).decreaseCubeAmount(1);  // Waarschijnlijk zal dit altijd 1 zijn
    }

    public void removeCubes(City currentCity, VirusType type) {
        currentCity.removeCube();
        this.getVirusByType(type).increaseCubeAmount(1);  // Waarschijnlijk zal dit altijd 1 zijn
    }

    public Virus getVirusByType(VirusType type) {
        for(Virus virus : viruses) {
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
            if (c.equals(city)) {
                citiesWithResearchStations.add(c);
                break;
            }
        }
    }

    public boolean cityHasCube(City currentCity) {
        return currentCity.getCubeAmount() > 0;
    }

    public boolean cureIsFound(VirusType virus) {
        for (Cure cure : getCuredDiseases()) {
            if (cure.getType().equals(virus)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Cure> getCuredDiseases() {
        ArrayList<Cure> curedDiseases = new ArrayList<>();
        for(Cure cure : cures) {
            if(cure.getCureState().equals(CureState.CURED)){
                curedDiseases.add(cure);
            }
        }

        return curedDiseases;
    }

    @Deprecated  // Hebben we waarschijnlijk niet nodig
    public ArrayList<City> getCitiesToAddCubesTo() {
        return citiesToAddCubesTo;
    }

    @Deprecated
    public void addCityToAddCubeTo(City city) {
        citiesToAddCubesTo.add(city);
    }

    public boolean gameboardHasResearchStationsLeft() {
        return citiesWithResearchStations.size() < 6;
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
