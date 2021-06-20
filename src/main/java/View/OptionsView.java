package View;

import Controller.SoundController;
import Model.Sound;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
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
import java.util.Arrays;
import java.util.Collections;

public class OptionsView {

    Stage primaryStage;
    final String pathToImage = "src/main/media/PandemicMenuBackground.jpg";
    final double width = 1280;
    final double height = 960;
    SoundController soundController = SoundController.getInstance();

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
        masterVolumeTextBox.setY(190);
        masterVolumeTextBox.setX(140);
        Rectangle SFXVolumeTextBox = new Rectangle(400, 100);
        SFXVolumeTextBox.setY(375);
        SFXVolumeTextBox.setX(140);
        Rectangle musicVolumeTextBox = new Rectangle(400, 100);
        musicVolumeTextBox.setY(560);
        musicVolumeTextBox.setX(140);
        ArrayList<Rectangle> textBoxes = new ArrayList<Rectangle>();
        Collections.addAll(textBoxes, masterVolumeTextBox, SFXVolumeTextBox, musicVolumeTextBox);

        for (Rectangle textBox : textBoxes) {
            textBox.setArcHeight(30d);
            textBox.setArcWidth(30d);
            textBox.setOpacity(0.7f);
            textBox.setFill(Color.valueOf("#ff5c6c"));
            textBox.setEffect(new DropShadow());
        }

        // Setup Center BorderPane (hboxCenter) //
        Slider masterVolumeSlider = new Slider();
        masterSliderHandler(masterVolumeSlider);
        Slider SFXVolumeSlider = new Slider();
        SFXSliderHandler(SFXVolumeSlider);
        Slider musicVolumeSlider = new Slider();
        musicSliderHandler(musicVolumeSlider);
        ArrayList<Slider> volumeSliders = new ArrayList<Slider>();
        Collections.addAll(volumeSliders, masterVolumeSlider, SFXVolumeSlider, musicVolumeSlider);

        for (int i = 0; i < volumeSliders.size(); i++) {
            volumeSliders.get(i).setMin(0);
            volumeSliders.get(i).setMax(100);
            volumeSliders.get(i).setValue(soundController.getVolumes()[i]);
            volumeSliders.get(i).setShowTickLabels(true);
            volumeSliders.get(i).setShowTickMarks(true);
            volumeSliders.get(i).setMajorTickUnit(25);
            volumeSliders.get(i).setMinorTickCount(5);
        }

        VBox vboxSliders = new VBox();
        vboxSliders.getChildren().addAll(volumeSliders);
        vboxSliders.setAlignment(Pos.CENTER);
        vboxSliders.setSpacing(150);
        vboxSliders.setPrefWidth(400);

        Text masterVolumeText = new Text("Master volume");
        Text SFXVolumeText = new Text("SFX volume");
        Text musicVolumeText = new Text("Music volume");
        ArrayList<Text> volumeText = new ArrayList<Text>();
        Collections.addAll(volumeText, masterVolumeText, SFXVolumeText, musicVolumeText);

        for (Text text : volumeText) {
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
        backToMainMenuButton.setOnAction(event -> backToMainMenuButtonHandler());

        backToMainMenuButton.setStyle("-fx-background-color: #ff5c6c; -fx-background-radius: 30px;");
        backToMainMenuButton.setTextFill(Color.BLACK);
        backToMainMenuButton.setOnMouseEntered(event -> backToMainMenuButton.setStyle("-fx-background-color: firebrick; -fx-background-radius: 30px;"));
        backToMainMenuButton.setOnMouseExited(event -> backToMainMenuButton.setStyle("-fx-background-color: #ff5c6c; -fx-background-radius: 30px;"));
        backToMainMenuButton.setFont(new Font("Arial", 50));
        backToMainMenuButton.setOpacity(0.95f);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void backToMainMenuButtonHandler() {
        soundController.playSound(Sound.BUTTON);
        MenuView view = new MenuView(primaryStage);
    }

    private void masterSliderHandler(Slider musicSlider) {
        musicSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                soundController.setMasterVolume(newValue.floatValue());
                soundController.playSound(Sound.SLIDER);
            }
        });
    }

    private void SFXSliderHandler(Slider musicSlider) {
        musicSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                soundController.setSfxVolume(newValue.floatValue());
                soundController.playSound(Sound.SLIDER);
            }
        });
    }

    private void musicSliderHandler(Slider musicSlider) {
        musicSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                soundController.setMusicVolume(newValue.floatValue());
                soundController.playSound(Sound.SLIDER);
            }
        });
    }
}
