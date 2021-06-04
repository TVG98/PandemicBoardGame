package Model;

import java.util.ArrayList;

public class City {
    private final String name;
    private ArrayList<Cube> cubes;
    private VirusType virusType;
    private ArrayList<City> nearCities;
    private boolean hasStation;

    public City(String name, VirusType virusType, ArrayList<City> nearCities, boolean hasStation) {
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

    public VirusType getVirusType() {
        return virusType;
    }

    public boolean checkCityForAdjacency(City city) {
        return nearCities.contains(city);
    }

    public boolean checkForResearchStation() {
        return hasStation;
    }

    public void initializeNeighbours(ArrayList<City> nearCities) {
        this.nearCities = nearCities;
    }

    public ArrayList<City> getNearCities() {
        return nearCities;
    }

    public void addCube(VirusType type) {
        cubes.add(new Cube(type));  // We moeten wel nog ergens de algemene cubeAmount bijhouden wat 24 is.
    }

    public void addResearchStation() {
        hasStation = true;
    }

    public void removeAllCubes() {
        cubes.clear();
    }

    public void removeCube() {
        cubes.remove(0);
    }
}
