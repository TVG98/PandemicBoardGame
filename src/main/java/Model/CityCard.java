package Model;

public class CityCard implements PlayerCard {

    private final String name;
    private final City city;

    public CityCard(City city) {
        this.city = city;
        this.name = city.getName();
    }

    public City getCity() {
        return city;
    }
    public VirusType getVirusType() {
        return city.getVirusType();
    }

    @Override
    public String getName() {
        return name;
    }
}
