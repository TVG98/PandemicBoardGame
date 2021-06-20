package View;

import Controller.LobbyController;
import Exceptions.LobbyNotFoundException;
import Exceptions.LobbyNotJoinableException;
import Model.Sound;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.border.StrokeBorder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class StartLobbyView {

    Stage primaryStage;
    final String pathToImage = "src/main/media/LobbyBackground.jpg";
    final double width = 1280;
    final double height = 960;
    LobbyController lobbyController = LobbyController.getInstance();
    TextField inputCode = new TextField();
    TextField inputName  = new TextField();


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
        Rectangle inputTextBackground = new Rectangle(750, 150);
        inputTextBackground.setX((width/2) - (750/2f));
        inputTextBackground.setY(125);
        inputTextBackground.setFill(Color.color(0.3f, 0.3, 0.3, 0.95f));
        inputTextBackground.setArcWidth(30);
        inputTextBackground.setArcHeight(30);

        Rectangle lobbyJoinBackground = new Rectangle(400, 350);
        lobbyJoinBackground.setFill(Color.color(0.2f, 0f, 0f, 0.7f));
        lobbyJoinBackground.setX((width * 0.25) - (400 / 2f));
        lobbyJoinBackground.setY((height / 2) - (250 / 2f));
        lobbyJoinBackground.setStrokeType(StrokeType.OUTSIDE);
        lobbyJoinBackground.setStrokeWidth(5);
        lobbyJoinBackground.setStroke(Color.DARKRED);

        Rectangle lobbyCreateBackground = new Rectangle(400, 350);
        lobbyCreateBackground.setFill(Color.color(0f, 0f, 0.2f, 0.7f));
        lobbyCreateBackground.setX((width * 0.70) - (400 / 2f));
        lobbyCreateBackground.setY((height / 2) - (250 / 2f));
        lobbyCreateBackground.setStrokeType(StrokeType.OUTSIDE);
        lobbyCreateBackground.setStrokeWidth(5);
        lobbyCreateBackground.setStroke(Color.DARKBLUE);

        Rectangle lobbyCodeBackground = new Rectangle(300, 100);
        lobbyCodeBackground.setFill(Color.color(0f, 0f, 0f, 0.7f));
        lobbyCodeBackground.setX((width / 2 - 150));
        lobbyCodeBackground.setY((height - 100));

        // Setup Borderpane Top (vboxTop) //
        Text name = new Text("Enter your name: ");
        name.setFont(new Font("Arial", 50));
        name.setFill(Color.WHITE);

        inputName.setPromptText("Your name");

        HBox hboxInputName = new HBox();
        hboxInputName.getChildren().addAll(name, inputName);
        hboxInputName.setAlignment(Pos.CENTER);

        VBox vboxInputs = new VBox();
        vboxInputs.getChildren().addAll(hboxInputName);

        VBox vboxTop = new VBox();

        Text lobbyText = new Text("Lobby");
        lobbyText.setFont(new Font("Castellar", 80));
        vboxTop.getChildren().addAll(lobbyText, vboxInputs);
        vboxTop.setAlignment(Pos.CENTER);
        vboxTop.setSpacing(20);
        vboxTop.setSpacing(70);
        vboxTop.setPadding(new Insets(0, 0, -50, 0));

        // Setup BorderPane Center (hboxCenter) //

        Text createText = new Text("Create a lobby");
        createText.setFill(Color.WHITE);
        createText.setFont(Font.font("Arial", 40));

        Button createButton = new Button("Create");
        createButton.setOnAction(event -> createButtonHandler());

        VBox vboxCreate = new VBox();
        vboxCreate.getChildren().addAll(createText, createButton);
        vboxCreate.setAlignment(Pos.CENTER);
        vboxCreate.setSpacing(80);

        Text joinText = new Text("Join a lobby");
        joinText.setFill(Color.WHITE);
        joinText.setFont(Font.font("Arial", 40));

        Text code = new Text("Enter your code: ");
        code.setFont(new Font("Arial", 30));
        code.setFill(Color.WHITE);
        inputCode.setPromptText("Your code");

        HBox hboxInputCode = new HBox();
        hboxInputCode.getChildren().addAll(code, inputCode);
        hboxInputCode.setAlignment(Pos.CENTER);

        Button joinButton = new Button("Join");
        joinButton.setOnAction(event -> joinButtonHandler());

        VBox vboxJoin = new VBox();
        vboxJoin.getChildren().addAll(joinText, hboxInputCode, joinButton);
        vboxJoin.setAlignment(Pos.CENTER);
        vboxJoin.setSpacing(25);

        ArrayList<Button> buttonsArrayList = new ArrayList<Button>();
        Collections.addAll(buttonsArrayList, createButton, joinButton);

        for (Button button : buttonsArrayList) {
            button.setOpacity(0.85f);
            button.setStyle("-fx-background-color: Grey; -fx-background-radius: 2px;");
            button.setTextFill(Color.RED);
            button.setFont(new Font("Arial", 30));
            button.setPrefHeight(100);
            button.setPrefWidth(250);
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: Dimgray;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: Grey;"));
        }

        HBox hboxCenter = new HBox();
        hboxCenter.getChildren().addAll(vboxCreate, vboxJoin);
        hboxCenter.setAlignment(Pos.CENTER);
        hboxCenter.setSpacing(250);


        // Setup BorderPane Bottom (vboxBottom) //

        Button backToMainMenuButton = new Button("Back to main menu");
        backToMainMenuButton.setOnAction(event -> {
            lobbyController.playSoundEffect(Sound.BUTTON);
            //lobbyController.makeLobby(inputName.getText());
            MenuView view = new MenuView(primaryStage);
        });

        backToMainMenuButton.setStyle("-fx-background-color: Gray; -fx-background-radius: 2px;");
        backToMainMenuButton.setOpacity(0.80f);
        backToMainMenuButton.setTextFill(Color.WHITE);
        backToMainMenuButton.setOnMouseEntered(e -> backToMainMenuButton.setStyle("-fx-background-color: Dimgray;"));
        backToMainMenuButton.setOnMouseExited(e -> backToMainMenuButton.setStyle("-fx-background-color: Gray;"));
        backToMainMenuButton.setFont(new Font("Arial", 30));
        backToMainMenuButton.setPrefHeight(100);
        backToMainMenuButton.setPrefWidth(300);

        VBox vboxBottom = new VBox();
        vboxBottom.getChildren().add(backToMainMenuButton);
        vboxBottom.setAlignment(Pos.CENTER);

        // BorderPane Layout, order of elements placed is IMPORTANT for layering //
        bp.getChildren().addAll(inputTextBackground, lobbyCreateBackground, lobbyJoinBackground, lobbyCodeBackground);
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

    private void joinButtonHandler()
    {
        try {
            lobbyController.playSoundEffect(Sound.BUTTON);
            lobbyController.addPlayerToServer(inputCode.getText(), inputName.getText());
            InLobbyView view = new InLobbyView(primaryStage);
        } catch (LobbyNotFoundException | LobbyNotJoinableException e) {
            e.printStackTrace();
            //Todo goto main menu view?
        }
    }

    private void createButtonHandler() {
        lobbyController.playSoundEffect(Sound.BUTTON);
        lobbyController.makeLobby(inputName.getText());
        InLobbyView view = new InLobbyView(primaryStage);
    }

}
