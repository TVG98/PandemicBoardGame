package Model;

public class OneQuietNight extends EventCard{

    public OneQuietNight(String name, String effectText) {
        super(name, effectText);
    }

    @Override
    public void play() {
        //Todo skip next infection step
    }
}
