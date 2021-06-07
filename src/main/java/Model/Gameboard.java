package Model;

import Observers.GameBoardObservable;
import Observers.GameBoardObserver;
import Observers.GameObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gameboard implements GameBoardObservable {
    private final List<GameBoardObserver> observers = new ArrayList<>();
    private final City[] cities = this.initializeCities();
    private final Cure[] cures = new Cure[]{new Cure(VirusType.RED),
                                            new Cure(VirusType.BLUE),
                                            new Cure(VirusType.YELLOW),
                                            new Cure(VirusType.BLACK)};
    private final Virus[] viruses = new Virus[]{new Virus(VirusType.RED),
                                                 new Virus(VirusType.BLUE),
                                                 new Virus(VirusType.YELLOW),
                                                 new Virus(VirusType.BLACK)};
    private ArrayList<InfectionCard> infectionStack;
    private ArrayList<InfectionCard> infectionDiscardStack = new ArrayList<InfectionCard>();
    private ArrayList<PlayerCard> playerStack;
    private ArrayList<PlayerCard> playerDiscardStack = new ArrayList<PlayerCard>();
    private int outbreakCounter = 0;
    private int infectionRate = 0;
    private ArrayList<City> citiesWithResearchStations = new ArrayList<City>(Arrays.asList(this.getCity("Atlanta")));
    private ArrayList<City> citiesToAddCubesTo;

    public Gameboard(ArrayList<InfectionCard> infectionStack, ArrayList<PlayerCard> playerStack) {
        this.infectionStack = infectionStack;
        this.playerStack = playerStack;
    }

    public City[] initializeCities(){
        City[] cities = new City[48];
        String[] cityNames = new String[]{"San Fransisco", "Chicago", "Atlanta", "Montreal", "Washington", "New York", "Madrid", "London", "Paris", "Essen", "Milan", "St. Petersburg",  // Blue
            "Los Angeles", "Mexico City", "Miami", "Bogota", "Lima", "Santiago", "Buenos Aires", "Sao Paulo", "Lagos", "Kinshasa", "Khartoum", "Johannesburg",  // Yellow
            "Algiers", "Istanbul", "Moscow", "Cairo", "Baghdad", "Riyadh", "Karachi", "Tehran", " Dehli", "Mumbai", "Kolkata", "Chennai",  // Black
            "Bankok", "Jakata", "Ho Chi Minh City", "Hong Kong", "Shanghai", "Beijing", "Seoul", "Tokyo", "Osaka", "Taipei", "Manila", "Sydney"};  // Red

        int x = 0;
        for(int i = 0; i < cityNames.length; i++){
            if(i % cityNames.length/viruses.length == 0) {
                x++;
            }
            cities[i] = new City(cities[i].getName(), viruses[x].getType());
        }

        return cities;
    }

    public void flipCurePawn(Cure cure) {
        if(cure.getCureState().equals("active")) {
            cure.setCureState("cured");
        } else if(cure.getCureState().equals("cured")) {
            cure.setCureState("eradicated");
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
            if (c.equals(city)) { ;
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
        ArrayList<Cure> curedDiseases = new ArrayList<Cure>();
        for(Cure cure : cures) {
            if(cure.getCureState().equals("cured")){
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
    public void register(GameBoardObserver gameBoardObserver) {
        observers.add(gameBoardObserver);
    }

    @Override
    public void notifyAllObservers() {

    }
}
