package Model;

import Exceptions.SoundNotFoundException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.Locale;

public class SoundEffects implements GameSounds {
    private float volume;

    public SoundEffects(float volume) {
        this.volume = volume;
    }


    private String getSoundEffectPath(Sound soundEffect) throws SoundNotFoundException {
        String soundPath = "src/main/media/GameMusic/";

        try {
            soundPath += soundEffect.name().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SoundNotFoundException("SoundEffect number does not exist");
        }

        return soundPath + ".wav";
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
        } catch (SoundNotFoundException snfe) {
            snfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVolume(float volume) {
        this.volume = volume;
    }
}
