package View;

import Controller.GameController;
import Model.City;
import Model.Sound;
import Observers.GameBoardObservable;
import Observers.GameBoardObserver;
import Observers.GameObservable;
import Observers.GameObserver;
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

public class ShuttleFlightView implements GameObserver, GameBoardObserver {
    Stage primaryStage;
    final String pathToImage = "src/main/media/GameBoardResized.jpg";
    final double width = 1600;
    final double height = 900;
    Text cityHasResearchStationText = new Text("TEMP!");
    boolean cityHasResearchStation;
    City currentCity;
    Text selectedCityText = new Text("You currently have no city selected");
    String selectedCity = "None";
    ArrayList<Button> cityButtons;
    Text statusText = new Text ("temp");


    GameController gameController = GameController.getInstance();

    public ShuttleFlightView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //this.primaryStage.setResizable(true);
        loadStageWithBorderPane(createDriveViewBorderPane());

        gameController.registerPlayerObserver(this);
        gameController.registerGameBoardObserver(this);
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
        Text actionTitle = new Text("Shuttle Flight");
        actionTitle.setFill(Color.WHITE);
        actionTitle.setFont(Font.font("Castellar", 80));
        //Text statusText = new Text("You are currently in: " + "Washington");
        statusText.setFill(Color.WHITE);
        statusText.setFont(Font.font("Arial", 30));
        Text infoText = new Text("You are able to move from a city with a research station to another city with a research station");
        infoText.setFill(Color.WHITE);
        infoText.setFont(Font.font("Arial", 20));
        infoText.setTextAlignment(TextAlignment.CENTER);

        selectedCityText.setFill(Color.WHITE);
        selectedCityText.setFont(Font.font("Arial", 20));

        cityHasResearchStationText.setFill(Color.WHITE);
        cityHasResearchStationText.setFont(Font.font("Arial", 20));

        ArrayList<Text> texts = new ArrayList<Text>();
        Collections.addAll(texts, actionTitle, statusText, infoText, cityHasResearchStationText, selectedCityText);

        VBox vboxTexts = new VBox();
        vboxTexts.getChildren().addAll(texts);
        vboxTexts.setAlignment(Pos.CENTER);
        vboxTexts.setSpacing(20);

        ArrayList<Button> buttons = new ArrayList<>();
        for (int i=0; i<7; i++) {
            buttons.add(new Button());
        }

        cityButtons = buttons;
        for (Button cityButton : cityButtons) {
            cityButton.setTextFill(Color.WHITE);
            cityButton.setPrefHeight(80);
            cityButton.setPrefWidth(200);
            cityButton.setStyle("-fx-background-color: Gray;");
            cityButton.setFont(Font.font("Arial", 20));
        }

        int index = 0;
        HBox hboxCityRowOne = new HBox();
        hboxCityRowOne.setAlignment(Pos.CENTER);
        HBox hboxCityRowTwo = new HBox();
        hboxCityRowTwo.setAlignment(Pos.CENTER);

        for (Button cityButton : cityButtons) {

            if (index < 3) {
              hboxCityRowOne.getChildren().add(cityButton);
            } else {
              hboxCityRowTwo.getChildren().add(cityButton);
            }

            index++;
        }

        ArrayList<HBox> cityRows = new ArrayList<HBox>();
        Collections.addAll(cityRows, hboxCityRowOne, hboxCityRowTwo);

        for (HBox hboxCityRow : cityRows) {
            hboxCityRow.setSpacing(30);
        }

        VBox vboxCityRows = new VBox();
        vboxCityRows.getChildren().addAll(cityRows);
        vboxCityRows.setSpacing(30);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {backButtonHandler();});
        Button moveButton = new Button("Move");
        moveButton.setOnAction(e -> {moveButtonHandler();});

        ArrayList<Button> menuButtons = new ArrayList<Button>();
        Collections.addAll(menuButtons, backButton, moveButton);

        for (Button menuButton : menuButtons) {
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
        vboxCenter.getChildren().addAll(vboxTexts, vboxCityRows, hboxBottomButtons);
        vboxCenter.setSpacing(100);
        vboxCenter.setAlignment(Pos.CENTER);

        // BorderPane Layout //
        bp.getChildren().addAll(backgroundDrop, menuBackground);
        bp.setCenter(vboxCenter);

        return bp;
    }

    /**
     * sets all City buttons, how many depending on research station cities.
     *
     * @author Willem Bakker
     */
    private void getCitiesWithResearchStationButtons(ArrayList<String> citiesWithResearchStationsNames)
    {

        int index = 0;
        for (String cityName : citiesWithResearchStationsNames) {
            Button button = cityButtons.get(index);
            button.setText(cityName);
            button.setOnAction(e -> getCitiesWithResearchStationButtonHandler(button));
            index++;
        }

        for (int i = index; i < cityButtons.size(); i++) {
            cityButtons.get(i).setPrefHeight(0);
            cityButtons.get(i).setPrefWidth(0);
            cityButtons.get(i).setStyle("-fx-background-color:transparent");
        }


    }

    private void backButtonHandler() {
        gameController.playSoundEffect(Sound.BUTTON);
        GameView view = new GameView(primaryStage);
    }

    private void moveButtonHandler() {
        gameController.playSoundEffect(Sound.BUTTON);
        gameController.handleShuttleFlight(selectedCity);
        GameView view = new GameView(primaryStage);
    }

    private void getCitiesWithResearchStationButtonHandler(Button button) {
        selectedCityText.setText("You selected: " + button.getText());
        selectedCity = button.getText();

    }

    /**
     * updates border pane with appropriate currently-in text.
     * @author Willem Bakker
     */
    private void createUpdatedBorderPane(GameObservable gameObservable) {
        currentCity = gameObservable.getPlayers().get(gameObservable.getCurrentPlayerIndex() % 4).getCurrentCity();
        statusText.setText("You are currently in: " + currentCity.getName());
    }

    /**
     * creates border pane with text indicating whether the current city has a research station.
     *
     * @author Willem Bakker
     */
    private void createUpdatedBorderPane(GameBoardObservable gameBoardObservable) {
        ArrayList<City> citiesWithResearchStations = gameBoardObservable.getCitiesWithResearchStations();
        ArrayList<String> citiesWithResearchStationsNames = new ArrayList<String>();
        for (City city : citiesWithResearchStations) {
            citiesWithResearchStationsNames.add(city.getName());
        }
        getCitiesWithResearchStationButtons(citiesWithResearchStationsNames);
        cityHasResearchStation = citiesWithResearchStations.contains(currentCity);
        if (cityHasResearchStation) {
            cityHasResearchStationText.setText("The city you are in has a research station.");
        } else {
            cityHasResearchStationText.setText("The city you are in does not have a research station!");
        }
    }

    /**
     *
     * @author Willem Bakker
     */
    @Override
    public void update(GameObservable gameObservable ) {
        createUpdatedBorderPane(gameObservable);
    }

    /**
     *
     * @author Willem Bakker
     */
    @Override
    public void update(GameBoardObservable gameBoardObservable) {
        createUpdatedBorderPane(gameBoardObservable);
    }
}

