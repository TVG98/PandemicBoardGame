package Controller.Behavior;

import Controller.PlayerController;
import Model.Player;

public interface ShareKnowledgeBehavior {
    PlayerController playerController = PlayerController.getInstance();

    void shareKnowledge(Player currentPlayer, Player chosenPlayer);
}
