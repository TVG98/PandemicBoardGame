package View;

import Controller.GameController;
import Model.City;
import Model.CityCard;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @created June 13 2021 - 8:19 PM
 * @project testGame
 */

public class CureView implements GameObserver, GameBoardObserver {
    Stage primaryStage;
    final String pathToImage = "src/main/media/GameBoardResized.jpg";
    final double width = 1600;
    final double height = 900;
    Text cityHasResearchStationText = new Text("city has research station text temp. error");
    String selectedVirusToCure = "None";
    Text selectedVirusToCureText = new Text("You selected no virus to find a cure for");
    ArrayList<String> selectedCities = new ArrayList<>();
    Text selectedCityText = new Text("You haven't selected any city card to discard");

    City currentCity;
    Text statusText = new Text("temp");
    ArrayList<Button> cityButtons;
    //boolean cityHasResearchStation;
    GameController gameController = GameController.getInstance();

    public CureView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //this.primaryStage.setResizable(true);
        loadStageWithBorderPane(createCureViewBorderPane());

        gameController.registerGameBoardObserver(this);
        gameController.registerPlayerObserver(this);
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

    private BorderPane createCureViewBorderPane() {
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
        Text actionTitle = new Text("Create a cure");
        actionTitle.setFill(Color.WHITE);
        actionTitle.setFont(Font.font("Castellar", 80));
        //Text statusText = new Text("You are currently in: " + "Washington");
        statusText.setFill(Color.WHITE);
        statusText.setFont(Font.font("Arial", 30));
        Text infoText = new Text("Select the virus you want to cure and 5 citycards matching its colour");

        cityHasResearchStationText.setFill(Color.WHITE);
        cityHasResearchStationText.setFont(Font.font("Arial", 20));

        selectedVirusToCureText.setFill(Color.WHITE);
        selectedVirusToCureText.setFont(Font.font("Arial", 20));

        selectedCityText.setFill(Color.WHITE);
        selectedCityText.setFont(Font.font("Arial", 20));

        infoText.setFill(Color.WHITE);
        infoText.setFont(Font.font("Arial", 20));
        infoText.setTextAlignment(TextAlignment.CENTER);

        ArrayList<Text> texts = new ArrayList<Text>();
        Collections.addAll(texts, actionTitle, statusText, cityHasResearchStationText, infoText, selectedVirusToCureText, selectedCityText);

        VBox vboxTexts = new VBox();
        vboxTexts.getChildren().addAll(texts);
        vboxTexts.setAlignment(Pos.CENTER);
        vboxTexts.setSpacing(10);

        ArrayList<Button> virusButtons = getVirusButtons();
        for (Button virusButton : virusButtons) {
            virusButton.setTextFill(Color.WHITE);
            virusButton.setPrefHeight(80);
            virusButton.setPrefWidth(200);
            virusButton.setFont(Font.font("Arial", 20));
        }

        HBox hboxVirusesButtons = new HBox();
        hboxVirusesButtons.setAlignment(Pos.CENTER);
        hboxVirusesButtons.setSpacing(30);
        hboxVirusesButtons.getChildren().addAll(virusButtons);



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
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> {resetButtonHandler();});
        Button cureButton = new Button("Cure");
        cureButton.setOnAction(e -> {cureButtonHandler();});

        ArrayList<Button> menuButtons = new ArrayList<Button>();
        Collections.addAll(menuButtons, backButton, resetButton, cureButton);

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
        hboxBottomButtons.setSpacing(width / 3 - 300);

        VBox vboxCenter = new VBox();
        vboxCenter.getChildren().addAll(vboxTexts, hboxVirusesButtons, vboxCityRows, hboxBottomButtons);
        vboxCenter.setSpacing(50);
        vboxCenter.setAlignment(Pos.CENTER);

        // BorderPane Layout //
        bp.getChildren().addAll(backgroundDrop, menuBackground);
        bp.setCenter(vboxCenter);


        return bp;
    }

    private ArrayList<Button> getVirusButtons() {
        // TODO: Moet de status van het virus gebruiken om knop aan/uit te zetten
        ArrayList<Button> buttons = new ArrayList<Button>();

        Button b1 = new Button("Red");
        b1.setStyle("-fx-background-color: Red;");
        b1.setOnAction(e -> getVirusButtonHandler(b1));

        Button b2 = new Button("Blue");
        b2.setStyle("-fx-background-color: Blue;");
        b2.setOnAction(e -> getVirusButtonHandler(b2));

        Button b3 = new Button("Green");
        b3.setStyle("-fx-background-color: Green;");
        b3.setOnAction(e -> getVirusButtonHandler(b3));

        Button b4 = new Button("Yellow");
        b4.setStyle("-fx-background-color: Yellow;");
        b4.setOnAction(e -> getVirusButtonHandler(b4));

       Collections.addAll(buttons, b1, b2, b3, b4);
        return buttons;
    }

    /**
     * sets all City buttons, amount of buttons depends on amount of city cards in hand.
     *
     * @author Willem Bakker
     */
    private void getPlayerCityCards(ArrayList<String> cityCardsInHandNames) {
        //ArrayList<Button> cityButtons = new ArrayList<>();

        int index = 0;
        for (String cityName : cityCardsInHandNames) {
            Button button = cityButtons.get(index);
            button.setText(cityName);
            button.setOnAction(e -> getPlayerCityCardsButtonHandler(button));
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
        new GameView(primaryStage);
    }

    private void cureButtonHandler() {
        gameController.playSoundEffect(Sound.FINDCURE);
        gameController.handleFindCure();
        new GameView(primaryStage);
    }

    private void resetButtonHandler() {
        gameController.playSoundEffect(Sound.BUTTON);
        selectedCities.clear();
        selectedCityText.setText("You haven't selected any city card to discard");
    }

    private void getPlayerCityCardsButtonHandler(Button button) {
        gameController.playSoundEffect(Sound.BUTTON);
        if (selectedCities.size() < 5) {
            String allSelectedCities = "";
            for (String selectedCity : selectedCities) {
                allSelectedCities += selectedCity + ", ";
            }
            allSelectedCities += button.getText() + ", ";
            selectedCities.add(button.getText());
            selectedCityText.setText("You selected: " + allSelectedCities.substring(0, allSelectedCities.length() - 2));
        }
    }

    private void getVirusButtonHandler(Button button) {
        gameController.playSoundEffect(Sound.BUTTON);
        selectedVirusToCure = button.getText();
        selectedVirusToCureText.setText("You selected the " + selectedVirusToCure + " virus to find a cure for");
    }

    private void createUpdatedBorderPane(GameObservable gameObservable) {
        currentCity = gameObservable.getPlayers().get(gameObservable.getCurrentPlayerIndex() % 4).getCurrentCity();
        statusText.setText("You are currently in: " + currentCity.getName());

        ArrayList<CityCard> cityCardsInHand = gameObservable.getPlayers().get(gameObservable.getCurrentPlayerIndex() % 4).createCityCardsFromPlayer();
        ArrayList<String> cityCardsInHandNames = new ArrayList<>();

        for (CityCard cityCard : cityCardsInHand) {
            cityCardsInHandNames.add(cityCard.getName());
        }

        getPlayerCityCards(cityCardsInHandNames);
    }

    /**
     * creates updated border pane, with appropriate text depending on whether the current city has a research station.
     *
     * @author Willem Bakker
     */
    private void createUpdatedBorderPane(GameBoardObservable gameBoardObservable) {
        ArrayList<City> citiesWithResearchStations = gameBoardObservable.getCitiesWithResearchStations();
        if (citiesWithResearchStations.contains(currentCity)) {
            cityHasResearchStationText.setText("The city you are in has a research station.");
        } else {
            cityHasResearchStationText.setText("The city you are in does not have a research station.");
        }
    }

    /**
     *
     * @author Willem Bakker
     */
    @Override
    public void update(GameObservable gameObservable) {
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
