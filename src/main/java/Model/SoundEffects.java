package Model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Plays the sound effects.
 * @author Daniel Paans
 */
public class SoundEffects implements GameSounds {
    private float volume;

    public SoundEffects(float volume) {
        this.volume = volume;
    }

    @Override
    public void playSound(Sound soundEffect) {

        try {
            String soundPath = getSoundEffectPath(soundEffect);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(soundPath)));
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVolume(float volume) {
        this.volume = volume;
    }
}
