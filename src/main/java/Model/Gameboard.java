package Model;

import javax.swing.plaf.ColorUIResource;
import java.util.ArrayList;

public class Gameboard {
    private ArrayList<City> cities;
    private ArrayList<Cure> cures;
    private ArrayList<InfectionCard> infectionStack;
    private ArrayList<InfectionCard> infectionDiscardStack;
    private ArrayList<PlayerCard> playerStack;
    private ArrayList<PlayerCard> playerDiscardStack;
    private int outbreakCounter;
    private int infectionRate;
    private ArrayList<City> citiesWithResearchStations;
    private ArrayList<City> citiesToAddCubesTo;

    public Gameboard(ArrayList<City> cities, ArrayList<Cure> cures, ArrayList<InfectionCard> infectionStack, ArrayList<InfectionCard> infectionDiscardStack, ArrayList<PlayerCard> playerStack, ArrayList<PlayerCard> playerDiscardStack, int outbreakCounter, int infectionRate, ArrayList<City> citiesWithResearchStations, ArrayList<City> citiesToAddCubesTo) {
        this.cities = cities;
        this.cures = cures;
        this.infectionStack = infectionStack;
        this.infectionDiscardStack = infectionDiscardStack;
        this.playerStack = playerStack;
        this.playerDiscardStack = playerDiscardStack;
        this.outbreakCounter = outbreakCounter;
        this.infectionRate = infectionRate;
        this.citiesWithResearchStations = citiesWithResearchStations;
        this.citiesToAddCubesTo = citiesToAddCubesTo;
    }

    /*public void flipCurePawn(Cure cure) {
        if(cure.getCureState().equals("active")) {
            cure.setCureState("cured");
        } else if(cure.getCureState().equals("cured")) {
            cure.setCureState("eradicated");
        }
    }*/

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
    }

    public City getCity(String cityName) {
        for (City city : cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }
        return null;
    }

    public ArrayList<City> getCitiesWithResearchStations() {
        return citiesWithResearchStations;
    }

    public void addResearchStationToCity(City city) {
        for (City c : cities) {
            if (c.equals(city)) {
                c.addResearchStation();
                citiesWithResearchStations.add(c);
                break;
            }
        }
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

    public ArrayList<City> getCitiesToAddCubesTo() { //model: getCityToAddCubeTo()
        return citiesToAddCubesTo;
    }

    public void addCityToAddCubeTo(City city) {
        citiesToAddCubesTo.add(city);
    }

    public boolean gameboardHasResearchStationsLeft() {
        return citiesWithResearchStations.size() < 6;
    }
}
