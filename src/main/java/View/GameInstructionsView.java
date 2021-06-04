package View;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @created June 03 2021 - 2:54 PM
 * @project testGame
 */
public class GameInstructionsView
{
    Stage primaryStage;
    final String pathToBackground = "src/main/media/InstructionsBackgroundResized.jpg";
    final double width = 1600;
    final double height = 900;
    Text text = new Text("a");

    public GameInstructionsView(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        loadStageWithBorderPane(createGameInstructionsBorderPane());
    }

    private BorderPane createGameInstructionsBorderPane() {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToBackground).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Setup Background Shapes //
        Rectangle lobbyWindowBackground = new Rectangle(1000, 700);
        lobbyWindowBackground.setFill(Color.color(0f, 0f, 0f, 0.9f));
        lobbyWindowBackground.setX((width / 2) - (1000 / 2f));
        lobbyWindowBackground.setY((height / 2) - (700 / 2f));

        Button objectOfTheGameButton = new Button("Object of the game");
        objectOfTheGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                bp.setCenter(makeObjectOfTheGameWindow());
            }
        });

        Button gameplayButton = new Button("Gameplay");
        gameplayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                bp.setCenter(makeGameplayWindow());
            }
        });

        Button actionsButton = new Button("Actions");
        actionsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                bp.setCenter(makeActionsWindow());
            }
        });

        Button winButton = new Button("Win condition");
        winButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                bp.setCenter(makeWinWindow());
            }
        });

        Button defeatButton = new Button("Defeat");
        defeatButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                bp.setCenter(makeDefeatWindow());
            }
        });

        Button rolesButton = new Button("Roles");
        rolesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                bp.setCenter(makeRolesWindow());
            }
        });

        ArrayList<Button> infoButtons = new ArrayList<Button>();
        Collections.addAll(infoButtons, objectOfTheGameButton, gameplayButton, actionsButton, winButton, defeatButton, rolesButton);

        for (Button infoButton : infoButtons)
        {
            infoButton.setOpacity(0.95f);
            infoButton.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
            infoButton.setTextFill(Color.BLACK);
            infoButton.setFont(new Font("Arial", 15));
            infoButton.setPrefHeight(50);
            infoButton.setPrefWidth(200);
        }

        HBox hboxButtons = new HBox();
        hboxButtons.getChildren().addAll(infoButtons);
        hboxButtons.setAlignment(Pos.CENTER);
        hboxButtons.setSpacing(50);

        bp.getChildren().addAll(lobbyWindowBackground);
        bp.setBottom(hboxButtons);


        return bp;
    }

    private void loadStageWithBorderPane(BorderPane bp){
        try {
            Scene scene = new Scene(bp, width, height);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox makeObjectOfTheGameWindow()
    {
        Text title = new Text("Object of the game");
        title.setFont(new Font("Castellar", 50));
        title.setFill(Color.WHITE);

        Text summary = new Text("Pandemic is a cooperative game. You and your fellow players are members of a disease control team, working together to research cures and prevent additional outbreaks.\n\n" +
                "Each of you will assume a unique role within the team, with special abilities that will improve your team's chances if applied wisely. The object is to save humanity by discovering cures to four deadly diseases (Blue, Yellow, Black, and Red) that threaten to overtake the planet.\n\n" +
                "If you and your team aren't able to keep the diseases contained before finding the necessary cures, the planet will be overrun and the game will end in defeat for everyone... Do you have what it takes to save humanity?\n\n");

        summary.setFont(new Font("Arial", 25));
        summary.setFill(Color.WHITE);
        summary.setWrappingWidth(600);

        VBox vboxObjectOfTheGame = new VBox();
        vboxObjectOfTheGame.getChildren().addAll(title, summary);
        vboxObjectOfTheGame.setAlignment(Pos.CENTER);
        vboxObjectOfTheGame.setSpacing(60);
        return vboxObjectOfTheGame;
    }

    private VBox makeGameplayWindow()
    {
        Text title = new Text("Gameplay");
        title.setFont(new Font("Castellar", 50));
        title.setFill(Color.WHITE);

        Text summary = new Text(
                "Each turn, the current player must:\n\n" +
                "1. Take 4 actions\n\n" +
                "2. Draw 2 cards to add to his/her hand\n\n" +
                "3. Draw cards from the Infection Draw Pile equal to the current Infection Rate and add one cube to the pictured cities, using a cube of the same color as each card.\n\n"
        );

        summary.setFont(new Font("Arial", 25));
        summary.setFill(Color.WHITE);
        summary.setWrappingWidth(600);

        VBox vboxGameplay = new VBox();

        vboxGameplay.getChildren().addAll(title, summary);
        vboxGameplay.setAlignment(Pos.CENTER);
        vboxGameplay.setSpacing(60);
        return vboxGameplay;
    }

    private VBox makeActionsWindow()
    {
        Text title = new Text("Actions");
        title.setFont(new Font("Castellar", 50));
        title.setFill(Color.WHITE);

        Text summary = new Text(
                "A player gets 4 actions to spend on her turn. A player may select from any of the available Basic and Special actions and spend 1 action to perform it. A given action may be performed more than once during a turn, so long as 1 action is spent for each instance.\n\n" +
                "Each player's Role will grant them special abilities that are unique to that player. Players may also pass if they have nothing else to do. Unused actions may not be saved from turn to turn.\n"
        );

        summary.setFont(new Font("Arial", 25));
        summary.setFill(Color.WHITE);
        summary.setWrappingWidth(600);

        VBox vboxActions = new VBox();

        vboxActions.getChildren().addAll(title, summary);
        vboxActions.setAlignment(Pos.CENTER);
        vboxActions.setSpacing(60);

        return vboxActions;
    };

    private VBox makeWinWindow()
    {
        Text title = new Text("How to win!");
        title.setFont(new Font("Castellar", 50));
        title.setFill(Color.WHITE);

        Text summary = new Text(
                "Victory:\n\n" +
                "Players collectively win the game immediately when the cures for all four diseases (Blue, Yellow, Black, and Red) have been discovered.\n\n" +
                "Players do not need to administer cures to every infected city in order to win the game-victory is instant when any player discovers the fourth and final cure.\n"
        );

        summary.setFont(new Font("Arial", 25));
        summary.setFill(Color.WHITE);
        summary.setWrappingWidth(600);

        VBox vboxWin = new VBox();
        vboxWin.getChildren().addAll(title, summary);
        vboxWin.setAlignment(Pos.CENTER);
        vboxWin.setSpacing(30);

        return vboxWin;
    }

    private VBox makeDefeatWindow()
    {
        Text title = new Text("Defeat");
        title.setFont(new Font("Castellar", 50));
        title.setFill(Color.WHITE);

        Text summary = new Text(
                "Defeat:\n\n" +
                "The game ends immediately in defeat for all players if any of the following conditions occur:\n\n" +
                "1. A player needs to add disease cubes to the board and there aren't any left of that color in the supply.\n\n" +
                "2. The eighth outbreak occurs.\n\n" +
                "3. There are not enough cards in the Player Draw Pile when a player must draw cards."
        );

        summary.setFont(new Font("Arial", 25));
        summary.setFill(Color.WHITE);
        summary.setWrappingWidth(600);

        VBox vboxDefeat = new VBox();
        vboxDefeat.getChildren().addAll(title, summary);
        vboxDefeat.setAlignment(Pos.CENTER);
        vboxDefeat.setSpacing(30);

        return vboxDefeat;
    }

    private VBox makeRolesWindow()
    {
        Text title = new Text("Roles");
        title.setFont(new Font("Castellar", 50));
        title.setFill(Color.WHITE);

        Text dispatcherTitle = new Text("Dispatcher");

        Text dispatcherSummary = new Text(
                "- Move another player's pawn as if it were yours.\n\n" +
                "- As an action, move any pawn to a city with another pawn.\n\n" +
                "Get permission before moving another player's pawn"
        );

        Text researcherTitle = new Text("Researcher");

        Text researcherSummary = new Text(
                "- As an action, you may give (or a player can take) any City card from your hand.\n" +
                "You must both be in the same city.\n" +
                "The card does not have to match the city you are in."
        );

        Text quarantineSpecialistTitle = new Text("Quarantine Specialist");

        Text quarantineSpecialistSummary = new Text(
                "- Prevent disease cube placements (and outbreaks) in the city you are in and all cities connected to it."
        );

        Text contingencyPlannerTitle = new Text("Contingency Planner");

        Text contingencyPlannerSummary = new Text(
                "- As an action, take any discarded Event card and store it on this card\n\n" +
                "- When you play the stored Event card, remove it from the game.\n\n" +
                "Limit: 1 Event card on this card at a time, which is not part of your hand"
        );

        Text operationsExpertTitle = new Text("Operation Expert");

        Text operationsExpertSummary = new Text(
                "- As an action, build a research station in the city you are in (no city card needed)\n\n" +
                "- Once per turn as an action, move from a research station to any city by discarding any City card."
                );

        Text scientistTitle = new Text("Scientist");

        Text scientistSummary = new Text(
                "- You need only 4 cards of the same color to do the Discover a Cure action."
        );

        Text medicTitle = new Text("Medic");

        Text medicSummary = new Text(
                "- Remove all cubes of one color when doing Treat disease" +
                "- Automatically remove cubes of cured diseases from the city you are in (and prevent them from being placed there)."
        );

        ArrayList<Text> characterTitles = new ArrayList<Text>();
        Collections.addAll(characterTitles, dispatcherTitle, researcherTitle, quarantineSpecialistTitle, contingencyPlannerTitle, operationsExpertTitle, scientistTitle, medicTitle);
        for (Text characterTitle : characterTitles)
        {
            characterTitle.setFont(new Font("Castellar", 20));
            characterTitle.setWrappingWidth(200);
            characterTitle.setFill(Color.WHITE);
        }

        ArrayList<Text> characterSummaries = new ArrayList<Text>();
        Collections.addAll(characterSummaries, dispatcherSummary, researcherSummary, quarantineSpecialistSummary, contingencyPlannerSummary, operationsExpertSummary, scientistSummary, medicSummary);
        for (Text characterSummary : characterSummaries)
        {
            characterSummary.setWrappingWidth(200);
            characterSummary.setFont(new Font("Arial", 15));
            characterSummary.setFill(Color.WHITE);
        }

        VBox vboxDispatcherCard = new VBox();
        vboxDispatcherCard.getChildren().addAll(dispatcherTitle, dispatcherSummary);

        VBox vboxResearcherCard = new VBox();
        vboxResearcherCard.getChildren().addAll(researcherTitle, researcherSummary);

        VBox vboxQuarantineSpecialistCard = new VBox();
        vboxQuarantineSpecialistCard.getChildren().addAll(quarantineSpecialistTitle, quarantineSpecialistSummary);

        VBox vboxContingencyPlannerCard = new VBox();
        vboxContingencyPlannerCard.getChildren().addAll(contingencyPlannerTitle, contingencyPlannerSummary);

        VBox vboxOperationsExpertCard = new VBox();
        vboxOperationsExpertCard.getChildren().addAll(operationsExpertTitle, operationsExpertSummary);

        VBox vboxScientistCard = new VBox();
        vboxScientistCard.getChildren().addAll(scientistTitle, scientistSummary);

        VBox vboxMedicCard = new VBox();
        vboxMedicCard.getChildren().addAll(medicTitle, medicSummary);

        ArrayList<VBox> characterCardsTop = new ArrayList<VBox>();
        Collections.addAll(characterCardsTop, vboxDispatcherCard, vboxResearcherCard, vboxQuarantineSpecialistCard, vboxContingencyPlannerCard);
        for (VBox characterCard : characterCardsTop)
        {
            characterCard.setSpacing(20);
            characterCard.setPadding(new Insets(10, 10, 10 , 10));
            characterCard.setStyle(("-fx-border-color: red; -fx-border-width: 2px;"));
        }

        HBox hboxCharactersTop = new HBox();
        hboxCharactersTop.getChildren().addAll(characterCardsTop);
        hboxCharactersTop.setAlignment(Pos.CENTER);
        hboxCharactersTop.setSpacing(15);

        ArrayList<VBox> characterCardsBottom = new ArrayList<VBox>();
        Collections.addAll(characterCardsBottom, vboxOperationsExpertCard, vboxScientistCard, vboxMedicCard);

        for (VBox characterCard : characterCardsBottom)
        {
            characterCard.setPadding(new Insets(10, 10, 10 , 10));
            characterCard.setStyle(("-fx-border-color: red; -fx-border-width: 2px;"));
            characterCard.setSpacing(15);
        }

        HBox hboxCharactersBottom = new HBox();
        hboxCharactersBottom.getChildren().addAll(characterCardsBottom);
        hboxCharactersBottom.setAlignment(Pos.CENTER);
        hboxCharactersBottom.setSpacing(20);

        VBox vboxRoles = new VBox();
        vboxRoles.getChildren().addAll(title, hboxCharactersTop, hboxCharactersBottom);
        vboxRoles.setAlignment(Pos.CENTER);
        vboxRoles.setSpacing(30);

        return vboxRoles;

    }

}
