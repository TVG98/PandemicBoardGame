package Model;

import java.util.ArrayList;

public class City {
    private String name;
    private ArrayList<Cube> cubes;
    private String virusType;
    private ArrayList<City> nearCities;
    private boolean hasStation;

    public City(String name, String virusType, ArrayList<City> nearCities, boolean hasStation) {
        this.name = name;
        this.virusType = virusType;
        this.nearCities = nearCities;
        this.hasStation = hasStation;
    }

    public String getName() {
        return name;
    }

    public int getCubeAmount() {
        return this.cubes.size();
    }

    public String getVirusType() {
        return virusType;
    }

   /* public boolean checkCityForAdjacency(City city) {

    }*/

    /*public boolean checkForResearchStation() {

    }*/

    public void initializeNeighbours() {

    }

    public void addCube(String type) {
        this.cubes.add(new Cube(type));
    }

    public void addResearchStation() {

    }

}
