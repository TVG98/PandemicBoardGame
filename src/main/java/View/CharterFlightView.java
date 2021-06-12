package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
 * @created June 12 2021 - 2:22 PM
 * @project testGame
 */

public class CharterFlightView {
    Stage primaryStage;
    final String pathToImage = "src/main/media/GameBoardResized.jpg";
    final double width = 1600;
    final double height = 900;
    String currentCity = "Washington";

    public CharterFlightView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //this.primaryStage.setResizable(true);
        loadStageWithBorderPane(createDriveViewBorderPane());
    }

    private void loadStageWithBorderPane(BorderPane bp) {
        try {
            Scene mainMenu = new Scene(bp, width, height);
            this.primaryStage.setScene(mainMenu);
            this.primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BorderPane createDriveViewBorderPane() {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Setup Background Shapes //
        Rectangle backgroundDrop = new Rectangle(width, height);
        backgroundDrop.setFill(Color.color(0, 0, 0));
        backgroundDrop.setOpacity(0.60);
        Rectangle menuBackground = new Rectangle(1400, 800);
        menuBackground.setFill(Color.color(0f, 0f, 0f, 0.7f));
        menuBackground.setX((width / 2) - (1400 / 2f));
        menuBackground.setY((height / 2) - (800 / 2f));

        // Setup BorderPane Center //
        Text actionTitle = new Text("Charter Flight");
        actionTitle.setFill(Color.WHITE);
        actionTitle.setFont(Font.font("Castellar", 80));
        Text infoText = new Text("By discarding a city card equal to the city you are currently in, you can move to any city on the map");
        infoText.setFill(Color.WHITE);
        infoText.setFont(Font.font("Arial", 20));
        infoText.setTextAlignment(TextAlignment.CENTER);

        ArrayList<Text> texts = new ArrayList<Text>();
        Collections.addAll(texts, actionTitle, infoText);

        VBox vboxTexts = new VBox();
        vboxTexts.getChildren().addAll(texts);
        vboxTexts.setAlignment(Pos.CENTER);
        vboxTexts.setSpacing(20);

        VBox vboxCurrentCity = new VBox();
        vboxCurrentCity.setSpacing(50);
        Text currentCityText = new Text("You are currently in:\n\n" + currentCity);
        currentCityText.setFill(Color.WHITE);
        currentCityText.setFont(Font.font("Arial", 20));
        currentCityText.setTextAlignment(TextAlignment.CENTER);
        vboxCurrentCity.getChildren().addAll(currentCityText);

        VBox vboxMovement = new VBox();
        vboxMovement.setSpacing(20);
        Text movementText = new Text("Choose a city to move to");
        movementText.setFill(Color.WHITE);
        movementText.setFont(Font.font("Arial", 20));
        movementText.setTextAlignment(TextAlignment.CENTER);
        ComboBox citiesToMoveTo = new ComboBox<>();
        citiesToMoveTo.setEditable(false);
        citiesToMoveTo.setPrefSize(300, 30);

        ArrayList<String> allCityNames = getAllCityNames();

        for (String city : allCityNames)
        {
            citiesToMoveTo.getItems().add(city);

        }

        vboxMovement.getChildren().addAll(movementText, citiesToMoveTo);

        HBox hboxCharterFlightMenu = new HBox();
        hboxCharterFlightMenu.getChildren().addAll(vboxCurrentCity, vboxMovement);
        hboxCharterFlightMenu.setAlignment(Pos.CENTER);
        hboxCharterFlightMenu.setSpacing(200);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {backButtonHandler();});
        Button moveButton = new Button("Move");
        moveButton.setOnAction(e -> {moveButtonHandler();});

        ArrayList<Button> menuButtons = new ArrayList<Button>();
        Collections.addAll(menuButtons, backButton, moveButton);

        for (Button menuButton : menuButtons)
        {
            menuButton.setTextFill(Color.WHITE);
            menuButton.setPrefHeight(100);
            menuButton.setPrefWidth(200);
            menuButton.setStyle("-fx-background-color: Gray;");
            menuButton.setFont(Font.font("Arial", 20));
        }

        HBox hboxBottomButtons = new HBox();
        hboxBottomButtons.getChildren().addAll(menuButtons);
        hboxBottomButtons.setAlignment(Pos.CENTER);
        hboxBottomButtons.setSpacing(width / 2);

        VBox vboxCenter = new VBox();
        vboxCenter.getChildren().addAll(vboxTexts, hboxCharterFlightMenu, hboxBottomButtons);
        vboxCenter.setSpacing(100);
        vboxCenter.setAlignment(Pos.CENTER);

        // BorderPane Layout //
        bp.getChildren().addAll(backgroundDrop, menuBackground);
        bp.setCenter(vboxCenter);
        return bp;
    }

    private ArrayList<String> getAllCityNames()
    {
        ArrayList<String> cityNames = new ArrayList<>();
        // Blue cities
        cityNames.add("Chicago");
        cityNames.add("Montréal");
        cityNames.add("San Francisco");
        cityNames.add("New York");
        cityNames.add("Atlanta");
        cityNames.add("Washington");
        cityNames.add("London");
        cityNames.add("Madrid");
        cityNames.add("Essen");
        cityNames.add("Paris");
        cityNames.add("St. Petersburg");
        cityNames.add("Milan");
        // Green cities
        cityNames.add("Moscow");
        cityNames.add("Tehran");
        cityNames.add("Delhi");
        cityNames.add("Kolkata");
        cityNames.add("Istanbul");
        cityNames.add("Baghdad");
        cityNames.add("Karachi");
        cityNames.add("Mumbai");
        cityNames.add("Chennai");
        cityNames.add("Algiers");
        cityNames.add("Cairo");
        cityNames.add("Riyadh");
        // Red cities
        cityNames.add("Beijing");
        cityNames.add("Seoul");
        cityNames.add("Tokyo");
        cityNames.add("Shanghai");
        cityNames.add("Osaka");
        cityNames.add("Taipei");
        cityNames.add("H.K.");
        cityNames.add("Bangkok");
        cityNames.add("Ho Chi Minh City");
        cityNames.add("Manila");
        cityNames.add("Jakarta");
        cityNames.add("Sydney");
        // Yellow cities
        cityNames.add("Los Angeles");
        cityNames.add("Mexico City");
        cityNames.add("Miami");
        cityNames.add("Bogota");
        cityNames.add("Lima");
        cityNames.add("Santiago");
        cityNames.add("São Paulo");
        cityNames.add("Buenos Aires");
        cityNames.add("Lagos");
        cityNames.add("Khartoum");
        cityNames.add("Kinshasa");
        cityNames.add("Johannesburg");
        return cityNames;
    }

    private void backButtonHandler()
    {
        GameView view = new GameView(primaryStage);
    }

    private void moveButtonHandler()
    {
        // TODO: behaviour implementeren
        GameView view = new GameView(primaryStage);
    }
}

