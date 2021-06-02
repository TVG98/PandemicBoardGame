/**
 * @author Romano Biertantie
 * @created May 20 2021 - 6:47 PM
 * @project testGame
 */

import Controller.DatabaseController;
import View.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import javafx.stage.Stage;

import java.io.File;

public class GameApplication extends Application {

    //private DatabaseController databaseController = new DatabaseController();

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(new File("src/main/media/PandemicIcon.png").toURI().toString()));
        primaryStage.setTitle("Pandemic: The Board Game");
        MenuView a = new MenuView(primaryStage);
        //WinView b = new WinView(primaryStage);
        //StartLobbyView c = new StartLobbyView(primaryStage);
        //OptionsView d = new OptionsView(primaryStage);
        //InLobbyView e = new InLobbyView(primaryStage);
        //JoinLobbyView f = new JoinLobbyView(primaryStage);
        //GameView g = new GameView(primaryStage);
        //LobbyServersView h = new LobbyServersView(primaryStage);
        //LossView l = new LossView(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}