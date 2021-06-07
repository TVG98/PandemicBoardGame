package Model;

public class Virus {
    private VirusType type;
    private int cubeAmount = 24;

    public Virus(VirusType type) {
        this.type = type;
    }

    public VirusType getType() {
        return type;
    }

    public int getCubeAmount() {
        return cubeAmount;
    }

    public void increaseCubeAmount(int amount) {
        cubeAmount += amount;
    }
    public void decreaseCubeAmount(int amount) {
        cubeAmount -= amount;
    }
}
