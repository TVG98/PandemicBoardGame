package View;

import Controller.DatabaseController;
import Controller.LobbyController;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

/**
 * @created May 25 2021 - 12:20 PM
 * @project testGame
 */

public class StartLobbyView {
    Stage primaryStage;
    final String pathToImage = "src/main/media/LobbyBackground.jpg";
    final double width = 1280;
    final double height = 960;
    LobbyController lobbyController = LobbyController.getInstance();


    public StartLobbyView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadStageWithBorderPane(createStartLobbyGridPane());
    }

    public BorderPane createStartLobbyGridPane() {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Setup Background Shapes (inputTextBackground) //
        Rectangle inputTextBackground = new Rectangle(750, 150);
        inputTextBackground.setX((width/2) - (750/2f));
        inputTextBackground.setY(175);
        inputTextBackground.setFill(Color.color(0.3f, 0.3, 0.3, 0.95f));
        inputTextBackground.setArcWidth(30);
        inputTextBackground.setArcHeight(30);

        // Setup Borderpane Top (vboxTop) //
        Text name = new Text("Enter your name: ");
        name.setFont(new Font("Arial", 50));
        name.setFill(Color.WHITE);
        TextField inputName  = new TextField();
        inputName.setPromptText("Your name");

        HBox hboxInputName = new HBox();
        hboxInputName.getChildren().addAll(name, inputName);
        hboxInputName.setAlignment(Pos.CENTER);

        Text code = new Text("Enter your code: ");
        code.setFont(new Font("Arial", 50));
        code.setFill(Color.WHITE);
        TextField inputCode = new TextField();
        inputCode.setPromptText("Your code");

        HBox hboxInputCode = new HBox();
        hboxInputCode.getChildren().addAll(code, inputCode);
        hboxInputCode.setAlignment(Pos.CENTER);

        VBox vboxInputs = new VBox();
        vboxInputs.getChildren().addAll(hboxInputName, hboxInputCode);

        VBox vboxTop = new VBox();

        Text lobbyText = new Text("Lobby");
        lobbyText.setFont(new Font("Castellar", 80));
        vboxTop.getChildren().addAll(lobbyText, vboxInputs);
        vboxTop.setAlignment(Pos.CENTER);
        vboxTop.setSpacing(20);
        vboxTop.setSpacing(100);

        // Setup BorderPane Center (hboxCenter) //

        Button createButton = new Button("Create");
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lobbyController.makeLobby(inputName.getText());
                InLobbyView view = new InLobbyView(primaryStage);
            }
        });
        Button joinButton = new Button("Join");
        joinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lobbyController.addPlayerToLobby(inputCode.getText(), inputName.getText());
                InLobbyView view = new InLobbyView(primaryStage);
            }
        });

        ArrayList<Button> buttonsArrayList = new ArrayList<Button>();
        Collections.addAll(buttonsArrayList, createButton, joinButton);

        for (Button button : buttonsArrayList)
        {
            button.setOpacity(0.95f);
            button.setStyle("-fx-background-color: Grey; -fx-background-radius: 40px;");
            button.setTextFill(Color.RED);
            button.setFont(new Font("Arial", 50));
            button.setPrefHeight(200);
            button.setPrefWidth(300);
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: Dimgray; -fx-background-radius: 40px;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: Grey; -fx-background-radius: 40px;"));
        }

        HBox hboxCenter = new HBox();
        hboxCenter.getChildren().addAll(buttonsArrayList);
        hboxCenter.setAlignment(Pos.CENTER);
        hboxCenter.setSpacing(200);


        // Setup BorderPane Bottom (vboxBottom) //

        Button backToMainMenuButton = new Button("Back to main menu");
        backToMainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //lobbyController.makeLobby(inputName.getText());
                MenuView view = new MenuView(primaryStage);
            }
        });

        backToMainMenuButton.setStyle("-fx-background-color: Grey; -fx-background-radius: 30px;");
        backToMainMenuButton.setTextFill(Color.RED);
        backToMainMenuButton.setOnMouseEntered(e -> backToMainMenuButton.setStyle("-fx-background-color: Dimgray; -fx-background-radius: 30px;"));
        backToMainMenuButton.setOnMouseExited(e -> backToMainMenuButton.setStyle("-fx-background-color: Grey; -fx-background-radius: 30px;"));
        backToMainMenuButton.setFont(new Font("Arial", 50));
        backToMainMenuButton.setPrefHeight(100);
        backToMainMenuButton.setPrefWidth(600);

        VBox vboxBottom = new VBox();
        vboxBottom.getChildren().add(backToMainMenuButton);
        vboxBottom.setAlignment(Pos.CENTER);

        // BorderPane Layout, order of elements placed is IMPORTANT for layering //
        bp.getChildren().addAll(inputTextBackground);
        bp.setTop(vboxTop);
        bp.setCenter(hboxCenter);
        bp.setBottom(vboxBottom);

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
