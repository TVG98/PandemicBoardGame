package View;

import Controller.GameController;
import Model.Player;
import Model.PlayerCard;
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
import java.util.List;

/**
 * @created June 18 2021 - 11:00 AM
 * @project PandemicBoardGame
 */
public class TakeShareCardsView implements GameObserver {
    Stage primaryStage;
    final String pathToImage = "src/main/media/GameBoardResized.jpg";
    final double width = 1600;
    final double height = 900;
    ArrayList<Button> availableCardsToTake = createInitialCityCardsButtons();
    String selectedPlayer;
    Text selectedPlayerText = new Text();
    Text selectedCityText = new Text("You currently have no city card selected to take away");
    String selectedCity = "None";
    GameController gameController;

    public TakeShareCardsView(Stage primaryStage, String selectedPlayer) {
        this.primaryStage = primaryStage;
        this.selectedPlayer = selectedPlayer;
        this.selectedPlayerText.setText("You have selected " + this.selectedPlayer + " to take a card from");
        gameController = GameController.getInstance();
        loadStageWithBorderPane(createTakeShareCardsViewBorderPane());
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

    private BorderPane createTakeShareCardsViewBorderPane() {
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
        Text actionTitle = new Text("Take knowledge");
        actionTitle.setFill(Color.WHITE);
        actionTitle.setFont(Font.font("Castellar", 80));
        Text statusText = new Text("You are currently in: " + "Washington");
        statusText.setFill(Color.WHITE);
        statusText.setFont(Font.font("Arial", 20));
        selectedPlayerText.setFill(Color.WHITE);
        selectedPlayerText.setFont(Font.font("Arial", 20));
        Text infoText = new Text("Select a card to give to another player");
        selectedCityText.setFill(Color.WHITE);
        selectedCityText.setFont(Font.font("Arial", 20));
        infoText.setFill(Color.WHITE);
        infoText.setFont(Font.font("Arial", 20));
        infoText.setTextAlignment(TextAlignment.CENTER);

        ArrayList<Text> texts = new ArrayList<Text>();
        Collections.addAll(texts, actionTitle, statusText, infoText, selectedPlayerText, selectedCityText);

        VBox vboxTexts = new VBox();
        vboxTexts.getChildren().addAll(texts);
        vboxTexts.setAlignment(Pos.CENTER);
        vboxTexts.setSpacing(10);

        ArrayList<Button> cityButtons = createInitialCityCardsButtons();
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
        backButton.setOnAction(e -> backButtonHandler());
        Button takeCardButton = new Button("Take card");
        takeCardButton.setOnAction(e -> takeCardButtonHandler());

        ArrayList<Button> menuButtons = new ArrayList<Button>();
        Collections.addAll(menuButtons, backButton, takeCardButton);

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
        vboxCenter.getChildren().addAll(vboxTexts, vboxCityRows, hboxBottomButtons);
        vboxCenter.setSpacing(20);
        vboxCenter.setAlignment(Pos.CENTER);

        // BorderPane Layout //
        bp.getChildren().addAll(backgroundDrop, menuBackground);
        bp.setCenter(vboxCenter);

        return bp;
    }

    private ArrayList<Button> createInitialCityCardsButtons()
    {
        ArrayList<Button> buttons = new ArrayList<Button>();

        for (int i = 0; i < 7; i++) {
            Button button = new Button("");
            button.setOnAction(e -> getCitiesButtonHandler(button));
            buttons.add(button);
        }

        return buttons;
    }

    private void backButtonHandler() {
        GameView view = GameView.getInstance(primaryStage);
    }

    private void takeCardButtonHandler() {
        // TODO: behaviour implementeren, selectedcity waarde uit class attribute halen
        TakeSharePlayerView view = new TakeSharePlayerView(primaryStage);
    }

    private void getCitiesButtonHandler(Button button) {
        selectedCityText.setText("You selected the city card of " + button.getText() + " to take away");
        selectedCity = button.getText();
    }

    @Override
    public void update(GameObservable observable) {
        List<Player> players = observable.getPlayers();

        for (Player player : players) {
            if (player.getPlayerName().equals(this.selectedPlayer)) {
                displaySelectedPlayerCards(player.getHand());
            }
        }
    }

    private void displaySelectedPlayerCards(ArrayList<PlayerCard> playerCards) {
        int index = 0;

        for (PlayerCard playerCard : playerCards) {
            this.availableCardsToTake.get(index).setText(playerCard.getName());
            index++;
        }

        for (int i = index; index < 7; index++) {
            this.availableCardsToTake.get(index).setText("");
            this.availableCardsToTake.get(index).setStyle("-fx-background-color:transparent");
        }
    }
}
