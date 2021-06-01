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

    public void flipCurePawn(Cure cure) {

    }

   /* public PlayerCard drawPlayerCard() {

    }*/

    public void discardPlayerCard(PlayerCard card) {

    }

    /*public InfectionCard drawInfectionCard() {

    }*/

    public void discardInfectionCard(InfectionCard card) {

    }

    public void shuffleCards(ArrayList cards) { // blote arraylist?

    }

    public void increaseOutbreakCounter() {

    }

    public void increaseInfectionRate() {

    }

    public void addCubes(String type) {

    }


    /*public City getCity(String cityName) {

    }*/

    public ArrayList<City> getCitiesWithResearchStations() {
        return citiesWithResearchStations;
    }

    public void addResearchStationToCity(City city) {

    }

    /*public ArrayList<Cure> getCuredDiseases() {

    }*/

    public ArrayList<City> getCitiesToAddCubesTo() { //model: getCityToAddCubeTo()
        return citiesToAddCubesTo;
    }

    public void addCityToAddCubeTo(City city) {

    }
}
