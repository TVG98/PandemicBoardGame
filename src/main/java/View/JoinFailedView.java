package View;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class JoinFailedView {
    Stage primaryStage;
    final String pathToImage = "src/main/media/LobbyBackground.jpg";
    final double width = 1280;
    final double height = 960;
    String reasonForFailure;

    public JoinFailedView(Stage primaryStage, String reasonForFailure) {
        this.primaryStage = primaryStage;
        this.reasonForFailure = reasonForFailure;
        loadStageWithBorderPane(createJoinFailedBorderPane());
    }

    private void loadStageWithBorderPane(BorderPane bp) {
        try {
            Scene scene = new Scene(bp, width, height);
            primaryStage.show();
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BorderPane createJoinFailedBorderPane() {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Background Shapes Setup //
        Rectangle backgroundDrop = new Rectangle(width, height);
        backgroundDrop.setFill(Color.color(0, 0, 0));
        backgroundDrop.setOpacity(0.60);
        Rectangle menuBackground = new Rectangle(800, 700);
        menuBackground.setFill(Color.color(0f, 0f, 0f, 0.7f));
        menuBackground.setX((width / 2) - (800 / 2f));
        menuBackground.setY((height / 2) - (700 / 2f));

        // BorderPane Center Setup (vboxCenter) //

        Text joinFailedText = new Text("Join failed");
        joinFailedText.setFont(Font.font("Castellar", 80));
        joinFailedText.setFill(Color.RED);

        Text reasonText = new Text("Reason for failed join:");
        reasonText.setFont(Font.font("Arial", 40));
        reasonText.setFill(Color.WHITE);

        Text reasonForFailureText = new Text(reasonForFailure);
        reasonForFailureText.setFont(Font.font("Arial", 30));
        reasonForFailureText.setFill(Color.WHITE);

        Button backButton = new Button("Back");
        backButton.setAlignment(Pos.CENTER);
        backButton.setOpacity(0.70);
        backButton.setStyle("-fx-background-color: Firebrick;");
        backButton.setPrefHeight(80);
        backButton.setPrefWidth(300);
        backButton.setFont(new Font("Castellar", 40));
        backButton.setTextFill(Color.BLACK);
        backButton.setEffect(new DropShadow());
        backButton.setOnMouseEntered(event -> backButton.setStyle("-fx-background-color: Red;"));
        backButton.setOnMouseExited(event -> backButton.setStyle("-fx-background-color: Firebrick;"));
        backButton.setOnAction(e -> backButtonHandler());

        VBox vboxCenter = new VBox();
        vboxCenter.getChildren().addAll(joinFailedText, reasonText, reasonForFailureText, backButton);
        vboxCenter.setSpacing(100);
        vboxCenter.setAlignment(Pos.CENTER);

        // Setup BorderPane Layout //
        bp.getChildren().addAll(backgroundDrop, menuBackground);
        bp.setCenter(vboxCenter);

        return bp;
    }

    private void backButtonHandler() {
        StartLobbyView view = new StartLobbyView(primaryStage);
    }
}
