package Observers;

import Model.Player;

import java.util.List;
/**
 * @author : Tom van Gogh
 */
public interface GameObservable {
    void register(GameObserver observer);
    void notifyAllObservers();
    List<Player> getPlayers();
    int getCurrentPlayerIndex();
    boolean getWon();
    boolean getLost();
}
