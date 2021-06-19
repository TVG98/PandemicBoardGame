package Controller.Behavior;

import Model.City;
import Model.Player;
import Model.PlayerCard;
import Model.Role;

import java.util.ArrayList;

public class ShareKnowledgeOnAnyCityBehavior implements ShareKnowledgeBehavior {

    @Override
    public void shareKnowledge(Player currentPlayer, Player chosenPlayer, boolean giveCard) {

        if(giveCard) {
            currentPlayer.setCardsToShare(currentPlayer.getHand());
        } else {
            chosenPlayer.setCardsToShare(chosenPlayer.getHand());
        }

        playerController.decrementActions(currentPlayer);
    }
}
