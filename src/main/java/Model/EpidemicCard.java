package Model;

public class EpidemicCard implements PlayerCard {
    private final String name;

    public EpidemicCard(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
