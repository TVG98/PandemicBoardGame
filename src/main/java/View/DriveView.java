package View;

import Controller.GameController;
import Model.City;
import Model.GameSounds;
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
 * The view of the drive action.
 * @author Romano Biertantie, Willem Bakker
 */
public class DriveView implements GameObserver {
    Stage primaryStage;
    final String pathToImage = "src/main/media/GameBoardResized.jpg";
    final double width = 1600;
    final double height = 900;
    Text selectedCityText = new Text("You currently have no city selected");
    String selectedCity = "None";
    ArrayList<Button> cityButtons;
    Text statusText = new Text("error huh");

    GameController gameController = GameController.getInstance();

    public DriveView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //this.primaryStage.setResizable(true);
        loadStageWithBorderPane(createDriveViewBorderPane());
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
        Text actionTitle = new Text("Drive / Ferry");
        actionTitle.setFill(Color.WHITE);
        actionTitle.setFont(Font.font("Castellar", 80));

        statusText.setFill(Color.WHITE);
        statusText.setFont(Font.font("Arial", 30));
        Text infoText = new Text("You are able to move to the cities connected to your current city");
        selectedCityText.setFill(Color.WHITE);
        selectedCityText.setFont(Font.font("Arial", 30));
        infoText.setFill(Color.WHITE);
        infoText.setFont(Font.font("Arial", 20));
        infoText.setTextAlignment(TextAlignment.CENTER);

        ArrayList<Text> texts = new ArrayList<>();
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
     * sets all City buttons, amount of buttons depends on amount of near cities.
     *
     * @author Willem Bakker
     */
    private void getCitiesButtons(ArrayList<String> nearCities)
    {
        int index = 0;
        //ArrayList<Button> buttons = new ArrayList<Button>();

        for (String cityName : nearCities) {
            //Button button = new Button(cityName);\
            Button button = cityButtons.get(index);
            button.setText(cityName);

            button.setOnAction(e -> getCitiesButtonHandler(button));
            //buttons.add(button);
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

    private void moveButtonHandler() {
        gameController.playSoundEffect(Sound.CARDRIVING);
        gameController.handleDrive(selectedCity);
        new GameView(primaryStage);
    }
    /**
     *
     * @author Willem Bakker
     */
    private void getCitiesButtonHandler(Button button) {
        gameController.playSoundEffect(Sound.BUTTON);
        selectedCityText.setText("You selected: " + button.getText());
        selectedCity = button.getText();
    }

    /**
     * Creates a new border pane with appropriate text and buttons with the near Cities.
     *
     * @author Willem Bakker
     */
    private void createUpdatedBorderPane(GameObservable observable) {
        statusText.setText("You are currently in: " + observable.getPlayers().get(observable.getCurrentPlayerIndex() % 4).getCurrentCity().getName());
        ArrayList<String> nearCities = observable.getPlayers().get(observable.getCurrentPlayerIndex() % 4).getCurrentCity().getNearCities();
        getCitiesButtons(nearCities);
    }

    /**
     *
     * @author Willem Bakker
     */
    @Override
    public void update(GameObservable observable) {
        createUpdatedBorderPane(observable);
    }
}
