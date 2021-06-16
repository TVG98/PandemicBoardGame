package Model;

import javafx.scene.shape.Line;

public class Connection {

    private final int[] cityOne;
    private final int[] cityTwo;

    public Connection(int[] cityOne, int[] cityTwo) {
        this.cityOne = cityOne;
        this.cityTwo = cityTwo;
    }

    public int getShortestX() {
        return Math.min(cityOne[0], cityTwo[0]);
    }

    public int getShortestY() {
        return Math.min(cityOne[1], cityTwo[1]);
    }

    public int getYDifference() {
        return Math.abs(cityOne[1] - cityTwo[1]);
    }

    public int[] getCityOne() {
        return cityOne;
    }

    public int[] getCityTwo() {
        return cityTwo;
    }

    public int[] getLeftCity() {
        return (cityOne[0] < cityTwo[0]) ? cityOne : cityTwo;
    }

    public int[] getRightCity() {
        return (cityOne[0] > cityTwo[0]) ? cityOne : cityTwo;
    }

    public Line getLineFromConnection() {
        Line line = new Line();
        line.setStartX(cityOne[0]);
        line.setStartY(cityOne[1]);
        line.setEndX(cityTwo[0]);
        line.setEndY(cityTwo[1]);
        line.setStyle("-fx-stroke:" + "#9a1ac9;");
        line.setStrokeWidth(4);

        return line;
    }

}
