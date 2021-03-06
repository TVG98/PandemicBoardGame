package Controller.Behavior.EventCards;

import Model.City;
import Model.VirusType;

/**
 * Implements the government grant functionality in to the game.
 * @author Daniel Paans
 */
public class GovernmentGrantBehavior implements  EventBehavior {

    public void play() {
        //Todo: Kies 1 van elke stad uit
        City chosenCity = new City("Tokyo", VirusType.RED); // Hier komt het resultaat
        gameBoardController.addResearchStationToCity(chosenCity);
    }
}
