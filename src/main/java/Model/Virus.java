package Model;

public class Virus {
    private String type;
    private int cubeAmount;

    public Virus(String type, int cubeAmount) {
        this.type = type;
        this.cubeAmount = cubeAmount;
    }

    public String getType() {
        return type;
    }

    public int getCubeAmount() {
        return cubeAmount;
    }

    public void changeCubeAmount(int amount) {
        cubeAmount = amount;
    }
}
