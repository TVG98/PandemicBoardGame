package Observers;

public interface PlayerObservable {
    void register(PlayerObserver observer);
    void unregister(PlayerObserver observer);
    void notifyAllObservers();
    String getPlayerName();
    boolean getReadyToStart();
}
