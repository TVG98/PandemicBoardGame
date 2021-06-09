package Model;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundEffects
{
    final String pathToSoundOne = "src/main/media/menusound.wav";

    public SoundEffects() {

    }

    public void playSound() {

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(pathToSoundOne)));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
