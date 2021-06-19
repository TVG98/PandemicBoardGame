package Model;

public class CityCard implements PlayerCard {

    private final City city;

    public CityCard(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public VirusType getVirusType() {
        return city.getVIRUS_TYPE();
    }

    @Override
    public String getName() {
        return city.getName();
    }
}
