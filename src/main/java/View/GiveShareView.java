package View;

import Controller.GameController;
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

public class GiveShareView {
    Stage primaryStage;
    final String pathToImage = "src/main/media/GameBoardResized.jpg";
    final double width = 1600;
    final double height = 900;
    ArrayList<String> availableCardsToGive;
    Text selectedCityText = new Text("You currently have no city card selected to give away");
    String selectedCity = "None";
    Text selectedPlayerText = new Text("You have no player selected");
    String selectedPlayer = "None";
    GameController gameController;

    public GiveShareView(Stage primaryStage, ArrayList<String> availableCardsToGive) {
        this.primaryStage = primaryStage;
        this.availableCardsToGive = availableCardsToGive;
        gameController = GameController.getInstance();
        loadStageWithBorderPane(createDoShareViewBorderPane());
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

    private BorderPane createDoShareViewBorderPane() {
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
        Text actionTitle = new Text("Give knowledge");
        actionTitle.setFill(Color.WHITE);
        actionTitle.setFont(Font.font("Castellar", 80));
        Text statusText = new Text("You are currently in: " + "Washington");
        statusText.setFill(Color.WHITE);
        statusText.setFont(Font.font("Arial", 20));
        Text infoText = new Text("Select a card to give to another player");
        selectedCityText.setFill(Color.WHITE);
        selectedCityText.setFont(Font.font("Arial", 20));
        selectedPlayerText.setFill(Color.WHITE);
        selectedPlayerText.setFont(Font.font("Arial", 20));
        infoText.setFill(Color.WHITE);
        infoText.setFont(Font.font("Arial", 20));
        infoText.setTextAlignment(TextAlignment.CENTER);

        ArrayList<Text> texts = new ArrayList<Text>();
        Collections.addAll(texts, actionTitle, statusText, infoText, selectedCityText, selectedPlayerText);

        VBox vboxTexts = new VBox();
        vboxTexts.getChildren().addAll(texts);
        vboxTexts.setAlignment(Pos.CENTER);
        vboxTexts.setSpacing(10);

        ArrayList<Button> playerButtons = getPlayerButtons();
        for (Button playerButton : playerButtons)
        {
            playerButton.setTextFill(Color.WHITE);
            playerButton.setPrefHeight(50);
            playerButton.setPrefWidth(200);
            playerButton.setStyle("-fx-background-color: Gray;");
            playerButton.setFont(Font.font("Arial", 20));
        }

        HBox hboxPlayers = new HBox();
        hboxPlayers.setAlignment(Pos.CENTER);
        hboxPlayers.getChildren().addAll(playerButtons);
        hboxPlayers.setSpacing(30);

        ArrayList<Button> cityButtons = getAvailableCityCardsButtons();
        for (Button cityButton : cityButtons)
        {
            cityButton.setTextFill(Color.WHITE);
            cityButton.setPrefHeight(50);
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
        Button giveCardButton = new Button("Give card");
        giveCardButton.setOnAction(e -> {giveCardButtonHandler();});

        ArrayList<Button> menuButtons = new ArrayList<Button>();
        Collections.addAll(menuButtons, backButton, giveCardButton);

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
        vboxCenter.getChildren().addAll(vboxTexts, hboxPlayers, vboxCityRows, hboxBottomButtons);
        vboxCenter.setSpacing(20);
        vboxCenter.setAlignment(Pos.CENTER);

        // BorderPane Layout //
        bp.getChildren().addAll(backgroundDrop, menuBackground);
        bp.setCenter(vboxCenter);

        return bp;
    }

    private ArrayList<Button> getAvailableCityCardsButtons()
    {
        ArrayList<Button> buttons = new ArrayList<Button>();

        for (String availableCard : availableCardsToGive) {
            Button button = new Button(availableCard);
            button.setOnAction(e -> getCitiesButtonHandler(button));
            buttons.add(button);
        }

        return buttons;
    }


    private ArrayList<Button> getPlayerButtons()
    {
        // TODO: get initial player
        ArrayList<Button> buttons = new ArrayList<Button>();
        Button b1 = new Button("playerName1");
        b1.setOnAction(e -> getPlayerButtonHandler(b1));

        Button b2 = new Button("playerName2");
        b2.setOnAction(e -> getPlayerButtonHandler(b2));

        Button b3 = new Button("playerName3");
        b3.setOnAction(e -> getPlayerButtonHandler(b3));

        Collections.addAll(buttons, b1, b2, b3);
        return buttons;
    }

    private void backButtonHandler() {
        GameView view = new GameView(primaryStage);
    }

    private void giveCardButtonHandler() {
        // TODO: behaviour implementeren, selectedcity waarde uit class attribute halen
        GameView view = new GameView(primaryStage);
    }

    private void getPlayerButtonHandler(Button button)
    {
        selectedPlayerText.setText("You selected " + button.getText() + " to give a card to");
        selectedPlayer = button.getText();
        gameController.handleShareKnowledge(selectedPlayer, true);
    }

    private void getCitiesButtonHandler(Button button)
    {
        selectedCityText.setText("You selected the city card of " + button.getText() + " to give away");
        selectedCity = button.getText();
    }
}
