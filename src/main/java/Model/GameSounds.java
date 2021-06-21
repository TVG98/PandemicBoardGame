package Model;

/**
 * Is implemented by the sound models.
 * @author Daniel Paans
 */

public interface GameSounds {

    default String getSoundEffectPath(Sound soundEffect) {
        String soundPath = "src/main/media/GameMusic/";

        soundPath += soundEffect.name().toLowerCase();

        return soundPath + ".wav";
    }

    void playSound(Sound sound);
    void setVolume(float volume);
}
