package Model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class TestPlayer {

    private Player player;
    private PlayerCard playerCard;

    @Before
    public void setup() {
        player = new Player("Thimo", true);
        playerCard = new CityCard(new City("Atlanta", VirusType.BLUE), VirusType.BLUE);
    }

    @Test
    public void Should_ReturnThatPlayerCardIsInHandOfPlayer() {
        player.addCardToHand(playerCard);
        boolean playerHasCard = player.checkCardInHand(playerCard);

        assertThat(playerHasCard,  is(true));
    }

    @Test
    public void Should_ReturnThatPlayerCardIsNotInTheHandOfPlayerBecauseDifferentVirusType() {
        PlayerCard differentPlayerCard = new CityCard(new City("Atlanta", VirusType.BLUE), VirusType.BLUE);
        player.addCardToHand(playerCard);

        boolean playerHasCard = player.checkCardInHand(differentPlayerCard);

        assertThat(playerHasCard,  is(false));
    }

    @Test
    public void Should_ReturnThatPlayerCardIsNotInTheHandOfPlayerBecauseDifferentCityName() {
        PlayerCard differentPlayerCard = new CityCard(new City("New York", VirusType.BLUE), VirusType.BLUE);
        player.addCardToHand(playerCard);

        boolean playerHasCard = player.checkCardInHand(differentPlayerCard);

        assertThat(playerHasCard,  is(false));
    }
}
