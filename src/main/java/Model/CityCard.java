package Model;

public class CityCard implements PlayerCard {
    private final String name;
    private final String color;

    public CityCard(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String getName() {
        return name;
    }
}
