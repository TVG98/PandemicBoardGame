package View;

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

public class StartLobbyView
{
    Stage primaryStage;
    final String pathToImage = "src/main/media/LobbyBackground.jpg";
    final double width = 1280;
    final double height = 960;

    public StartLobbyView(Stage primaryStage){
        this.primaryStage = primaryStage;
        loadStageWithBorderPane(createStartLobbyGridPane());
    }

    public BorderPane createStartLobbyGridPane(){
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Setup Background Shapes (inputTextBackground) //
        Rectangle inputTextBackground = new Rectangle(750, 100);
        inputTextBackground.setX((width/2) - (750/2f));
        inputTextBackground.setY(175);
        inputTextBackground.setFill(Color.GREY);

        // Setup Borderpane Top (vboxTop) //
        Text lobbyText = new Text("Lobby");
        lobbyText.setFont(new Font("Castellar", 80));
        Text name = new Text("Enter your name: ");
        name.setFont(new Font("Arial", 50));
        TextField inputName  = new TextField();
        inputName.setPromptText("Your name");

        HBox hboxInput = new HBox();
        hboxInput.getChildren().addAll(name, inputName);
        hboxInput.setAlignment(Pos.CENTER);

        VBox vboxTop = new VBox();
        vboxTop.getChildren().addAll(lobbyText, hboxInput);
        vboxTop.setAlignment(Pos.CENTER);
        vboxTop.setSpacing(100);

        // Setup BorderPane Center (hboxCenter) //

        Button createButton = new Button("Create");
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                InLobbyView view = new InLobbyView(primaryStage);
            }
        });
        Button joinButton = new Button("Join");
        joinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JoinLobbyView view = new JoinLobbyView(primaryStage);
            }
        });

        ArrayList<Button> buttonsArrayList = new ArrayList<Button>();
        Collections.addAll(buttonsArrayList, createButton, joinButton);

        for (Button button : buttonsArrayList)
        {
            button.setStyle("-fx-background-color: Grey");
            button.setTextFill(Color.RED);
            button.setFont(new Font("Arial", 50));
            button.setPrefHeight(200);
            button.setPrefWidth(300);
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: Dimgray"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: Grey"));
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
                MenuView view = new MenuView(primaryStage);
            }
        });

        backToMainMenuButton.setStyle("-fx-background-color: Grey");
        backToMainMenuButton.setTextFill(Color.RED);
        backToMainMenuButton.setOnMouseEntered(e -> backToMainMenuButton.setStyle("-fx-background-color: Dimgray"));
        backToMainMenuButton.setOnMouseExited(e -> backToMainMenuButton.setStyle("-fx-background-color: Grey"));
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
