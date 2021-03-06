package Controller.Behavior;

import Controller.PlayerController;
import Model.City;
import Model.Player;

/**
 * Is implemented by the ShareKnowledgeBehaviors.
 * @author Thimo van Velzen
 */

public interface ShareKnowledgeBehavior {
    PlayerController playerController = PlayerController.getInstance();

    void shareKnowledge(Player currentPlayer, Player chosenPlayer, boolean giveCard);
}
