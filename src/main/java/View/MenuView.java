package View;

import Controller.DatabaseController;
import Controller.SoundController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @created May 20 2021 - 6:47 PM
 * @project testGame
 */

public class MenuView {
    Stage primaryStage;
    final String pathToImage = "src/main/media/PandemicMenuBackground.jpg";
    final double width = 1280;
    final double height = 960;

    public MenuView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadStageWithBorderPane(createMainMenuBorderPane());
    }


    private BorderPane createMainMenuBorderPane()
    {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Setup BorderPane Center (vboxCenter) //
        Button startButton = new Button("Start");
        Button optionsButton = new Button("Options");
        Button quitButton = new Button("Quit");

        ArrayList<Button> buttonsArrayList = new ArrayList<Button>();
        Collections.addAll(buttonsArrayList, startButton, optionsButton, quitButton);

        for (Button button : buttonsArrayList)
        {
            button.setPrefHeight(80);
            button.setPrefWidth(550);
            button.setFont(new Font("Castellar", 50));
            button.setStyle("-fx-background-color: #ff5c6c; -fx-background-radius: 30px;");
            button.setOpacity(0.8f);
            button.setEffect(new DropShadow());
            button.setTextFill(Color.BLACK);
            button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setStyle("-fx-background-color: Firebrick; -fx-background-radius: 30px;");
                }
            });
            button.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setStyle("-fx-background-color: #ff5c6c; -fx-background-radius: 30px;");
                }
            });
        }

        VBox vboxCenter = new VBox();
        vboxCenter.setAlignment(Pos.CENTER);
        vboxCenter.setSpacing(80);
        vboxCenter.getChildren().addAll(startButton, optionsButton, quitButton);

        buttonsArrayList.get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StartLobbyView view = new StartLobbyView(primaryStage);
            }
        });

        buttonsArrayList.get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OptionsView view = new OptionsView(primaryStage);
            }
        });

        buttonsArrayList.get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        // BorderPane layout //
        bp.setCenter(vboxCenter);
        return bp;
    }

    private void loadStageWithBorderPane(BorderPane bp)
    {
        try
        {
            Scene mainMenu = new Scene(bp, width, height);
            this.primaryStage.setScene(mainMenu);
            this.primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
