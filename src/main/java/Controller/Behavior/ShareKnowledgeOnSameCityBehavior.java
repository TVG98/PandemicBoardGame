package Controller.Behavior;

import Model.Player;

public class ShareKnowledgeOnSameCityBehavior implements ShareKnowledgeBehavior {

    public void shareKnowledge(Player currentPlayer, Player chosenPlayer) {
//        if (playersInCity.size() > 1) {
//            //game.getCurrentPlayer()
//        }

        playerController.decrementActions(currentPlayer);
    }
}
