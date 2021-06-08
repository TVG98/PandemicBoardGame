package View;

import Controller.LobbyController;
import Observers.Observable;
import Observers.Observer;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/** BELANGRIJK
 * Er mist een manier om naar GameView te gaan wanneer alle spelers ready zijn
 *
 */

public class InLobbyView implements Observer {
    Stage primaryStage;
    final String pathToImage = "src/main/media/LobbyBackground.jpg";
    final double width = 1280;
    final double height = 960;
    LobbyController lobbyController = LobbyController.getInstance();


    public InLobbyView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadStageWithBorderPane(createInLobbyBorderPane());
        lobbyController.registerObserver(this);
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

        Text playerOneName = new Text("Aad");
        //Text playerOneCharacter = new Text("Medic");
        Text playerOneStatus = new Text("Ready");

        Text playerTwoName = new Text("Bartolomeüs");
        //Text playerTwoCharacter = new Text("Scientist");
        Text playerTwoStatus = new Text("Not Ready");

        Text playerThreeName = new Text("Carlos");
        //Text playerThreeCharacter = new Text("Dispatcher");
        Text playerThreeStatus = new Text("Ready");

        Text playerFourName = new Text("-");
        //Text playerFourCharacter = new Text("-");
        Text playerFourStatus = new Text("-");

        ArrayList<Text> playerNames = new ArrayList<Text>();
        Collections.addAll(playerNames, playerOneName, playerTwoName, playerThreeName, playerFourName);

        for (Text playerName : playerNames) {
            playerName.setTextAlignment(TextAlignment.LEFT);
            playerName.setFont(Font.font("Arial", 40));
            playerName.setFill(Color.WHITE);
        }

        VBox vboxPlayerNames = new VBox();
        vboxPlayerNames.getChildren().addAll(playerNames);
        vboxPlayerNames.setAlignment(Pos.CENTER);
        vboxPlayerNames.setSpacing(100);

        ArrayList<Text> playerStatuses = new ArrayList<Text>();
        Collections.addAll(playerStatuses, playerOneStatus, playerTwoStatus, playerThreeStatus, playerFourStatus);

        for (Text playerStatus : playerStatuses) {
            playerStatus.setTextAlignment(TextAlignment.LEFT);
            playerStatus.setFont(Font.font("Arial", 40));
            playerStatus.setFill(Color.WHITE);
        }

        VBox vboxPlayerStatuses = new VBox();
        vboxPlayerStatuses.getChildren().addAll(playerStatuses);
        vboxPlayerStatuses.setAlignment(Pos.CENTER);
        vboxPlayerStatuses.setSpacing(100);

        HBox hboxCenter = new HBox();
        hboxCenter.getChildren().addAll(vboxPlayerNames, vboxPlayerStatuses);
        hboxCenter.setAlignment(Pos.CENTER);
        hboxCenter.setSpacing(200);


        // BorderPane Bottom Setup (hboxBottom) //

        Button backToMainMenuButton = new Button("Back to main menu");
        backToMainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lobbyController.removePlayerFromLobby(lobbyController.getCurrentPLayer());
                MenuView view = new MenuView(primaryStage);
            }
        });

        Button readyUpButton = new Button("Ready Up");
        readyUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lobbyController.setPlayerReady();
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

    @Override
    public void update(Observable observable) {
        System.out.println("hoi");
    }
}
