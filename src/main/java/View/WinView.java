package View;

import Controller.SoundController;
import Model.Sound;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class WinView {

    Stage primaryStage;
    final String pathToImage = "src/main/media/SituationBackground.jpg";
    final double width = 1280;
    final double height = 960;
    SoundController soundController = SoundController.getInstance();

    public WinView(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        loadStageWithBorderPane(createWinBorderPane());
    }

    private BorderPane createWinBorderPane() {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Setup Background Shapes (winWindowBackgound) //
        Rectangle winWindowBackground = new Rectangle(400, 500, Color.GRAY);
        winWindowBackground.setX((width/2) - (400 / 2f));
        winWindowBackground.setY((height/2) - (500 / 2f));

        // BorderPane Center Setup (vboxCenter)//
        Text winText = new Text("You win!");
        winText.setFont(new Font("Arial", 50));
        winText.setFill(Color.GREEN);

        Button backToMainMenuButton = new Button("Back to main menu");
        backToMainMenuButton.setPrefHeight(60);
        backToMainMenuButton.setPrefWidth(200);
        backToMainMenuButton.setFont(new Font("Arial", 20));
        backToMainMenuButton.setStyle("-fx-background-color: Green");
        backToMainMenuButton.setEffect(new DropShadow());
        backToMainMenuButton.setOnAction(event -> backToMainMenuButtonHandler());

        backToMainMenuButton.setOnMouseEntered(e -> backToMainMenuButton.setStyle("-fx-background-color: Red"));
        backToMainMenuButton.setOnMouseExited(e -> backToMainMenuButton.setStyle("-fx-background-color: Green"));

        VBox vboxCenter = new VBox();
        vboxCenter.setAlignment(Pos.CENTER);
        vboxCenter.setSpacing(150);
        vboxCenter.getChildren().addAll(winText, backToMainMenuButton);

        // BorderPane Layout, order of elements placed is IMPORTANT for layering //
        bp.getChildren().add(winWindowBackground);
        bp.setCenter(vboxCenter);

        return bp;
    }

    private void loadStageWithBorderPane(BorderPane bp) {
        try {
            Scene scene = new Scene(bp, width, height);
            primaryStage.show();
            primaryStage.setScene(scene);
            soundController.playSound(Sound.VICTORY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void backToMainMenuButtonHandler() {
        soundController.playSound(Sound.BUTTON);
        MenuView view = new MenuView(primaryStage);
    }
}
