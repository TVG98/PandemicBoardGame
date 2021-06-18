package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class InGameMenuView {
    Stage primaryStage;
    final String pathToImage = "src/main/media/GameBoardResized.jpg";
    final double width = 1600;
    final double height = 900;

    public InGameMenuView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadStageWithBorderPane(createInGameMenuViewBorderPane());
    }

    private void loadStageWithBorderPane(BorderPane bp) {
        try {
            Scene scene = new Scene(bp, width, height);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BorderPane createInGameMenuViewBorderPane() {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // BorderPane Shapes Setup //
        Rectangle backgroundDrop = new Rectangle(width, height);
        backgroundDrop.setFill(Color.color(0, 0, 0));
        backgroundDrop.setOpacity(0.60);
        Rectangle menuBackground = new Rectangle(800, 700);
        menuBackground.setFill(Color.color(0f, 0f, 0f, 0.7f));
        menuBackground.setX((width / 2) - (800 / 2f));
        menuBackground.setY((height / 2) - (700 / 2f));

        // BorderPane Center Setup //
        Text menuText = new Text("Menu");

        Button closeMenuButton = new Button("Close Menu");
        closeMenuButton.setOnAction(event -> closeMenuButtonHandler());

        Button backToMainButton = new Button("Back to main menu");
        backToMainButton.setOnAction(event -> backToMainMenuButtonHandler());

        Button quitGameButton = new Button("Quit game");
        quitGameButton.setOnAction(event -> primaryStage.close());

        ArrayList<Button> menuButtons = new ArrayList<>();
        Collections.addAll(menuButtons, closeMenuButton, backToMainButton, quitGameButton);

        for (Button menuButton : menuButtons) {
            menuButton.setAlignment(Pos.CENTER);
            menuButton.setOpacity(0.70);
            menuButton.setStyle("-fx-background-color: firebrick; -fx-background-radius: 30px;");
            menuButton.setPrefHeight(80);
            menuButton.setPrefWidth(550);
            menuButton.setFont(new Font("Castellar", 40));
            menuButton.setTextFill(Color.BLACK);
            menuButton.setEffect(new DropShadow());
            menuButton.setOnMouseEntered(event -> menuButton.setStyle("-fx-background-color: #ff5c6c; -fx-background-radius: 30px;"));
            menuButton.setOnMouseExited(event -> menuButton.setStyle("-fx-background-color: firebrick; -fx-background-radius: 30px;"));
        }
        menuText.setFill(Color.FIREBRICK);
        menuText.setFont(Font.font("Castellar", 80));

        VBox vboxCenter = new VBox();
        vboxCenter.getChildren().addAll(menuText, closeMenuButton, backToMainButton, quitGameButton);
        vboxCenter.setAlignment(Pos.CENTER);
        vboxCenter.setSpacing(50);

        // BorderPane layout //
        bp.getChildren().addAll(backgroundDrop, menuBackground);
        bp.setCenter(vboxCenter);

        return bp;
    }

    private void closeMenuButtonHandler() {
        GameView view = GameView.getInstance(primaryStage);
    }

    private void backToMainMenuButtonHandler() {
        MenuView view = new MenuView(primaryStage);
    }
}
