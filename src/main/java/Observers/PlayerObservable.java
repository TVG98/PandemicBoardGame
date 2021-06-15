package Observers;

public interface PlayerObservable {
    void register(PlayerObserver observer);
    void notifyAllObservers();
}
