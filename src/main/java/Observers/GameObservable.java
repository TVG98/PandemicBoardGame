package Observers;

import Model.Player;

import java.util.List;

public interface GameObservable {
    void register(GameObserver observer);
    void notifyAllObservers();
    List<Player> getPlayers();
    int getCurrentPlayerIndex();
    boolean getWon();
    boolean getLost();
}
