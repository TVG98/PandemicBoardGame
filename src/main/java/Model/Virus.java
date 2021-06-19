package Model;

public class Virus {
    private VirusType virusType;
    private final int MAX_CUBE_AMOUNT = 24;

    private int cubeAmount = 24;

    public Virus() {}

    public Virus(VirusType virusType) {
        this.virusType = virusType;
    }

    public void setVirusType(VirusType virusType) {
        this.virusType = virusType;
    }

    public VirusType getVirusType() {
        return virusType;
    }

    public int getCubeAmount() {
        return cubeAmount;
    }

    public void setCubeAmount(int cubeAmount) {
        this.cubeAmount = cubeAmount;
    }

    public void increaseCubeAmount(int amount) {
        cubeAmount = Math.min(cubeAmount + amount, MAX_CUBE_AMOUNT);
    }

    public void decreaseCubeAmount(int amount) {
        cubeAmount = Math.max(cubeAmount - amount, 0);
    }
}
