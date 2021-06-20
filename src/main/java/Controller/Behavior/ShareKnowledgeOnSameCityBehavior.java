package Controller.Behavior;

import Exceptions.CardNotFoundException;
import Model.Player;
import Model.PlayerCard;

import java.util.ArrayList;

public class ShareKnowledgeOnSameCityBehavior implements ShareKnowledgeBehavior {

    @Override
    public void shareKnowledge(Player currentPlayer, Player chosenPlayer, boolean giveCard) {

        ArrayList<PlayerCard> cardToShare = new ArrayList<>();
        try {
            if(giveCard) {
                cardToShare.add(currentPlayer.getCardFromHandByCity(currentPlayer.getCurrentCity()));
            } else {
                cardToShare.add(chosenPlayer.getCardFromHandByCity(chosenPlayer.getCurrentCity()));
            }
        } catch (CardNotFoundException cnfe) {
            cnfe.printStackTrace();  // Deze exception moeten we nog anders afhandelen
        }

        currentPlayer.setCardsToShare(cardToShare);
        playerController.decrementActions(currentPlayer);
    }


}
