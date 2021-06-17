package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class TestLobby {

    private Lobby lobby;

    @Before
    public void setup() {
        lobby = new Lobby("1234");
    }

    @Test
    public void Should_ReturnFourWhenAskedMaxLobbySize() {
        int expectedMaxLobbySize = 4;
        int maxLobbySize = lobby.getMaxLobbySize();

        assertThat(maxLobbySize, is(expectedMaxLobbySize));
    }

    @Test
    public void Should_ReturnTrueIfGivenLobbyCodeWas1234() {
        boolean matchingCode = lobby.checkLobbyCode("1234");

        assertThat(matchingCode, is(true));
    }

    @Test
    public void Should_ReturnTrueIfGivenLobbyCodeWas123() {
        boolean matchingCode = lobby.checkLobbyCode("123");

        assertThat(matchingCode, is(false));
    }

    @Test
    public void Should_ReturnCorrectNamesWhenAskedAllNamesFromLobby() {
        ArrayList<String> expectedPlayerNames = new ArrayList<>(Arrays.asList("Thimo", "", "Tom", ""));

        lobby.updatePlayer(0, new Player("Thimo", true));
        lobby.updatePlayer(2, new Player("Tom", true));
        ArrayList<String> playerNames = lobby.getPlayerNames();

        assertThat(playerNames, is(expectedPlayerNames));
    }

    @Test
    public void Should_ReturnCorrectNamesWhenAskedAllPlayersThatAreReadyToStart() {
        ArrayList<Boolean> expectedBooleans = new ArrayList<>(Arrays.asList(true, false, false, true));

        lobby.updatePlayer(0, new Player("Thimo", true));
        lobby.updatePlayer(1, new Player("Tom", false));
        lobby.updatePlayer(2, new Player("Romano", false));
        lobby.updatePlayer(3, new Player("Daniel", true));

        ArrayList<Boolean> booleans = lobby.getPlayerReadyToStart();

        assertThat(booleans, is(expectedBooleans));
    }
}
