package Model;

import Exceptions.CityNotFoundException;
import Exceptions.GameLostException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author Thimo van Velzen
 */
public class TestGameBoard {

    private Gameboard gameboard;

    @Before
    public void setup() {
        gameboard = new Gameboard();
    }

    /**
     * @author Thimo van Velzen
     */
    @Test
    public void Should_SayThatPlayerStackSizeIs59WhenInitialized() {
        int expectedPlayerStackSize = 59;
        int playerStackSize = gameboard.getPlayerStack().size();

        assertThat(playerStackSize, is(expectedPlayerStackSize));
    }

    /**
     * @author Thimo van Velzen
     */
    @Test
    public void Should_DecreasePlayerStackSizeByTwoIfTwoPlayerCardsAreTakenFromDeck() throws GameLostException {
        gameboard.drawPlayerCard();
        gameboard.drawPlayerCard();

        int expectedPlayerStackSize = 57;
        int playerStackSize = gameboard.getPlayerStack().size();

        assertThat(playerStackSize, is(expectedPlayerStackSize));
    }

    /**
     * @author Thimo van Velzen
     */
    @Test
    public void Should_ThrowCityNotFoundExceptionWhenForgettingCapitalFirstLetter() {
        boolean cityNotFoundExceptionThrown = false;

        try {
            gameboard.getCity("algiers");
        } catch (CityNotFoundException cnfe) {
            cityNotFoundExceptionThrown = true;
        }

        assertThat(cityNotFoundExceptionThrown, is(true));
    }

    /**
     * @author Thimo van Velzen
     */
    @Test
    public void Should_SayThatAlgiersAndChennaiHaveBlackVirusType() {
        boolean cityNotFoundExceptionThrown = false;

        City algiers;
        City chennai;
        boolean algiersHasBlackVirusType = false;
        boolean chennaiHasBlackVirusType = false;

        try {
            algiers = gameboard.getCity("Algiers");
            chennai = gameboard.getCity("Chennai");
            algiersHasBlackVirusType = algiers.getVIRUS_TYPE().equals(VirusType.BLACK);
            chennaiHasBlackVirusType = chennai.getVIRUS_TYPE().equals(VirusType.BLACK);
        } catch (CityNotFoundException cnfe) {
            cityNotFoundExceptionThrown = true;
        }

        boolean citiesHaveBlackVirusType = !cityNotFoundExceptionThrown &&
                algiersHasBlackVirusType &&
                chennaiHasBlackVirusType;

        assertThat(citiesHaveBlackVirusType, is(true));
    }

    /**
     * @author Thimo van Velzen
     */
    @Test
    public void Should_IncreasePlayerDiscardPileByOneWhenDiscardingPlayerCard() {
        PlayerCard playerCard = new CityCard(new City("Atlanta", VirusType.BLUE));
        gameboard.discardPlayerCard(playerCard);

        int expectedPlayerDiscardStackSize = 1;
        int playerDiscardStackSize = gameboard.getPlayerDiscardStack().size();

        assertThat(playerDiscardStackSize, is(expectedPlayerDiscardStackSize));
    }

    /**
     * @author Thimo van Velzen
     */
    @Test
    public void Should_SayThatAtlantaIsOnlyCityWithResearchStationWhenGameStarts() {
        ArrayList<City> citiesWithResearchStation = gameboard.getCitiesWithResearchStations();

        boolean atlantaHasResearchStation = citiesWithResearchStation.get(0).getName().equals("Atlanta");
        boolean isOnlyOneCity = citiesWithResearchStation.size() == 1;
        boolean atlantaIsOnlyCityWithResearchStation = atlantaHasResearchStation && isOnlyOneCity;

        assertThat(atlantaIsOnlyCityWithResearchStation, is(true));
    }

    /**
     * @author Thimo van Velzen
     */
    @Test
    public void Should_IncreaseSizeOfCitiesWithResearchStationWhenAddingResearchStationToCity() {
        try {
            City newYork = gameboard.getCity("New York");

            gameboard.addResearchStationToCity(newYork);
            int expectedResearchStationSize = 2;
            int researchStationSize = gameboard.getCitiesWithResearchStations().size();

            assertThat(researchStationSize, is(expectedResearchStationSize));

        } catch (CityNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    /**
     * @author Thimo van Velzen
     */
    @Test
    public void Should_SayThatWashingTonIsConnectedToAtlanta() {
        boolean isConnected = false;

        try {
            City atlanta = gameboard.getCity("Atlanta");
            isConnected = gameboard.getCity("Washington").checkCityForAdjacency(atlanta);
        } catch (CityNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        assertThat(isConnected, is(true));
    }
}
