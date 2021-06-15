package View;

import Controller.GameController;
import Model.Connection;
import Observers.GameBoardObservable;
import Observers.PlayerObservable;
import Observers.PlayerObserver;
import Observers.GameBoardObserver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class GameView implements PlayerObserver, GameBoardObserver {
    private Stage primaryStage;
    private final String pathToImage = "src/main/media/GameBoardResized.jpg";
    private final String pathToConnectedCities = "src/main/connectedCities.txt";
    private final double width = 1600;
    private final double height = 900;
    private final HashMap<String, int[]> cities = new HashMap<>();
    private final ArrayList<Connection> connectedCities = new ArrayList<>();
    private final BorderPane borderPane = new BorderPane();

    private final GameController gameController;

    public GameView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.gameController = GameController.getInstance();
        gameController.registerPlayerObserver(this);
        gameController.registerGameBoardObserver(this);
        //this.primaryStage.setResizable(true);
        createGameViewBorderPane();
        loadStageWithBorderPane(borderPane);
        gameController.notifyObservers();
    }

    private void createGameViewBorderPane() {
        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        borderPane.setBackground(new Background(bgImage));

        // Setup BorderPane Top //
        Text title = new Text("Pandemic");
        title.setFont(new Font("Castellar", 50));

        Button openMenuButton = new Button("Open Menu");
        openMenuButton.setOnAction(event -> openMenuButtonHandler());

        Button howToPlayButton = new Button("How to play");
        howToPlayButton.setOnAction(event -> howToPlayButtonHandler());

        ArrayList<Button> menuButtons = new ArrayList<>();
        Collections.addAll(menuButtons, openMenuButton, howToPlayButton);

        for (Button menuButton : menuButtons) {
            menuButton.setOpacity(0.95f);
            menuButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white");
            menuButton.setTextFill(Color.BLACK);
            menuButton.setFont(new Font("Arial", 20));
            menuButton.setPrefHeight(60);
            menuButton.setPrefWidth(150);
            menuButton.setOnMouseEntered(event -> menuButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: lightgrey;"));
            menuButton.setOnMouseExited(event -> menuButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white;"));
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

        makeGameBoard();

        // Setup BorderPane Bottom //

        // Player overview //

        Text playerText = new Text("Players");
        playerText.setFont(new Font("Castellar", 20));
        Text playerOneOverview = new Text("Aad" + " - " + "Researcher");
        Text playerTwoOverview = new Text("Bert" + " - " + "Dispatcher");
        Text playerThreeOverview = new Text("Carl" + " - " + "Medic");
        Text playerFourOverview = new Text("Dirk" + " - " + "Containment Specialist");

        ArrayList<Text> playerOverviews = new ArrayList<>();
        Collections.addAll(playerOverviews, playerOneOverview, playerTwoOverview, playerThreeOverview, playerFourOverview);

        for (Text playerOverview : playerOverviews) {
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
        driveButton.setOnAction(event -> driveButtonHandler());

        Button directFlightButton = new Button("Direct Flight");
        directFlightButton.setOnAction(event -> directFlightButtonHandler());

        Button charterFlightButton = new Button("Charter Flight");
        charterFlightButton.setOnAction(event -> charterFlightButtonHandler());

        Button shuttleFlightButton = new Button("Shuttle Flight");
        shuttleFlightButton.setOnAction(event -> shuttleFlightButtonHandler());

        ArrayList<Button> movementButtons = new ArrayList<>();
        Collections.addAll(movementButtons, driveButton, directFlightButton, charterFlightButton, shuttleFlightButton);

        for (Button movementButton : movementButtons) {
            movementButton.setOpacity(0.95f);
            movementButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white;");
            movementButton.setTextFill(Color.BLACK);
            movementButton.setFont(new Font("Arial", 15));
            movementButton.setPrefHeight(50);
            movementButton.setPrefWidth(130);
            movementButton.setOnMouseEntered(event -> movementButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: lightgrey;"));
            movementButton.setOnMouseExited(event -> movementButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white;"));
        }

        HBox hboxMovementButtons = new HBox();
        hboxMovementButtons.getChildren().addAll(movementButtons);
        hboxMovementButtons.setSpacing(5);

        VBox vboxMovement = new VBox();
        vboxMovement.setAlignment(Pos.CENTER);
        vboxMovement.setSpacing(20);
        vboxMovement.getChildren().addAll(movement, hboxMovementButtons);

        // Actions Menu //
        Text actions = new Text("Actions");
        actions.setFont(new Font("Castellar", 20));

        Button treatButton = new Button("Treat");
        treatButton.setOnAction(event -> treatButtonHandler());

        Button cureButton = new Button("Cure");
        cureButton.setOnAction(event -> cureButtonHandler());

        Button buildButton = new Button("Build");
        buildButton.setOnAction(event -> buildButtonHandler());

        Button giveShareButton = new Button("Give share");
        giveShareButton.setOnAction(event -> giveShareButtonHandler());

        Button takeShareButton = new Button("Take share");
        takeShareButton.setOnAction(event -> takeShareButtonHandler());

        ArrayList<Button> actionButtons = new ArrayList<>();
        Collections.addAll(actionButtons,treatButton, cureButton, buildButton, takeShareButton, giveShareButton);

        for (Button actionButton : actionButtons) {
            actionButton.setOpacity(0.95f);
            actionButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white");
            actionButton.setTextFill(Color.BLACK);
            actionButton.setFont(new Font("Arial", 15));
            actionButton.setPrefHeight(50);
            actionButton.setPrefWidth(130);
            actionButton.setOnMouseEntered(event -> actionButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: lightgrey;"));
            actionButton.setOnMouseExited(event -> actionButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white;"));
        }

        HBox hboxActionsButtons = new HBox();
        hboxActionsButtons.getChildren().addAll(actionButtons);
        hboxActionsButtons.setSpacing(5);

        VBox vboxActions = new VBox();
        vboxActions.getChildren().addAll(actions, hboxActionsButtons);
        vboxActions.setAlignment(Pos.CENTER);
        vboxActions.setSpacing(20);

        // Bottom Right Elements //
        Text actionsLeft = new Text("Actions left\n\t" + "2" + "/4");
        actionsLeft.setFont(new Font("Castellar", 20));
        Button endTurnButton = new Button("End turn");
        endTurnButton.setOpacity(0.95f);
        endTurnButton.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-color: white");
        endTurnButton.setTextFill(Color.BLACK);
        endTurnButton.setFont(new Font("Arial", 20));
        endTurnButton.setPrefHeight(100);
        endTurnButton.setPrefWidth(120);
        endTurnButton.setOnAction(event -> endTurnButtonHandler());
        endTurnButton.setOnMouseEntered(event -> endTurnButton.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-color: red;"));
        endTurnButton.setOnMouseExited(event -> endTurnButton.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-color: white;"));

        HBox hboxBottomBottom = new HBox();
        hboxBottomBottom.getChildren().addAll(vboxPlayers, vboxMovement, vboxActions, actionsLeft, endTurnButton);
        hboxBottomBottom.setAlignment(Pos.CENTER);
        hboxBottomBottom.setSpacing(25);

        VBox vboxBottom = new VBox();
        vboxBottom.getChildren().addAll(hboxBottomBottom);
        vboxBottom.setSpacing(10);

        vboxBottom.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        vboxBottom.setPadding(new Insets(10, 0, 0, 0));

        // BorderPane Layout //
        borderPane.setTop(hboxTop);
        borderPane.setBottom(vboxBottom);
    }

    private void openMenuButtonHandler() {
        InGameMenuView view = new InGameMenuView(primaryStage);
    }

    private void howToPlayButtonHandler() {
        GameInstructionsView view = new GameInstructionsView(primaryStage);
    }

    private void driveButtonHandler() {
        DriveView view = new DriveView(primaryStage);
    }

    private void directFlightButtonHandler() {
        DirectFlightView view = new DirectFlightView(primaryStage);
    }

    private void charterFlightButtonHandler() {
        CharterFlightView view = new CharterFlightView(primaryStage);
    }

    private void shuttleFlightButtonHandler() {
        ShuttleFlightView view = new ShuttleFlightView(primaryStage);
    }

    private void treatButtonHandler() {

    }

    private void cureButtonHandler() {
        CureView view = new CureView(primaryStage);
    }

    private void buildButtonHandler() {

    }

    private void takeShareButtonHandler(){
        // Replace this //
        ArrayList<String> placeholder = new ArrayList<String>();
        placeholder.add("Washington");
        placeholder.add("New York");
        //              //

        TakeShareView view = new TakeShareView(primaryStage, placeholder);
    }

    private void giveShareButtonHandler() {
        // Replace this //
        ArrayList<String> placeholder = new ArrayList<String>();
        placeholder.add("Washington");
        placeholder.add("Sydney");
        //              //

        GiveShareView view = new GiveShareView(primaryStage, placeholder);
    }

    private void endTurnButtonHandler() {

    }

    private void makeGameBoard() {
        placeLinesOnBoard();
        placeCitiesWithColorOnBp(makeBlueCityCoordinates(), Color.DEEPSKYBLUE, borderPane);
        placeCitiesWithColorOnBp(makeGreenCityCoordinates(), Color.GREEN, borderPane);
        placeCitiesWithColorOnBp(makeRedCityCoordinates(), Color.RED, borderPane);
        placeCitiesWithColorOnBp(makeYellowCityCoordinates(), Color.YELLOW, borderPane);
    }

    private void placeLinesOnBoard() {
        initializeCitiesWithCoords();
        addConnectedCities();
        placeAllLines();
    }

    private void initializeCitiesWithCoords() {
        cities.putAll(makeBlueCityCoordinates());
        cities.putAll(makeGreenCityCoordinates());
        cities.putAll(makeRedCityCoordinates());
        cities.putAll(makeYellowCityCoordinates());
    }

    private HashMap<String, int[]> makeBlueCityCoordinates() {
        HashMap<String, int[]> blueCityCoords = new HashMap<>();

        blueCityCoords.put("Chicago", new int[]{360, 320});
        blueCityCoords.put("Montréal", new int[]{435, 320});
        blueCityCoords.put("San Francisco", new int[]{225, 340});
        blueCityCoords.put("New York", new int[]{495, 335});
        blueCityCoords.put("Atlanta", new int[]{380, 380});
        blueCityCoords.put("Washington", new int[]{455, 380});
        blueCityCoords.put("London", new int[]{720, 280});
        blueCityCoords.put("Madrid", new int[]{710, 340});
        blueCityCoords.put("Essen", new int[]{800, 255});
        blueCityCoords.put("Paris", new int[]{790, 310});
        blueCityCoords.put("St. Petersburg", new int[]{875, 260});
        blueCityCoords.put("Milan", new int[]{840, 300});

        return blueCityCoords;
    }

    private HashMap<String, int[]> makeGreenCityCoordinates() {
        HashMap<String, int[]> greenCityCoords = new HashMap<>();

        greenCityCoords.put("Moscow", new int[]{935, 295});
        greenCityCoords.put("Tehran", new int[]{1020, 340});
        greenCityCoords.put("Delhi", new int[]{1115, 380});
        greenCityCoords.put("Kolkata", new int[]{1175, 405});
        greenCityCoords.put("Istanbul", new int[]{900, 340});
        greenCityCoords.put("Baghdad", new int[]{950, 380});
        greenCityCoords.put("Karachi", new int[]{1040, 400});
        greenCityCoords.put("Mumbai", new int[]{1070, 440});
        greenCityCoords.put("Chennai", new int[]{1130, 500});
        greenCityCoords.put("Algiers", new int[]{790, 390});
        greenCityCoords.put("Cairo", new int[]{870, 400});
        greenCityCoords.put("Riyadh", new int[]{920, 450});

        return greenCityCoords;
    }

    private HashMap<String, int[]> makeRedCityCoordinates() {
        HashMap<String, int[]> redCityCoords = new HashMap<>();

        redCityCoords.put("Beijing", new int[]{1235, 325});
        redCityCoords.put("Seoul", new int[]{1300, 320});
        redCityCoords.put("Tokyo", new int[]{1365, 325});
        redCityCoords.put("Shanghai", new int[]{1260, 385});
        redCityCoords.put("Osaka", new int[]{1395, 385});
        redCityCoords.put("Taipei", new int[]{1337, 420});
        redCityCoords.put("H.K.", new int[]{1260, 430});
        redCityCoords.put("Bangkok", new int[]{1200, 470});
        redCityCoords.put("Ho Chi Minh City", new int[]{1275, 515});
        redCityCoords.put("Manila", new int[]{1375, 535});
        redCityCoords.put("Jakarta", new int[]{1230, 560});
        redCityCoords.put("Sydney", new int[]{1400, 675});

        return redCityCoords;
    }

    private HashMap<String, int[]> makeYellowCityCoordinates() {
        HashMap<String, int[]> yellowCityCoords = new HashMap<>();

        yellowCityCoords.put("Los Angeles", new int[]{255, 410});
        yellowCityCoords.put("Mexico City", new int[]{325, 450});
        yellowCityCoords.put("Miami", new int[]{440, 440});
        yellowCityCoords.put("Bogota", new int[]{430, 510});
        yellowCityCoords.put("Lima", new int[]{425, 600});
        yellowCityCoords.put("Santiago", new int[]{460, 700});
        yellowCityCoords.put("São Paulo", new int[]{590, 620});
        yellowCityCoords.put("Buenos Aires", new int[]{550, 700});
        yellowCityCoords.put("Lagos", new int[]{760, 500});
        yellowCityCoords.put("Khartoum", new int[]{900, 500});
        yellowCityCoords.put("Kinshasa", new int[]{830, 570});
        yellowCityCoords.put("Johannesburg", new int[]{900, 650});

        return yellowCityCoords;
    }

    private void addConnectedCities() {
        try {
            tryAddingConnectedCities();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tryAddingConnectedCities() throws IOException {
        BufferedReader bufferedReader = makeBufferedReader();
        addConnections(bufferedReader);
        bufferedReader.close();
    }

    private void addConnections(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        while (line != null) {
            addConnection(line);
            line = bufferedReader.readLine();
        }
    }

    private void addConnection(String line) {
        String[] con = line.split(";");
        connectedCities.add(new Connection(cities.get(con[0]), cities.get(con[1])));
    }

    private BufferedReader makeBufferedReader() throws FileNotFoundException {
        File textFile = new File(pathToConnectedCities);
        FileReader fileReader = new FileReader(textFile);

        return new BufferedReader(fileReader);
    }

    private void placeAllLines() {
        for (Connection connection : connectedCities) {
            placeLine(connection);
        }
    }

    private void placeLine(Connection connection) {
        borderPane.getChildren().addAll(connection.getLineFromConnection());
    }

    private void placeCitiesWithColorOnBp(HashMap<String, int[]> cityCoords, Color color, BorderPane bp) {
        HashMap<String, Circle> cities = new HashMap<>();

        for (Map.Entry<String, int[]> entry : cityCoords.entrySet()) {
            int[] coordinates = entry.getValue();
            int xCoord = coordinates[0];
            int yCoord = coordinates[1];

            Circle c1 = new Circle(10);
            c1.setFill(color);
            c1.setCenterX(xCoord);
            c1.setCenterY(yCoord);
            c1.setOnMouseEntered(event -> c1.setFill(color.darker()));
            c1.setOnMouseExited(event -> c1.setFill(color));

            cities.put(entry.getKey(), c1);

        }

        for (Map.Entry<String, Circle> entry : cities.entrySet()) {
            Text cityName = new Text(entry.getKey());
            cityName.setFill(color);
            cityName.setFont(Font.font("Arial", FontWeight.BOLD,18));
            cityName.setStroke(Color.BLACK);
            cityName.setStrokeWidth(0.3f);
            cityName.setX(entry.getValue().getCenterX() - 10);
            cityName.setY(entry.getValue().getCenterY() - 15);

            bp.getChildren().addAll(cityName, entry.getValue());
        }
    }

    private void loadStageWithBorderPane(BorderPane bp) {
        try {
            Scene mainMenu = new Scene(bp, width, height);
            this.primaryStage.setScene(mainMenu);
            this.primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(PlayerObservable observable) {
        System.out.println("Updating the game");
    }

    @Override
    public void update(GameBoardObservable gameBoardObservable) {
        System.out.println("updating game");
    }
}

