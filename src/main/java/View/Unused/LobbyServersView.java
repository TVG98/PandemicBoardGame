package View.Unused;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Romano Biertantie
 */

public class LobbyServersView
{
    Stage primaryStage;
    final String pathToImage = "src/main/media/LobbyBackground.jpg";
    final double width = 1280;
    final double height = 960;

    public LobbyServersView(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        loadStageWithBorderPane(createLobbyServerBorderPane());
    }

    private BorderPane createLobbyServerBorderPane()
    {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Setup Background Shapes //
        Rectangle lobbySpot1 = new Rectangle(300, 300);
        lobbySpot1.setX((width / 3) * 1 - 450);
        lobbySpot1.setY((height / 5) * 2 - 250);
        Rectangle lobbySpot2 = new Rectangle(300, 300);
        lobbySpot2.setX((width / 3) * 2 - 375);
        lobbySpot2.setY((height / 5) * 2 - 250);
        Rectangle lobbySpot3 = new Rectangle(300, 300);
        lobbySpot3.setX((width / 3) * 3 - 300);
        lobbySpot3.setY((height / 5) * 2 - 250);
        Rectangle lobbySpot4 = new Rectangle(300, 300);
        lobbySpot4.setX((width / 3) * 1 - 450);
        lobbySpot4.setY((height / 5) * 4 - 250);
        Rectangle lobbySpot5 = new Rectangle(300, 300);
        lobbySpot5.setX((width / 3) * 2 - 375);
        lobbySpot5.setY((height / 5) * 4 - 250);
        Rectangle lobbySpot6 = new Rectangle(300, 300);
        lobbySpot6.setX((width / 3) * 3 - 300);
        lobbySpot6.setY((height / 5) * 4 - 250);

        ArrayList<Rectangle> lobbySpots = new ArrayList<Rectangle>();
        Collections.addAll(lobbySpots, lobbySpot1, lobbySpot2, lobbySpot3, lobbySpot4, lobbySpot5, lobbySpot6);

        for (Rectangle lobbySpot : lobbySpots)
        {
            lobbySpot.setFill(Color.GRAY);
        }

        // Setup BorderPane Top //


        // Setup BorderPane Layout //
        bp.getChildren().addAll(lobbySpots);


        return bp;
    }

    private void loadStageWithBorderPane(BorderPane bp){
        try {
            Scene mainMenu = new Scene(bp, width, height);
            this.primaryStage.setScene(mainMenu);
            this.primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
