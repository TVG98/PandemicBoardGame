package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;

/**
 * @created May 25 2021 - 1:27 PM
 * @project testGame
 */

/** BELANGRIJK
 * Er mist een manier om naar GameView te gaan wanneer alle spelers ready zijn
 *
 */

public class InLobbyView {
    Stage primaryStage;
    final String pathToImage = "src/main/media/LobbyBackground.jpg";
    final double width = 1280;
    final double height = 960;

    public InLobbyView(Stage primaryStage){
        this.primaryStage = primaryStage;
        loadStageWithBorderPane(createInLobbyBorderPane());
    }

    private BorderPane createInLobbyBorderPane(){
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // BorderPane Top Setup (vboxTop) //
        Text lobbyText = new Text("Lobby");
        lobbyText.setFont(new Font("Castellar", 80));
        VBox vboxTop = new VBox();
        vboxTop.setAlignment(Pos.CENTER);
        vboxTop.getChildren().add(lobbyText);


        // BorderPane Center Setup (hboxCenter) //
        VBox nameVBox = new VBox();
        Text amountOfPlayers = new Text("3" + "/4");
        VBox characterVBox = new VBox();
        VBox statusVBox = new VBox();
        HBox hboxCenter = new HBox();
        hboxCenter.getChildren().addAll(nameVBox, amountOfPlayers, characterVBox, statusVBox);

        // BorderPane Bottom Setup (hboxBottom) //

        Button backToMainMenuButton = new Button("Back to main menu");
        backToMainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MenuView view = new MenuView(primaryStage);
            }
        });

        Button readyUpButton = new Button("Ready Up");
        readyUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Mist Firebase implementatie //
                // Mist Onclick kleur veranderen //
            }
        });

        backToMainMenuButton.setStyle("-fx-background-color: Gray");
        backToMainMenuButton.setTextFill(Color.BLACK);
        backToMainMenuButton.setOnMouseEntered(e -> backToMainMenuButton.setStyle("-fx-background-color: Dimgray"));
        backToMainMenuButton.setOnMouseExited(e -> backToMainMenuButton.setStyle("-fx-background-color: Gray"));
        backToMainMenuButton.setFont(new Font("Arial", 40));
        backToMainMenuButton.setPrefHeight(100);
        backToMainMenuButton.setPrefWidth(400);

        HBox hboxBottom = new HBox();
        hboxBottom.getChildren().addAll(backToMainMenuButton, readyUpButton);

        // BorderPane layout //
        bp.setTop(vboxTop);
        bp.setCenter(hboxCenter);
        bp.setBottom(hboxBottom);

        return bp;
    }


    private void loadStageWithBorderPane(BorderPane bp) {
        try {
            Scene scene = new Scene(bp, width, height);
            primaryStage.show();
            primaryStage.setScene(scene);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
