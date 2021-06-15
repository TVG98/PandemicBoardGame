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

    @Test
    public void Should_RemoveCardFromHandFromPlayer() {
        player.addCardToHand(playerCard);
        player.removeCardFromHand(playerCard);

        boolean playerHasCard = player.checkCardInHand(playerCard);

        assertThat(playerHasCard, is(false));
    }

    @Test
    public void Should_KeepCardFromHandFromPlayer() {
        PlayerCard differentPlayerCard = new CityCard(new City("New York", VirusType.BLUE), VirusType.BLUE);

        player.addCardToHand(playerCard);
        player.removeCardFromHand(differentPlayerCard);

        boolean playerHasCard = player.checkCardInHand(playerCard);

        assertThat(playerHasCard, is(true));
    }

    @Test
    public void Should_DecrementActionsByOne() {
        player.decrementActions();

        int expectedActionsLeft = 3;
        int actionsLeft = player.getActionsLeft();

        assertThat(actionsLeft, is(expectedActionsLeft));
    }

    @Test
    public void Should_ResetActionsToFourActionsLeft() {
        player.decrementActions();
        player.resetActions();

        int expectedActionsLeft = 4;
        int actionsLeft = player.getActionsLeft();

        assertThat(actionsLeft, is(expectedActionsLeft));
    }
}
