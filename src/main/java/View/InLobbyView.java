package View;

import Controller.LobbyController;
import Exceptions.PlayerNotFoundException;
import Observers.*;
import javafx.application.Platform;
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

public class InLobbyView implements LobbyObserver {
    Stage primaryStage;
    final String pathToImage = "src/main/media/LobbyBackground.jpg";
    final double width = 1280;
    final double height = 960;
    LobbyController lobbyController = LobbyController.getInstance();
    ArrayList<Text> playerNames = new ArrayList<Text>();
    ArrayList<Text> playerStatuses = new ArrayList<Text>();
    Text lobbyCode = new Text("xxxxxxxx");

    public InLobbyView(Stage primaryStage) {
        lobbyController.registerObserver(this);

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
        Rectangle lobbyCodeBackground = new Rectangle(300, 100);
        lobbyCodeBackground.setFill(Color.color(0f, 0f, 0f, 0.7f));
        lobbyCodeBackground.setX((width / 2 - 150));
        lobbyCodeBackground.setY((height - 100));

        // BorderPane Top Setup (vboxTop) //
        Text lobbyText = new Text("Lobby");
        lobbyText.setFont(Font.font("Castellar", 80));
        VBox vboxTop = new VBox();
        vboxTop.setAlignment(Pos.CENTER);
        vboxTop.getChildren().add(lobbyText);

        // BorderPane Center Setup (hboxCenter) //

        Text playerOneName = new Text("-");
        Text playerTwoName = new Text("-");
        Text playerThreeName = new Text("-");
        Text playerFourName = new Text("-");

        Collections.addAll(this.playerNames, playerOneName, playerTwoName, playerThreeName, playerFourName);

        for (Text playerName : playerNames) {
            playerName.setTextAlignment(TextAlignment.LEFT);
            playerName.setFont(Font.font("Arial", 40));
            playerName.setFill(Color.WHITE);
        }

        VBox vboxPlayerNames = new VBox();
        vboxPlayerNames.getChildren().addAll(playerNames);
        vboxPlayerNames.setAlignment(Pos.CENTER);
        vboxPlayerNames.setSpacing(100);

        Text playerOneStatus = new Text("-");
        Text playerTwoStatus = new Text("-");
        Text playerThreeStatus = new Text("-");
        Text playerFourStatus = new Text("-");

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
        backToMainMenuButton.setOnAction(event -> {
            try {
                lobbyController.removePlayerFromServer(lobbyController.getCurrentPlayer());
                MenuView view = new MenuView(primaryStage);
            } catch (PlayerNotFoundException e) {
                e.printStackTrace();
            }
        });

        Button readyUpButton = new Button("Ready Up");
        readyUpButton.setOnAction(event -> lobbyController.setPlayerReady());

        backToMainMenuButton.setStyle("-fx-background-color: Gray");
        backToMainMenuButton.setTextFill(Color.BLACK);
        backToMainMenuButton.setOnMouseEntered(e -> backToMainMenuButton.setStyle("-fx-background-color: Dimgray"));
        backToMainMenuButton.setOnMouseExited(e -> backToMainMenuButton.setStyle("-fx-background-color: Gray"));
        backToMainMenuButton.setFont(new Font("Arial", 25));
        backToMainMenuButton.setPrefHeight(100);
        backToMainMenuButton.setPrefWidth(400);

        readyUpButton.setStyle("-fx-background-color: Gray");
        readyUpButton.setOnAction(e -> readyUpButton.setStyle("-fx-background-color: Dimgray"));
        readyUpButton.setFont(new Font("Arial", 25));
        readyUpButton.setTextFill(Color.BLACK);
        readyUpButton.setPrefHeight(100);
        readyUpButton.setPrefWidth(400);
        lobbyCode.setFont(Font.font("Arial", 35));
        lobbyCode.setFill(Color.WHITE);
        lobbyCode.setTextAlignment(TextAlignment.CENTER);

        HBox hboxBottom = new HBox();
        hboxBottom.getChildren().addAll(backToMainMenuButton, lobbyCode, readyUpButton);
        hboxBottom.setAlignment(Pos.BOTTOM_CENTER);
        hboxBottom.setSpacing(200);

        // BorderPane layout //
        bp.getChildren().addAll(lobbyWindowBackground, lobbyCodeBackground);
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

    private void createUpdatedInLobbyBorderPane(LobbyObservable lobbyObservable)
    {
        lobbyCode.setText("Lobby code:\n" + lobbyObservable.getPassword());
        ArrayList<String> players = lobbyObservable.getPlayerNames();
        int index = 0;
        for (String player : players) {
            this.playerNames.get(index).setText(player);
            index++;
        }
//        for (int i = index; 4 > i; i++) {
//            if (index < 4) {
//                this.playerNames.get(index).setText("-");
//                index++;
//            }
//        }

        ArrayList<Boolean> statuses = lobbyObservable.getPlayerReadyToStart();
        index = 0;
        for (Boolean status : statuses) {
            if (status == null) {
                this.playerStatuses.get(index).setText("-");
                this.playerStatuses.get(index).setFill(Color.WHITE);
            } else if (status) {
                this.playerStatuses.get(index).setText("Ready");
                this.playerStatuses.get(index).setFill(Color.GREEN);
                this.playerNames.get(index).setFill(Color.GREEN);
            } else {
                this.playerStatuses.get(index).setText("Not Ready");
                this.playerStatuses.get(index).setFill(Color.RED);
                this.playerNames.get(index).setFill(Color.RED);
            }

            index++;
        }
        for (int i = 0; 4 > i; i++) {
            if (index < 4) {
                this.playerStatuses.get(index).setText("-");
                index++;
            }
        }
    }

    @Override
    public void update(LobbyObservable observable) {
        createUpdatedInLobbyBorderPane(observable);
        checkIfAllPlayersReady(observable);
    }

    private void checkIfAllPlayersReady(LobbyObservable observable) {
        ArrayList<Boolean> playersReady = observable.getPlayerReadyToStart();
        if (allItemsInArraylistTrue(playersReady)) {
            Platform.runLater(() -> {
                GameView view = new GameView(primaryStage);
            });
        }
    }

    private boolean allItemsInArraylistTrue(ArrayList<Boolean> arrList) {
        int counter = 0;
        int amountOfPlayers = 0;

        for (Boolean arrItem : arrList) {
            if (arrItem != null) {
                amountOfPlayers++;
                if (arrItem) {
                    counter++;
                }
            }
        }
        if (amountOfPlayers == counter && amountOfPlayers >= 2) {
            return true;
        }
        return false;
    }
}
