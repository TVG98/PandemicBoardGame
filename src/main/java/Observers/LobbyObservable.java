package Observers;

import java.util.ArrayList;

public interface LobbyObservable {
    void register(LobbyObserver observer);
    void unregister(LobbyObserver observer);
    void notifyAllObservers();
    boolean getJoinable();
    ArrayList<String> getPlayerNames();
    ArrayList<Boolean> getPlayerReadyToStart();
    String getPassword();
}
