package Model;

public class Virus {
    private final VirusType type;
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
        cubeAmount = Math.min(cubeAmount + amount, 24);
    }

    public void decreaseCubeAmount(int amount) {
        cubeAmount = Math.max(cubeAmount - amount, 0);
    }
}
