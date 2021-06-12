package Controller.Behavior;

import Controller.GameBoardController;
import Controller.PlayerController;
import Exceptions.CityNotFoundException;
import Model.CityCard;
import Model.Player;
import Model.VirusType;

public class DirectFlightBehavior {

    GameBoardController gameBoardController = GameBoardController.getInstance();
    PlayerController playerController = PlayerController.getInstance();

    public void directFlight(Player currentPlayer) {
        //Todo: Laat de speler een kaart uit zijn hand kiezen
        try {
            CityCard chosenCard = new CityCard(gameBoardController.getCity("Tokyo"), VirusType.RED);  // Hier komt het resultaat
            playerController.setCurrentCity(currentPlayer, chosenCard.getCity());
        } catch (CityNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
}
