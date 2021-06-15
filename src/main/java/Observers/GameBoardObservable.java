package Observers;

import Model.*;

import java.util.ArrayList;

public interface GameBoardObservable {
    City[] getCities();
    Cure[] getCures();
    Virus[] getViruses();
    ArrayList<InfectionCard> getInfectionStack();
    ArrayList<InfectionCard> getInfectionDiscardStack();
    ArrayList<PlayerCard> getPlayerStack();
    ArrayList<PlayerCard> getPlayerDiscardStack();
    int getOutbreakCounter();
    int getInfectionRate();
    int getDrawnEpidemicCards();
    ArrayList<City> getCitiesWithResearchStations();
    void register(GameBoardObserver gameBoardObserver);
    void unregisterAllObservers();
    void notifyAllObservers();
}
