package Model;

public class Virus {
    private final VirusType VIRUS_TYPE;
    private final int MAX_CUBE_AMOUNT = 24;

    private int cubeAmount = 24;

    public Virus(VirusType virusType) {
        this.VIRUS_TYPE = virusType;
    }

    public void setCubeAmount(int cubeAmount) {
        this.cubeAmount = cubeAmount;
    }

    public VirusType getType() {
        return VIRUS_TYPE;
    }

    public int getCubeAmount() {
        return cubeAmount;
    }

    public void increaseCubeAmount(int amount) {
        cubeAmount = Math.min(cubeAmount + amount, MAX_CUBE_AMOUNT);
    }

    public void decreaseCubeAmount(int amount) {
        cubeAmount = Math.max(cubeAmount - amount, 0);
    }
}
