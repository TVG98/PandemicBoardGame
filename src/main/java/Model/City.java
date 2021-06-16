package Model;

import java.util.ArrayList;

public class City {
    private final String name;
    private final ArrayList<Cube> cubes;
    private final VirusType VIRUS_TYPE;
    private final ArrayList<String> nearCities;

    public City(String name, VirusType virusType) {
        this.name = name;
        cubes = new ArrayList<>();
        this.VIRUS_TYPE = virusType;
        nearCities = new ArrayList<>();
    }

    public City(String name, ArrayList<Cube> cubes, VirusType VIRUS_TYPE, ArrayList<String> nearCities) {
        this.name = name;
        this.cubes = cubes;
        this.VIRUS_TYPE = VIRUS_TYPE;
        this.nearCities = nearCities;
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
