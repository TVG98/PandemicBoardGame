package View;

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

/**
 * @created May 27 2021 - 10:23 AM
 * @project testGame
 */

public class GameView
{
    Stage primaryStage;
    final String pathToImage = "src/main/media/GameBoardResized.jpg";
    final double width = 1600;
    final double height = 900;


    public GameView(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        //this.primaryStage.setResizable(true);
        loadStageWithBorderPane(createGameViewBorderPane());
    }

    private BorderPane createGameViewBorderPane()
    {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Setup BorderPane Top //
        Text title = new Text("Pandemic");
        title.setFont(new Font("Castellar", 50));
        Button openMenuButton = new Button("Open Menu");
        Button howToPlayButton = new Button("How to play");
        ArrayList<Button> menuButtons = new ArrayList<Button>();
        Collections.addAll(menuButtons, openMenuButton, howToPlayButton);

        for (Button menuButton: menuButtons)
        {
            menuButton.setOpacity(0.95f);
            menuButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white");;
            menuButton.setTextFill(Color.BLACK);
            menuButton.setFont(new Font("Arial", 20));
            menuButton.setPrefHeight(60);
            menuButton.setPrefWidth(150);
        }

        HBox hboxMenuButtons = new HBox();
        hboxMenuButtons.setSpacing(10);
        hboxMenuButtons.getChildren().addAll(menuButtons);

        // Top Left //
        VBox vboxTopLeft = new VBox();
        vboxTopLeft.getChildren().addAll(title, hboxMenuButtons);
        vboxTopLeft.setAlignment(Pos.TOP_LEFT);

        // Top Center //

        Text virusText = new Text("Viruses");
        virusText.setFont(new Font("Castellar", 40));
        Rectangle virus1 = new Rectangle(100, 60);
        virus1.setStyle("-fx-fill: transparent; -fx-stroke: blue; -fx-stroke-width: 5;");
        Rectangle virus2 = new Rectangle(100, 60);
        virus2.setStyle("-fx-fill: transparent; -fx-stroke: gold; -fx-stroke-width: 5;");
        Rectangle virus3 = new Rectangle(100, 60);
        virus3.setStyle("-fx-fill: transparent; -fx-stroke: green; -fx-stroke-width: 5;");
        Rectangle virus4 = new Rectangle(100, 60);
        virus4.setStyle("-fx-fill: transparent; -fx-stroke: red; -fx-stroke-width: 5;");

        HBox hboxViruses = new HBox();
        hboxViruses.getChildren().addAll(virus1, virus2, virus3, virus4);
        hboxViruses.setSpacing(20);

        VBox vboxTopCenter = new VBox();
        vboxTopCenter.getChildren().addAll(virusText, hboxViruses);
        vboxTopCenter.setAlignment(Pos.CENTER);

        // Top Right //

        Text outbreakCounter = new Text("Outbreak Counter: " + "2" + "/8");
        outbreakCounter.setFont(new Font("Castellar", 28));
        Text infectionRate = new Text("Infection Rate: 3");
        infectionRate.setFont(new Font("Castellar", 28));

        VBox vboxTopRight = new VBox();
        vboxTopRight.setAlignment(Pos.CENTER);
        vboxTopRight.getChildren().addAll(outbreakCounter, infectionRate);

        HBox hboxTop = new HBox();
        hboxTop.getChildren().addAll(vboxTopLeft, vboxTopCenter, vboxTopRight);
        hboxTop.setSpacing(200);
        hboxTop.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));

        // Setup BorderPane Center //

        // Setup BorderPane Bottom //

        // Player overview //

        Text playerText = new Text("Players");
        playerText.setFont(new Font("Castellar", 20));
        Text playerOneOverview = new Text("Aad" + " - " + "Researcher");
        Text playerTwoOverview = new Text("Bert" + " - " + "Dispatcher");
        Text playerThreeOverview = new Text("Carl" + " - " + "Medic");
        Text playerFourOverview = new Text("Dirk" + " - " + "Containment Specialist");

        ArrayList<Text> playerOverviews = new ArrayList<Text>();
        Collections.addAll(playerOverviews, playerOneOverview, playerTwoOverview, playerThreeOverview, playerFourOverview);

        for (Text playerOverview : playerOverviews)
        {
            playerOverview.setFont(new Font("Arial", 15));
        }

        VBox vboxPlayerList = new VBox();
        vboxPlayerList.getChildren().addAll(playerOverviews);


        VBox vboxPlayers = new VBox();
        vboxPlayers.setSpacing(10);
        vboxPlayers.getChildren().addAll(playerText, vboxPlayerList);

        // Movement Menu //

        Text movement = new Text("Movement");
        movement.setFont(new Font("Castellar", 20));

        Button driveButton = new Button("Drive/Ferry");
        Button directFlightButton = new Button("Direct Flight");
        Button charterFlightButton = new Button("Charter Flight");
        Button shuttleFlightButton = new Button("Shuttle Flight");

        ArrayList<Button> movementButtons = new ArrayList<Button>();
        Collections.addAll(movementButtons, driveButton, directFlightButton, charterFlightButton, shuttleFlightButton);

        for (Button movementButton : movementButtons)
        {
            movementButton.setOpacity(0.95f);
            movementButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white");;
            movementButton.setTextFill(Color.BLACK);
            movementButton.setFont(new Font("Arial", 15));
            movementButton.setPrefHeight(30);
            movementButton.setPrefWidth(120);
        }

        HBox hboxMovementButtons = new HBox();
        hboxMovementButtons.getChildren().addAll(movementButtons);
        hboxMovementButtons.setSpacing(10);

        VBox vboxMovement = new VBox();
        vboxMovement.setAlignment(Pos.CENTER);
        vboxMovement.setSpacing(5);
        vboxMovement.getChildren().addAll(movement, hboxMovementButtons);

        // Actions Menu //
        Text actions = new Text("Actions");
        actions.setFont(new Font("Castellar", 20));

        Button treatButton = new Button("Treat");
        Button cureButton = new Button("Cure");
        Button buildButton = new Button("Build");
        Button shareButton = new Button("Share");

        ArrayList<Button> actionButtons = new ArrayList<Button>();
        Collections.addAll(actionButtons,treatButton, cureButton, buildButton, shareButton);

        for (Button actionButton : actionButtons)
        {
            actionButton.setOpacity(0.95f);
            actionButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white");;
            actionButton.setTextFill(Color.BLACK);
            actionButton.setFont(new Font("Arial", 15));
            actionButton.setPrefHeight(30);
            actionButton.setPrefWidth(120);
        }


        HBox hboxActionsButtons = new HBox();
        hboxActionsButtons.getChildren().addAll(actionButtons);
        hboxActionsButtons.setSpacing(10);

        VBox vboxActions = new VBox();
        vboxActions.getChildren().addAll(actions, hboxActionsButtons);
        vboxActions.setAlignment(Pos.CENTER);
        vboxActions.setSpacing(5);

        // Bottom Right Elements //
        Text actionsLeft = new Text("Actions left\n\t" + "2" + "/4");
        actionsLeft.setFont(new Font("Castellar", 20));
        Button endTurnButton = new Button("End turn");
        endTurnButton.setOpacity(0.95f);
        endTurnButton.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-color: white");;
        endTurnButton.setTextFill(Color.BLACK);
        endTurnButton.setFont(new Font("Arial", 20));
        endTurnButton.setPrefHeight(100);
        endTurnButton.setPrefWidth(120);

        HBox hboxBottomBottom = new HBox();
        hboxBottomBottom.getChildren().addAll(vboxPlayers, vboxMovement, vboxActions, actionsLeft, endTurnButton);
        hboxBottomBottom.setAlignment(Pos.CENTER);
        hboxBottomBottom.setSpacing(25);

        VBox vboxBottom = new VBox();
        vboxBottom.getChildren().addAll(hboxBottomBottom);
        vboxBottom.setSpacing(10);

        vboxBottom.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));


        // BorderPane Layout //
        bp.setTop(hboxTop);
        bp.setBottom(vboxBottom);

        return bp;
    }

    private void loadStageWithBorderPane(BorderPane bp){
        try {
            Scene mainMenu = new Scene(bp, width, height);
            this.primaryStage.setScene(mainMenu);
            this.primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

