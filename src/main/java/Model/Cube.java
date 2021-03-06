package Model;

/**
 * A cube object in the game.
 * @author Thimo van Velzen
 */

public class Cube {
    private VirusType type;

    public Cube() {}

    public Cube(VirusType type) {
        this.type = type;
    }

    public void setType(VirusType type) {
        this.type = type;
    }

    public VirusType getType() {
        return type;
    }
}
