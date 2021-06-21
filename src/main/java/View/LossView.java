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

/**
 * The view when you lost the game.
 * @author Romano Biertantie
 */

public class LossView {

    Stage primaryStage;
    final String pathToImage = "src/main/media/SituationBackground.jpg";
    final double width = 1280;
    final double height = 960;
    SoundController soundController = SoundController.getInstance();

    public LossView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        loadStageWithBorderPane(createLossBorderPane());
    }

    private BorderPane createLossBorderPane() {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Setup Background Shapes (lossWindowBackgound) //
        Rectangle lossWindowBackground = new Rectangle(400, 500, Color.GRAY);
        lossWindowBackground.setX((width/2) - (400 / 2f));
        lossWindowBackground.setY((height/2) - (500 / 2f));

        // BorderPane Center Setup (vboxCenter)//
        Text lossText = new Text("You lose");
        lossText.setFont(new Font("Arial", 50));
        lossText.setFill(Color.RED);

        // Not used because of missing Firebase implementation
        Text reasonLossText = new Text();

        Button backToMainMenuButton = new Button("Back to main menu");
        backToMainMenuButton.setPrefHeight(60);
        backToMainMenuButton.setPrefWidth(200);
        backToMainMenuButton.setFont(new Font("Arial", 20));
        backToMainMenuButton.setStyle("-fx-background-color: Red");
        backToMainMenuButton.setEffect(new DropShadow());
        backToMainMenuButton.setOnAction(event -> backToMainMenuButtonHandler());

        backToMainMenuButton.setOnMouseEntered(e -> backToMainMenuButton.setStyle("-fx-background-color: Green"));
        backToMainMenuButton.setOnMouseExited(e -> backToMainMenuButton.setStyle("-fx-background-color: Red"));

        VBox vboxCenter = new VBox();
        vboxCenter.setAlignment(Pos.CENTER);
        vboxCenter.setSpacing(150);
        vboxCenter.getChildren().addAll(lossText, backToMainMenuButton);

        // BorderPane Layout, order of elements placed is IMPORTANT for layering //
        bp.getChildren().add(lossWindowBackground);
        bp.setCenter(vboxCenter);

        return bp;
    }

    private void loadStageWithBorderPane(BorderPane bp) {
        try {
            Scene scene = new Scene(bp, width, height);
            primaryStage.show();
            primaryStage.setScene(scene);
            soundController.playSound(Sound.LOSING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void backToMainMenuButtonHandler(){
        soundController.playSound(Sound.BUTTON);
        MenuView view = new MenuView(primaryStage);
    }
}
