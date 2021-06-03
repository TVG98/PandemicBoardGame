package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

/**
 * @created May 27 2021 - 10:23 AM
 * @project testGame
 */

public class GameView
{
    Stage primaryStage;
    final String pathToImage = "src/main/media/GameBoard.jpg";
    final double width = 1600;
    final double height = 900;


    public GameView(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(true);
        loadStageWithBorderPane(createGameViewBorderPane());
    }

    private BorderPane createGameViewBorderPane()
    {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Setup BorderPane Top //
        Text title = new Text("Pandemic");
        Button openMenuButton = new Button("Open Menu");

        // Top Left //
        VBox vboxTopLeft = new VBox();
        vboxTopLeft.getChildren().addAll(title, openMenuButton);
        vboxTopLeft.setAlignment(Pos.TOP_LEFT);

        // Top Center //

        Text virusText = new Text("Viruses");
        Rectangle virus1 = new Rectangle(200, 200);
        Rectangle virus2 = new Rectangle(200, 200);
        Rectangle virus3 = new Rectangle(200, 200);
        Rectangle virus4 = new Rectangle(200, 200);

        HBox hboxViruses = new HBox();
        hboxViruses.getChildren().addAll(virus1, virus2, virus3, virus4);

        VBox vboxTopCenter = new VBox();
        vboxTopCenter.getChildren().addAll(virusText, hboxViruses);

        // Top Right //

        HBox hboxTop = new HBox();
        hboxTop.getChildren().addAll(vboxTopLeft, vboxTopCenter);
        hboxTop.setSpacing(200);

        // Setup BorderPane Bottom //

        // BorderPane Layout //
        bp.setTop(hboxTop);


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
