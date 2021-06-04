package Model;

public class Virus {
    private VirusType type;
    private int cubeAmount;

    public Virus(VirusType type, int cubeAmount) {
        this.type = type;
        this.cubeAmount = cubeAmount;
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
