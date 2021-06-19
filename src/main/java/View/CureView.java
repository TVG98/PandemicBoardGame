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
 * @created June 13 2021 - 8:19 PM
 * @project testGame
 */

public class CureView {
    Stage primaryStage;
    final String pathToImage = "src/main/media/GameBoardResized.jpg";
    final double width = 1600;
    final double height = 900;
    Text cityHasResearchStationText = new Text("Your current city has a research station");
    String selectedVirusToCure = "None";
    Text selectedVirusToCureText = new Text("You selected no virus to find a cure for");
    ArrayList<String> selectedCities = new ArrayList<String>();
    Text selectedCityText = new Text("You haven't selected any city card to discard");

    public CureView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //this.primaryStage.setResizable(true);
        loadStageWithBorderPane(createCureViewBorderPane());
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
        Text statusText = new Text("You are currently in: " + "Washington");
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
        for (Button virusButton : virusButtons)
        {
            virusButton.setTextFill(Color.WHITE);
            virusButton.setPrefHeight(80);
            virusButton.setPrefWidth(200);
            virusButton.setFont(Font.font("Arial", 20));
        }

        HBox hboxVirusesButtons = new HBox();
        hboxVirusesButtons.setAlignment(Pos.CENTER);
        hboxVirusesButtons.setSpacing(30);
        hboxVirusesButtons.getChildren().addAll(virusButtons);

        ArrayList<Button> cityButtons = getPlayerCityCards();
        for (Button cityButton : cityButtons)
        {
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

        for (Button cityButton : cityButtons)
        {
            if (index < 3)
            {
                hboxCityRowOne.getChildren().add(cityButton);
            }
            else
            {
                hboxCityRowTwo.getChildren().add(cityButton);
            }
            index++;
        }

        ArrayList<HBox> cityRows = new ArrayList<HBox>();
        Collections.addAll(cityRows, hboxCityRowOne, hboxCityRowTwo);

        for (HBox hboxCityRow : cityRows)
        {
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

    private ArrayList<Button> getVirusButtons()
    {
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


    private ArrayList<Button> getPlayerCityCards()
    {
        // TODO: Moet alle city cards van een speler ophalen
        ArrayList<Button> buttons = new ArrayList<Button>();

        Button b1 = new Button("Ho Chi Minh");
        b1.setOnAction(e -> getPlayerCityCardsButtonHandler(b1));

        Button b2 = new Button("Jakarta");
        b2.setOnAction(e -> getPlayerCityCardsButtonHandler(b2));

        Button b3 = new Button("St. Petersburg");
        b3.setOnAction(e -> getPlayerCityCardsButtonHandler(b3));

        Button b4 = new Button("Chennai");
        b4.setOnAction(e -> getPlayerCityCardsButtonHandler(b4));

        Button b5 = new Button("Istanbul");
        b5.setOnAction(e -> getPlayerCityCardsButtonHandler(b5));

        Button b6 = new Button("Johannesburg");
        b6.setOnAction(e -> getPlayerCityCardsButtonHandler(b6));
        Collections.addAll(buttons, b1, b2, b3, b4, b5, b6);
        return buttons;
    }


    private void backButtonHandler() {
        GameView view = new GameView(primaryStage);
    }

    private void cureButtonHandler() {
        // TODO: behaviour implementeren
        GameView view = new GameView(primaryStage);
    }

    private void resetButtonHandler() {
        selectedCities.clear();
        selectedCityText.setText("You haven't selected any city card to discard");
    }

    private void getPlayerCityCardsButtonHandler(Button button) {
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

    private void getVirusButtonHandler(Button button)
    {
        selectedVirusToCure = button.getText();
        selectedVirusToCureText.setText("You selected the " + selectedVirusToCure + " virus to find a cure for");
    }
}
