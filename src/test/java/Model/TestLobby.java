package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author : Thimo van Velzen
 */
public class TestLobby {

    private Lobby lobby;

    @Before
    public void setup() {
        lobby = new Lobby("1234");
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_ReturnFourWhenAskedMaxLobbySize() {
        int expectedMaxLobbySize = 4;
        int maxLobbySize = lobby.getMaxLobbySize();

        assertThat(maxLobbySize, is(expectedMaxLobbySize));
    }

    /**
     * @author : Romano Biertantie
     */
    @Test
    public void Should_ReturnTrueIfGivenLobbyCodeWas1234() {
        boolean matchingCode = lobby.checkLobbyCode("1234");

        assertThat(matchingCode, is(true));
    }

    /**
     * @author : Romano Biertantie
     */
    @Test
    public void Should_ReturnTrueIfGivenLobbyCodeWas123() {
        boolean matchingCode = lobby.checkLobbyCode("123");

        assertThat(matchingCode, is(false));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_ReturnCorrectNamesWhenAskedAllNamesFromLobby() {
        ArrayList<String> expectedPlayerNames = new ArrayList<>(Arrays.asList("Thimo", "Tom"));
        List<Player> players = new ArrayList<>();
        players.add(new Player("Thimo", true));
        players.add(null);
        players.add(new Player("Tom", true));
        players.add(null);

        lobby.setPlayers(players);

        ArrayList<String> playerNames = lobby.getPlayerNames();

        assertThat(playerNames, is(expectedPlayerNames));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_ReturnCorrectNamesWhenAskedAllPlayersThatAreReadyToStart() {
        ArrayList<Boolean> expectedBooleans = new ArrayList<>(Arrays.asList(true, false, false, true));

        List<Player> players = new ArrayList<>();
        players.add(new Player("Thimo", true));
        players.add(new Player("Tom", false));
        players.add(new Player("Romano", false));
        players.add(new Player("Daniel", true));

        lobby.setPlayers(players);
        ArrayList<Boolean> booleans = lobby.getPlayerReadyToStart();

        assertThat(booleans, is(expectedBooleans));
    }
}
