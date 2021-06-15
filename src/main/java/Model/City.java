package Model;

import java.util.ArrayList;

public class City {
    private final String name;
    private final ArrayList<Cube> cubes = new ArrayList<>();
    private final VirusType virusType;
    private ArrayList<City> nearCities;

    public City(String name, VirusType virusType) {
        this.name = name;
        this.virusType = virusType;
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

    public void initializeNeighbours(ArrayList<City> nearCities) {
        this.nearCities = nearCities;
    }

    public ArrayList<City> getNearCities() {
        return nearCities;
    }

    public void addCube(VirusType type) {
        cubes.add(new Cube(type));  // We moeten wel nog ergens de algemene cubeAmount bijhouden, wat 24 is.
    }

    public void removeAllCubes() {
        cubes.clear();
    }

    public void removeCube() {
        if (cubes.size() > 0) {
            cubes.remove(0);
        }
    }
}
