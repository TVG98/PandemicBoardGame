package Observers;

import Model.City;

public interface PlayerObservable {
    void register(PlayerObserver observer);
    void notifyAllObservers();
    String getRoleAsString();
    City getCurrentCity();
    String getPlayerName();
    int getActions();
}
