package Model;

/**
 * An infection card object in the game.
 * @author Thimo van Velzen, Daniel Paans
 */

public class InfectionCard {
    private City city;

    public InfectionCard() {}

    public InfectionCard(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
