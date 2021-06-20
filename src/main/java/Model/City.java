package Model;

import java.util.ArrayList;

/**
 * @author : Thimo van Velzen
 */

public class City {
    private String name;
    private ArrayList<Cube> cubes;
    private VirusType VIRUS_TYPE;
    private ArrayList<String> nearCities;

    public City() {}

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

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Cube> getCubes() {
        return cubes;
    }

    public void setCubes(ArrayList<Cube> cubes) {
        this.cubes = cubes;
    }

    public VirusType getVIRUS_TYPE() {
        return VIRUS_TYPE;
    }

    public void setVIRUS_TYPE(VirusType VIRUS_TYPE) {
        this.VIRUS_TYPE = VIRUS_TYPE;
    }

    public void setNearCities(ArrayList<String> nearCities) {
        this.nearCities = nearCities;
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
