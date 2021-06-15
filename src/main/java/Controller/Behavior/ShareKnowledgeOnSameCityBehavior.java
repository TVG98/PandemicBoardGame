package Controller.Behavior;

import Model.City;
import Model.Player;

public class ShareKnowledgeOnSameCityBehavior implements ShareKnowledgeBehavior {

    public void shareKnowledge(Player currentPlayer, Player chosenPlayer, City chosenCity) {

        playerController.decrementActions(currentPlayer);
    }
}
