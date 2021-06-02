package View;

import Controller.LobbyController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    //LobbyController lobbyController = new LobbyController();


    public InLobbyView(Stage primaryStage){
        this.primaryStage = primaryStage;
        loadStageWithBorderPane(createInLobbyBorderPane());
    }

    // Observer als argument meegeven zorgt voor goede initial BorderPane
    private BorderPane createInLobbyBorderPane(){
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // BorderPane Shapes Setup (lobbyWindowBackground) //
        Rectangle lobbyWindowBackground = new Rectangle(800, 700);
        lobbyWindowBackground.setFill(Color.color(0f, 0f, 0f, 0.7f));
        lobbyWindowBackground.setX((width / 2) - (800 / 2f));
        lobbyWindowBackground.setY((height / 2) - (700 / 2f));


        // BorderPane Top Setup (vboxTop) //
        Text lobbyText = new Text("Lobby");
        lobbyText.setFont(new Font("Castellar", 80));
        VBox vboxTop = new VBox();
        vboxTop.setAlignment(Pos.CENTER);
        vboxTop.getChildren().add(lobbyText);

        // BorderPane Center Setup (hboxCenter) //
        HBox headers = new HBox();
        Text header1 = new Text("Players");
        Text header2 = new Text("Character");
        Text header3 = new Text("Status");

        headers.getChildren().addAll(header1, header2, header3);
        headers.setAlignment(Pos.CENTER);

        Text playerOneName = new Text("Aad");
        Text playerOneCharacter = new Text("Medic");
        Text playerOneStatus = new Text("Ready");
        HBox playerOneData = new HBox();
        playerOneData.getChildren().addAll(playerOneName, playerOneCharacter, playerOneStatus);
        playerOneData.setAlignment(Pos.CENTER);
        playerOneData.setSpacing(200);

        Text playerTwoName = new Text("Bert");
        Text playerTwoCharacter = new Text("Scientist");
        Text playerTwoStatus = new Text("Not Ready");
        HBox playerTwoData = new HBox();
        playerTwoData.getChildren().addAll(playerTwoName, playerTwoCharacter, playerTwoStatus);
        playerTwoData.setAlignment(Pos.CENTER);
        playerTwoData.setSpacing(200);

        Text playerThreeName = new Text("Carl");
        Text playerThreeCharacter = new Text("Dispatcher");
        Text playerThreeStatus = new Text("Ready");
        HBox playerThreeData = new HBox();
        playerThreeData.getChildren().addAll(playerThreeName, playerThreeCharacter, playerThreeStatus);
        playerThreeData.setAlignment(Pos.CENTER);
        playerThreeData.setSpacing(200);

        VBox vboxCenter = new VBox();
        vboxCenter.getChildren().addAll(headers, playerOneData, playerTwoData, playerThreeData);
        vboxCenter.setAlignment(Pos.CENTER);

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

            }
        });

        backToMainMenuButton.setStyle("-fx-background-color: Gray");
        backToMainMenuButton.setTextFill(Color.BLACK);
        backToMainMenuButton.setOnMouseEntered(e -> backToMainMenuButton.setStyle("-fx-background-color: Dimgray"));
        backToMainMenuButton.setOnMouseExited(e -> backToMainMenuButton.setStyle("-fx-background-color: Gray"));
        backToMainMenuButton.setFont(new Font("Arial", 40));
        backToMainMenuButton.setPrefHeight(100);
        backToMainMenuButton.setPrefWidth(400);

        readyUpButton.setStyle("-fx-background-color: Gray");
        readyUpButton.setOnMouseClicked(e -> readyUpButton.setStyle("-fx-background-color: Dimgray"));
        readyUpButton.setFont(new Font("Arial", 40));
        readyUpButton.setTextFill(Color.BLACK);
        readyUpButton.setPrefHeight(100);
        readyUpButton.setPrefWidth(400);


        HBox hboxBottom = new HBox();
        hboxBottom.getChildren().addAll(backToMainMenuButton, readyUpButton);
        hboxBottom.setAlignment(Pos.BOTTOM_CENTER);
        hboxBottom.setSpacing(200);


        // BorderPane layout //
        bp.getChildren().add(lobbyWindowBackground);
        bp.setTop(vboxTop);
        bp.setCenter(vboxCenter);
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
