package Model;

public abstract class EventCard extends PlayerCard {
    private final String name;
    private String effectText;
    private boolean played;

    public EventCard(String name, String effectText) {
        this.name = name;
        this.effectText = effectText;
        played = false;
    }

    public abstract void play();

    public String getEffectText() {
        return effectText;
    }

    public boolean getPlayed() {
        return played;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setPlayed() {
        played = true;
    }
}
