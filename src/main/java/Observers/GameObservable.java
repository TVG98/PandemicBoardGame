package Observers;

import Model.Player;

public interface GameObservable {
    void register(GameObserver observer);
    void notifyAllObservers();
    Player[] getPlayers();
    int getCurrentPlayerIndex();
    boolean getWon();
    boolean getLost();
    Player getCurrentPlayer();
}
