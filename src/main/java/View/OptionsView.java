package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
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
 * @created May 25 2021 - 11:10 AM
 * @project testGame
 */

public class OptionsView
{
    Stage primaryStage;
    final String pathToImage = "src/main/media/PandemicMenuBackground.jpg";
    final double width = 1280;
    final double height = 960;

    public OptionsView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadStageWithBorderPane(createOptionsMenuBorderPane());
    }

    private BorderPane createOptionsMenuBorderPane() {
        BorderPane bp = new BorderPane();

        // Setup Background Image //
        Image image = new Image(new File(pathToImage).toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(bgImage));

        // Setup Backgound Shapes (textBoxes) //
        Rectangle masterVolumeTextBox = new Rectangle(400, 100);
        masterVolumeTextBox.setY(100);
        masterVolumeTextBox.setX(140);
        Rectangle SFXVolumeTextBox = new Rectangle(400, 100);
        SFXVolumeTextBox.setY(285);
        SFXVolumeTextBox.setX(140);
        Rectangle musicVolumeTextBox = new Rectangle(400, 100);
        musicVolumeTextBox.setY(470);
        musicVolumeTextBox.setX(140);
        Rectangle UIVolumeTextBox = new Rectangle(400, 100);
        UIVolumeTextBox.setY(655);
        UIVolumeTextBox.setX(140);
        ArrayList<Rectangle> textBoxes = new ArrayList<Rectangle>();
        Collections.addAll(textBoxes, masterVolumeTextBox, SFXVolumeTextBox, musicVolumeTextBox, UIVolumeTextBox);
        for (Rectangle textBox : textBoxes)
        {
            textBox.setFill(Color.valueOf("#ff5c6c"));
            textBox.setEffect(new DropShadow());
        }

        // Setup Center BorderPane (hboxCenter) //
        Slider masterVolumeSlider = new Slider();
        Slider SFXVolumeSlider = new Slider();
        Slider musicVolumeSlider = new Slider();
        Slider UIVolumeSlider = new Slider();
        ArrayList<Slider> volumeSliders = new ArrayList<Slider>();
        Collections.addAll(volumeSliders, masterVolumeSlider, SFXVolumeSlider, musicVolumeSlider, UIVolumeSlider);

        for (Slider slider : volumeSliders)
        {
            slider.setMin(0);
            slider.setMax(100);
            slider.setValue(50);
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            slider.setMajorTickUnit(25);
            slider.setMinorTickCount(5);
        }

        VBox vboxSliders = new VBox();
        vboxSliders.getChildren().addAll(volumeSliders);
        vboxSliders.setAlignment(Pos.CENTER);
        vboxSliders.setSpacing(150);
        vboxSliders.setPrefWidth(400);

        Text masterVolumeText = new Text("Master volume");
        Text SFXVolumeText = new Text("SFX volume");
        Text musicVolumeText = new Text("Music volume");
        Text UIVolumeText = new Text("UI volume");
        ArrayList<Text> volumeText = new ArrayList<Text>();
        Collections.addAll(volumeText, masterVolumeText, SFXVolumeText, musicVolumeText, UIVolumeText);

        for (Text text : volumeText)
        {
            text.setFont(new Font("Arial", 50));
        }

        VBox vboxText = new VBox();
        vboxText.getChildren().addAll(volumeText);
        vboxText.setAlignment(Pos.CENTER);
        vboxText.setSpacing(130);

        HBox hboxCenter = new HBox();

        hboxCenter.getChildren().addAll(vboxText, vboxSliders);
        hboxCenter.setAlignment(Pos.CENTER);
        hboxCenter.setSpacing(200);

        // Setup Bottom BorderPane (vboxBottom) //

        Button backToMainMenuButton = new Button("Back to main menu");
        backToMainMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MenuView view = new MenuView(primaryStage);
            }
        });

        backToMainMenuButton.setStyle("-fx-background-color: #ff5c6c");
        backToMainMenuButton.setTextFill(Color.BLACK);
        backToMainMenuButton.setOnMouseEntered(e -> backToMainMenuButton.setStyle("-fx-background-color: Firebrick"));
        backToMainMenuButton.setOnMouseExited(e -> backToMainMenuButton.setStyle("-fx-background-color: #ff5c6c"));
        backToMainMenuButton.setFont(new Font("Arial", 50));
        backToMainMenuButton.setPrefHeight(100);
        backToMainMenuButton.setPrefWidth(600);

        VBox vboxBottom = new VBox();
        vboxBottom.getChildren().add(backToMainMenuButton);
        vboxBottom.setAlignment(Pos.CENTER);

        // BorderPane layout, order of elements placed is IMPORTANT for layering//
        bp.getChildren().addAll(textBoxes);
        bp.setCenter(hboxCenter);
        bp.setBottom(vboxBottom);

        return bp;
    }

    private void loadStageWithBorderPane(BorderPane bp){
        try {
            Scene mainMenu = new Scene(bp, width, height);
            this.primaryStage.setScene(mainMenu);
            this.primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
