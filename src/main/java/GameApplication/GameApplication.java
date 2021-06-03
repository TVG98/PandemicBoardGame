package GameApplication; /**
 * @author Romano Biertantie
 * @created May 20 2021 - 6:47 PM
 * @project testGame
 */

import Model.FirestoreDatabase;
import View.GameView;
import View.MenuView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.File;

public class GameApplication extends Application {

    public static FirestoreDatabase fsDatabase = new FirestoreDatabase();

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

    public static FirestoreDatabase getFsDatabase() {
        return fsDatabase;
    }
}