package Observers;

import Model.Player;

import java.util.List;
/**
 * Lets you retrieve all the relevant information for the view from the Game.
 * @author Tom van Gogh
 */
public interface GameObservable {
    void register(GameObserver observer);
    void notifyAllObservers();
    List<Player> getPlayers();
    int getCurrentPlayerIndex();
    boolean getWon();
    boolean getLost();
}
