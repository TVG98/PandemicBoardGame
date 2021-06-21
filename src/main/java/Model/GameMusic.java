package Model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Plays the game music.
 * @author Daniel Paans
 */

public class GameMusic implements GameSounds {
    private float volume;
    private FloatControl gainControl;

    public GameMusic(float volume) {
        this.volume = volume;
    }

    @Override
    public void playSound(Sound music) {
        try {
            String soundPath = getSoundEffectPath(music);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(soundPath)));
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVolume(float volume) {
        this.volume = volume;
        gainControl.setValue(this.volume);
    }
}
