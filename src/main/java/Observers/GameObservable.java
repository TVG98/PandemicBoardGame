package Observers;

public interface GameObservable {
    void register(GameObserver gameObserver);
    void notifyAllObservers();
}
