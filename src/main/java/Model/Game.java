package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players = new ArrayList<Player>();
    private int currentPlayerIndex;
    private boolean lost;
    private boolean won;

    public Game(ArrayList<Player> players, int currentPlayerIndex, boolean lost, boolean won) {
        this.players = players;
        this.currentPlayerIndex = currentPlayerIndex;
        this.lost = lost;
        this.won = won;
    }

    public void nextTurn() {
        //GameController.changeTurn(); ?
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public void setLost() {
        lost = true;
        won = false;
    }

    public void setWon() {
        won = true;
        lost = false;
    }

    /*public ArrayList<Player> getPlayersInCity(City city) {

    }*/
}
