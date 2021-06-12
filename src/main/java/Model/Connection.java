package Model;

import javafx.scene.shape.Line;

public class Connection {

    private final int[] cityOne;
    private final int[] cityTwo;

    public Connection(int[] cityOne, int[] cityTwo) {
        this.cityOne = cityOne;
        this.cityTwo = cityTwo;
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
