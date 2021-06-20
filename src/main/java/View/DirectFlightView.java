package View;

import Controller.GameController;
import Model.CityCard;
import Model.Sound;
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

/**
 * @created June 11 2021 - 1:34 PM
 * @project testGame
 */
public class DirectFlightView implements GameObserver {
    Stage primaryStage;
    final String pathToImage = "src/main/media/GameBoardResized.jpg";
    final double width = 1600;
    final double height = 900;
    Text selectedCityText = new Text("You currently have no city selected");
    String selectedCity = "None";
    ArrayList<Button> cityButtons;
    Text statusText = new Text("error?? look away :(");

    GameController gameController = GameController.getInstance();




    public DirectFlightView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //this.primaryStage.setResizable(true);
        loadStageWithBorderPane(createDirectFlightViewBorderPane());

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

    private BorderPane createDirectFlightViewBorderPane()
    {
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
        Text actionTitle = new Text("Direct Flight");
        actionTitle.setFill(Color.WHITE);
        actionTitle.setFont(Font.font("Castellar", 80));
        //Text statusText = new Text("You are currently in: " + gameController.getCurrentPlayer().getCurrentCity().getName());
        statusText.setFill(Color.WHITE);
        statusText.setFont(Font.font("Arial", 30));
        Text infoText = new Text(
                "By handing over a city card from your hand you can move to the matching city\n" +
                "Click on the city that you want to move to and then press 'Move'");
        selectedCityText.setFill(Color.WHITE);
        selectedCityText.setFont(Font.font("Arial", 30));
        infoText.setFill(Color.WHITE);
        infoText.setFont(Font.font("Arial", 20));
        infoText.setTextAlignment(TextAlignment.CENTER);

        ArrayList<Text> texts = new ArrayList<Text>();
        Collections.addAll(texts, actionTitle, statusText, infoText, selectedCityText);

        VBox vboxTexts = new VBox();
        vboxTexts.getChildren().addAll(texts);
        vboxTexts.setAlignment(Pos.CENTER);
        vboxTexts.setSpacing(20);

        ArrayList<Button> buttons = new ArrayList<>();
        for (int i=0; i<7; i++) {
            buttons.add(new Button());
        }

        cityButtons = buttons;
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
        vboxCenter.getChildren().addAll(vboxTexts, vboxCityRows, hboxBottomButtons);
        vboxCenter.setSpacing(100);
        vboxCenter.setAlignment(Pos.CENTER);

        // BorderPane Layout //
        bp.getChildren().addAll(backgroundDrop, menuBackground);
        bp.setCenter(vboxCenter);


        return bp;
    }

    private void getCitiesButtons(ArrayList<String> cityCardsInHandNames)
    {

        int index = 0;
        for (String cityName : cityCardsInHandNames) {
            Button button = cityButtons.get(index);
            button.setText(cityName);
            button.setOnAction(e -> getCityButtonHandler(button));
            index++;
        }

        for (int i = index; i < cityButtons.size(); i++) {
            cityButtons.get(i).setPrefHeight(0);
            cityButtons.get(i).setPrefWidth(0);
            cityButtons.get(i).setStyle("-fx-background-color:transparent");
        }


        // : Moet alle kaarten van een speler ophalen om zo te bepalen waar de speler naartoe kan bewegen
        /*ArrayList<Button> buttons = new ArrayList<Button>();

        Button b1 = new Button("Ho Chi Minh");
        b1.setOnAction(e -> getCityButtonHandler(b1));

        Button b2 = new Button("Jakarta");
        b2.setOnAction(e -> getCityButtonHandler(b2)
        );
        Button b3 = new Button("St. Petersburg");
        b3.setOnAction(e -> getCityButtonHandler(b3));

        Button b4 = new Button("Chennai");
        b4.setOnAction(e -> getCityButtonHandler(b4));

        Button b5 = new Button("Istanbul");
        b5.setOnAction(e -> getCityButtonHandler(b5));

        Button b6 = new Button("Johannesburg");
        b6.setOnAction(e -> getCityButtonHandler(b6));

        Collections.addAll(buttons, b1, b2, b3, b4, b5, b6);
        return buttons;*/
    }

    private void backButtonHandler() {
        gameController.playSoundEffect(Sound.BUTTON);
        GameView view = new GameView(primaryStage);
    }

    private void moveButtonHandler() {
        gameController.playSoundEffect(Sound.AIRPLANE);
        gameController.handleDirectFlight(selectedCity);
        GameView view = new GameView(primaryStage);
    }

    private void getCityButtonHandler(Button button) {
        gameController.playSoundEffect(Sound.BUTTON);
        selectedCityText.setText("You selected: " + button.getText());
        selectedCity = button.getText();
    }

    private void createUpdatedBorderPane(GameObservable gameObservable) {
        statusText.setText("You are currently in: " + gameObservable.getPlayers().get(gameObservable.getCurrentPlayerIndex() % 4).getCurrentCity().getName());

        ArrayList<String> cityCardNames = gameObservable.getPlayers().get(gameObservable.getCurrentPlayerIndex() % 4).getCityCardNames();
        //ArrayList<String> cityCardsInHandNames = new ArrayList<>();

        getCitiesButtons(cityCardNames);
    }

    @Override
    public void update(GameObservable observable) {
        createUpdatedBorderPane(observable);
    }

}
