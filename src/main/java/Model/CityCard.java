package Model;

/**
 * A city card in the game.
 * @author Daniel Paans
 */
public class CityCard extends PlayerCard {

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

    public String getName() {
        return city.getName();
    }
}
