package Model;

public class CityCard implements PlayerCard {

    private final String name;
    private final City city;
    private final VirusType virusType;

    public CityCard(City city, VirusType virusType) {
        this.city = city;
        this.name = city.getName();
        this.virusType = virusType;
    }

    public City getCity() {
        return city;
    }
    public VirusType getVirusType() {
        return virusType;
    }

    @Override
    public String getName() {
        return name;
    }
}
