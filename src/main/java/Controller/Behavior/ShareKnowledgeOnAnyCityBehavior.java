package Controller.Behavior;

import Model.Player;

/**
 * Implements the share knowledge functionality when the player has the Researcher Role.
 * @author Thimo van Velzen, Daniel Paans
 */

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
