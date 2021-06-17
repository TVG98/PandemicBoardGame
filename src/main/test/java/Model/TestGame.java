package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class TestGame {

    private Game game;
    private Player[] players;

    private final City atlanta = new City("Atlanta", VirusType.BLUE);
    private final City newYork = new City("New York", VirusType.BLUE);

    private final Player playerOne = new Player("Thimo", true);
    private final Player playerTwo = new Player("Romano", true);
    private final Player playerThree = new Player("Tom", true);
    private final Player playerFour = new Player("Daniel", true);

    @Before
    public void setup() {
        players = new Player[4];
        setPlayersToAtlanta();
        assignPlayersToPlayerArrayList();
        game = new Game(players);
    }

    private void assignPlayersToPlayerArrayList() {
        players[0] = playerOne;
        players[1] = playerTwo;
        players[2] = playerThree;
        players[3] = playerFour;
    }

    private void setPlayersToAtlanta() {
        playerOne.setCurrentCity(atlanta);
        playerTwo.setCurrentCity(atlanta);
        playerThree.setCurrentCity(atlanta);
        playerFour.setCurrentCity(atlanta);
    }

    @Test
    public void Should_SayThatPlayerSizeIsFour() {
        int expectedPlayerAmount = 4;

        int playerAmount = game.getPlayerAmount();

        assertThat(playerAmount, is(expectedPlayerAmount));
    }

    @Test
    public void Should_SayCurrentPlayerTurnIsPlayerOne() {
        String playerNameOne = "Thimo";

        String currentPlayerName = game.getCurrentPlayer().getPlayerName();

        assertThat(currentPlayerName, is(playerNameOne));
    }

    @Test
    public void Should_SayCurrentPlayerTurnIsPlayerThree() {
        String playerNameThree = "Tom";

        game.nextTurn();
        game.nextTurn();
        String currentPlayerName = game.getCurrentPlayer().getPlayerName();

        assertThat(currentPlayerName, is(playerNameThree));
    }

    @Test
    public void Should_SayCurrentPlayerTurnIsPlayerTwoAfterFiveTurns() {
        String playerNameTwo = "Romano";

        for (int i = 0; i < 5; i++) {
            game.nextTurn();
        }
        String currentPlayerName = game.getCurrentPlayer().getPlayerName();

        assertThat(currentPlayerName, is(playerNameTwo));
    }

    @Test
    public void Should_SayWonIsFalseAfterLostIsSetTrue() {
        game.setLost();

        boolean gameWon = game.getWon();

        assertThat(gameWon, is(false));
    }

    @Test
    public void Should_SayLostIsFalseAfterWonIsSetTrue() {
        game.setWon();

        boolean gameLost = game.getLost();

        assertThat(gameLost, is(false));
    }

    @Test
    public void Should_SayAllPlayersStartInAtlanta() {
        ArrayList<Player> players = game.getPlayersInCity(atlanta);

        int expectedPlayerSize = 4;
        int playerSize = players.size();

        assertThat(playerSize, is(expectedPlayerSize));
    }

    @Test
    public void Should_SayTwoPlayersAreInNewYork() {
        Player[] allPlayers = game.getPlayers();
        int expectedPlayerSize = 2;
        allPlayers[0].setCurrentCity(newYork);
        allPlayers[2].setCurrentCity(newYork);

        ArrayList<Player> players = game.getPlayersInCity(newYork);
        int playerSize = players.size();

        assertThat(playerSize, is(expectedPlayerSize));
    }
}