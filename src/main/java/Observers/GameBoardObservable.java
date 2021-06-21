package Observers;

import Model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Lets you retrieve all the relevant information for the view from the GameBoard.
 * @author Tom van Gogh
 */
public interface GameBoardObservable {
    List<City> getCities();
    List<Cure> getCures();
    List<Virus> getViruses();
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
