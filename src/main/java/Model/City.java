package Model;

import java.util.ArrayList;

public class City {
    private final String name;
    private final ArrayList<Cube> cubes = new ArrayList<>();
    private final VirusType VIRUS_TYPE;
    private final ArrayList<String> nearCities = new ArrayList<>();

    public City(String name, VirusType virusType) {
        this.name = name;
        this.VIRUS_TYPE = virusType;
    }

    public String getName() {
        return name;
    }

    public int getCubeAmount() {
        return cubes.size();
    }

    public VirusType getVirusType() {
        return VIRUS_TYPE;
    }

    public boolean checkCityForAdjacency(City city) {
        return nearCities.contains(city.getName());
    }

    public void addNeighbour(String cityName) {
        nearCities.add(cityName);
    }

    public ArrayList<String> getNearCities() {
        return nearCities;
    }

    public void addCube(VirusType type) {
        cubes.add(new Cube(type));
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
