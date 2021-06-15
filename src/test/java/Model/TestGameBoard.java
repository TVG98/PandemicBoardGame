package Model;

import Exceptions.CityNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class TestGameBoard {

    private Gameboard gameboard;

    @Before
    public void setup() {
        gameboard = new Gameboard();
    }

    @Test
    public void Should_SayThatPlayerStackSizeIs59WhenInitialized() {
        int expectedPlayerStackSize = 59;
        int playerStackSize = gameboard.getPlayerStack().size();

        assertThat(playerStackSize, is(expectedPlayerStackSize));
    }

    @Test
    public void Should_DecreasePlayerStackSizeByTwoIfTwoPlayerCardsAreTakenFromDeck() {
        gameboard.drawPlayerCard();
        gameboard.drawPlayerCard();

        int expectedPlayerStackSize = 57;
        int playerStackSize = gameboard.getPlayerStack().size();

        assertThat(playerStackSize, is(expectedPlayerStackSize));
    }

    @Test
    public void Should_ThrowCityNotFoundExceptionWhenForgettingCapitalFirstLetter() {
        boolean cityNotFoundExceptionThrown = false;

        try {
            gameboard.getCity("algiers");
        } catch (CityNotFoundException cnfe) {
            cityNotFoundExceptionThrown = true;
        }

        assertThat(cityNotFoundExceptionThrown, is(false));
    }

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
            algiersHasBlackVirusType = algiers.getVirusType().equals(VirusType.BLACK);
            chennaiHasBlackVirusType = chennai.getVirusType().equals(VirusType.BLACK);
        } catch (CityNotFoundException cnfe) {
            cityNotFoundExceptionThrown = true;
        }

        boolean citiesHaveBlackVirusType = !cityNotFoundExceptionThrown &&
                                            algiersHasBlackVirusType &&
                                            chennaiHasBlackVirusType;

        assertThat(citiesHaveBlackVirusType, is(true));
    }

    @Test
    public void Should_IncreasePlayerDiscardPileByOneWhenDiscardingPlayerCard() {
        PlayerCard playerCard = new CityCard(new City("Atlanta", VirusType.BLUE));
        gameboard.discardPlayerCard(playerCard);

        int expectedPlayerDiscardStackSize = 1;
        int playerDiscardStackSize = gameboard.getPlayerDiscardStack().size();

        assertThat(playerDiscardStackSize, is(expectedPlayerDiscardStackSize));
    }

    @Test
    public void Should_SayThatAtlantaIsOnlyCityWithResearchStationWhenGameStarts() {
        ArrayList<City> citiesWithResearchStation = gameboard.getCitiesWithResearchStations();

        boolean atlantaHasResearchStation = citiesWithResearchStation.get(0).getName().equals("Atlanta");
        boolean isOnlyOneCity = citiesWithResearchStation.size() == 1;
        boolean atlantaIsOnlyCityWithResearchStation = atlantaHasResearchStation && isOnlyOneCity;

        assertThat(atlantaIsOnlyCityWithResearchStation, is(true));
    }

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

//    @Test
//    public void Should_SayCureIsFoundForBlackVirusType() {
//        gameboard.
//    }
}
