package Observers;

public interface GameBoardObservable {
    void register(GameBoardObserver gameBoardObserver);
    void unregisterAllObservers();
    void notifyAllObservers();
}
