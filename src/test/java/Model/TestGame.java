package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author : Thimo van Velzen
 */
public class TestGame {

    private Game game;
    private List<Player> players;

    private final City atlanta = new City("Atlanta", VirusType.BLUE);
    private final City newYork = new City("New York", VirusType.BLUE);

    private final Player playerOne = new Player("Thimo", true);
    private final Player playerTwo = new Player("Romano", true);
    private final Player playerThree = new Player("Tom", true);
    private final Player playerFour = new Player("Daniel", true);

    @Before
    public void setup() {
        players = new ArrayList<>();
        setPlayersToAtlanta();
        assignPlayersToPlayerArrayList();
        game = new Game(players);
    }

    private void assignPlayersToPlayerArrayList() {
        players.add(playerOne);
        players.add(playerTwo);
        players.add(playerThree);
        players.add(playerFour);
    }

    private void setPlayersToAtlanta() {
        playerOne.setCurrentCity(atlanta);
        playerTwo.setCurrentCity(atlanta);
        playerThree.setCurrentCity(atlanta);
        playerFour.setCurrentCity(atlanta);
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_SayCurrentPlayerTurnIsPlayerOne() {
        String playerNameOne = "Thimo";

        String currentPlayerName = game.getCurrentPlayer().getPlayerName();

        assertThat(currentPlayerName, is(playerNameOne));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_SayCurrentPlayerTurnIsPlayerThree() {
        String playerNameThree = "Tom";

        game.nextTurn();
        game.nextTurn();
        String currentPlayerName = game.getCurrentPlayer().getPlayerName();

        assertThat(currentPlayerName, is(playerNameThree));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_SayCurrentPlayerTurnIsPlayerTwoAfterFiveTurns() {
        String playerNameTwo = "Romano";

        for (int i = 0; i < 5; i++) {
            game.nextTurn();
        }
        String currentPlayerName = game.getCurrentPlayer().getPlayerName();

        assertThat(currentPlayerName, is(playerNameTwo));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_SayWonIsFalseAfterLostIsSetTrue() {
        game.setLost();

        boolean gameWon = game.getWon();

        assertThat(gameWon, is(false));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_SayLostIsFalseAfterWonIsSetTrue() {
        game.setWon();

        boolean gameLost = game.getLost();

        assertThat(gameLost, is(false));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_SayAllPlayersStartInAtlanta() {
        ArrayList<Player> players = game.getPlayersInCity(atlanta);

        int expectedPlayerSize = 4;
        int playerSize = players.size();

        assertThat(playerSize, is(expectedPlayerSize));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_SayTwoPlayersAreInNewYork() {
        List<Player> allPlayers = game.getPlayers();
        int expectedPlayerSize = 2;
        allPlayers.get(0).setCurrentCity(newYork);
        allPlayers.get(2).setCurrentCity(newYork);

        ArrayList<Player> players = game.getPlayersInCity(newYork);
        int playerSize = players.size();

        assertThat(playerSize, is(expectedPlayerSize));
    }
}
