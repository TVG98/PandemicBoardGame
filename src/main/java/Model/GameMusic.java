package Model;

import Exceptions.SoundNotFoundException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class GameMusic implements GameSounds {
    private float volume;
    private FloatControl gainControl;

    public GameMusic(float volume) {
        this.volume = volume;
    }


    private String getSoundEffectPath(Sound musicNumber) throws SoundNotFoundException {
        String soundPath = "src/main/media/GameMusic/";

        try {
            soundPath += musicNumber.name().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SoundNotFoundException("SoundEffect number does not exist");
        }

        return soundPath + ".wav";
    }

    @Override
    public void playSound(Sound musicNumber) {
        try {
            String soundPath = getSoundEffectPath(musicNumber);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(soundPath)));
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
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
        gainControl.setValue(this.volume);
    }
}
