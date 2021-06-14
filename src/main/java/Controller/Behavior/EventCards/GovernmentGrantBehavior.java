package Controller.Behavior.EventCards;

import Controller.GameBoardController;
import Model.City;
import Model.VirusType;

public class GovernmentGrantBehavior {
    GameBoardController gameBoardController = GameBoardController.getInstance();

    public void play() {
        //Todo: Kies 1 van elke stad uit
        City chosenCity = new City("Tokyo", VirusType.RED); // Hier komt het resultaat
        gameBoardController.addResearchStationToCity(chosenCity);
    }

}
