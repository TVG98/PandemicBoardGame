package View;

import Controller.GameController;
import Model.Player;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The first view of the share knowledge tak action.
 * @author Romano Biertantie
 */
public class TakeSharePlayerView implements GameObserver{
    Stage primaryStage;
    final String pathToImage = "src/main/media/GameBoardResized.jpg";
    final double width = 1600;
    final double height = 900;
    //ArrayList<String> availableCardsToTake;
    Text selectedPlayerText = new Text("You have no player selected");
    String selectedPlayer = "None";
    GameController gameController;
    ArrayList<Button> playerButtons = createInitialPlayerButtons();

    public TakeSharePlayerView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //this.availableCardsToTake = availableCardsToTake;
        gameController = GameController.getInstance();
        loadStageWithBorderPane(createTakeSharePlayerViewBorderPane());

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

    private BorderPane createTakeSharePlayerViewBorderPane() {
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
        Text infoText = new Text("Select a card to give to another player");
        selectedPlayerText.setFill(Color.WHITE);
        selectedPlayerText.setFont(Font.font("Arial", 20));
        infoText.setFill(Color.WHITE);
        infoText.setFont(Font.font("Arial", 20));
        infoText.setTextAlignment(TextAlignment.CENTER);

        ArrayList<Text> texts = new ArrayList<Text>();
        Collections.addAll(texts, actionTitle, statusText, infoText, selectedPlayerText);

        VBox vboxTexts = new VBox();
        vboxTexts.getChildren().addAll(texts);
        vboxTexts.setAlignment(Pos.CENTER);
        vboxTexts.setSpacing(10);

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
        vboxCenter.getChildren().addAll(vboxTexts, hboxPlayers, hboxBottomButtons);
        vboxCenter.setSpacing(20);
        vboxCenter.setAlignment(Pos.CENTER);

        // BorderPane Layout //
        bp.getChildren().addAll(backgroundDrop, menuBackground);
        bp.setCenter(vboxCenter);

        return bp;
    }



    private ArrayList<Button> createInitialPlayerButtons()
    {
        ArrayList<Button> playerButtons = new ArrayList<Button>();

        Button b1 = new Button("playerName1");
        b1.setOnAction(e -> getPlayerButtonHandler(b1));

        Button b2 = new Button("playerName2");
        b2.setOnAction(e -> getPlayerButtonHandler(b2));

        Button b3 = new Button("playerName3");
        b3.setOnAction(e -> getPlayerButtonHandler(b3));

        Button b4 = new Button("playerName3");
        b4.setOnAction(e -> getPlayerButtonHandler(b3));

        Collections.addAll(playerButtons, b1, b2, b3, b4);
        return playerButtons;
    }


    private void backButtonHandler() {
        gameController.playSoundEffect(Sound.BUTTON);
        GameView view = new GameView(primaryStage);
    }

    private void takeCardButtonHandler() {
        gameController.playSoundEffect(Sound.CARDDRAW);
        if (!selectedPlayer.equals("None")) {
            TakeShareCardsView view = new TakeShareCardsView(primaryStage, selectedPlayer);
        }
    }

    private void getPlayerButtonHandler(Button button)
    {
        gameController.playSoundEffect(Sound.BUTTON);
        selectedPlayerText.setText("You selected " + button.getText() + " to take a card from");
        selectedPlayer = button.getText();
        gameController.handleShareKnowledge(selectedPlayer, false);
    }

    @Override
    public void update(GameObservable observable) {
        List<Player> players = observable.getPlayers();

        int index = 0;

        for (Player player : players)
        {
            if (player != null)
            {
                playerButtons.get(index).setText(player.getPlayerName());
                index++;
            }
        }

        for (int i = index; i < 4; i++)
        {
            playerButtons.get(i).setText("");
            playerButtons.get(i).setStyle("-fx-background-color:transparent");
        }
    }
}
