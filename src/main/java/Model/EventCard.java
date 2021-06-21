package Model;

/**
 * An eventCard in the game.
 * @author Daniel Paans
 */
public class EventCard extends PlayerCard {
    private String effectText;
    private boolean played;

    public EventCard() {}

    public EventCard(String name, String effectText) {
        setName(name);
        this.effectText = effectText;
        played = false;
    }

    public void play() {}

    public String getEffectText() {
        return effectText;
    }

    public boolean getPlayed() {
        return played;
    }

    public void setPlayed() {
        played = true;
    }
}
