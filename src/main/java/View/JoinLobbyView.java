package View;

import Controller.DatabaseController;
import Controller.LobbyController;
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
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @created May 25 2021 - 1:13 PM
 * @project testGame
 */

/**
 * FireBase implementatie mist voor join button
 */

public class JoinLobbyView {
    Stage primaryStage;
    final String pathToImage = "src/main/media/LobbyBackground.jpg";
    final double width = 1280;
    final double height = 960;
    LobbyController lobbyController = new LobbyController();

    public JoinLobbyView(Stage primaryStage){
        this.primaryStage = primaryStage;
        loadStageWithBorderPane(createJoinLobbyBorderPane());
    }

    private BorderPane createJoinLobbyBorderPane()
    {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Setup Background shapes (joinLobbyWindow)//
        Rectangle joinLobbyWindow = new Rectangle(500, 500);
        joinLobbyWindow.setFill(Color.GRAY);
        joinLobbyWindow.setX((width / 2) - (500 / 2f));
        joinLobbyWindow.setY((height / 2) - (500 / 2f));

        // Setup Top BorderPane (vboxTop) //
        Text joinLobbyText = new Text("Join Lobby");
        joinLobbyText.setFont(new Font("Castellar", 80));

        VBox vboxTop = new VBox();
        vboxTop.setAlignment(Pos.CENTER);
        vboxTop.getChildren().add(joinLobbyText);

        // Setup Center BorderPane (vboxCenter) //
        Text enterCodeText = new Text("Enter your code");
        enterCodeText.setFill(Color.RED);
        enterCodeText.setFont(new Font("Arial", 60));
        TextField enterCodeInput = new TextField();
        enterCodeInput.setPromptText("Your code");
        enterCodeInput.setMaxWidth(300);


        VBox vboxCenter = new VBox();
        vboxCenter.setAlignment(Pos.CENTER);
        vboxCenter.setSpacing(180);
        vboxCenter.getChildren().addAll(enterCodeText, enterCodeInput);

        // Setup Bottom BorderPane (hboxBottom) //

        Button backToMainMenuButton = new Button("Back to main menu");

        backToMainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MenuView view = new MenuView(primaryStage);
            }
        });


        Button join = new Button("Join");

        join.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                lobbyController.addPlayerToLobby(enterCodeInput.getText());
                InLobbyView view = new InLobbyView(primaryStage, lobbyController);
            }
        });

        ArrayList<Button> bottomButtonsArraylist = new ArrayList<Button>();
        Collections.addAll(bottomButtonsArraylist, backToMainMenuButton, join);

        for (Button button : bottomButtonsArraylist)
        {
            button.setStyle("-fx-background-color: Gray");
            button.setTextFill(Color.BLACK);
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: Dimgray"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: Gray"));
            button.setFont(new Font("Arial", 40));
            button.setPrefHeight(100);
            button.setPrefWidth(400);
        }

        HBox hboxBottom = new HBox();
        hboxBottom.setAlignment(Pos.CENTER);
        hboxBottom.setSpacing(200);
        hboxBottom.getChildren().addAll(bottomButtonsArraylist);

        // BorderPane Layout, order of elements placed is IMPORTANT for layering //
        bp.getChildren().add(joinLobbyWindow);
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
