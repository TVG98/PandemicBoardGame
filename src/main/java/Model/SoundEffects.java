package Model;

import Exceptions.SoundNotFoundException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class SoundEffects implements GameSounds {
    private float volume; // Reduce volume by 10 decibels.

    public SoundEffects(float volume) {
        this.volume = volume;
    }

    @Override
    public String getSoundEffectPath(int soundNumber) throws SoundNotFoundException {
        String soundPath = "src/main/media/";

        switch (soundNumber) {
            case 0:
                soundPath += "menusound.wav";
                break;
            case 1:
                soundPath += "etc.";
                break;
            default:
                throw new SoundNotFoundException("SoundEffect number does not exist");
        }

        return soundPath;
    }

    @Override
    public void playSound(int soundEffectNumber) {

        try {
            String soundPath = getSoundEffectPath(soundEffectNumber);
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
