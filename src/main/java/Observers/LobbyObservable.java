package Observers;

public interface LobbyObservable {
    void register(LobbyObserver observer);
    void notifyAllObservers();
}
