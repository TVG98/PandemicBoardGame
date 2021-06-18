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

    @Override
    public String getSoundEffectPath(int soundNumber) throws SoundNotFoundException {
        String soundPath = "src/main/media/";

        switch (soundNumber) {
            case 0:
                soundPath += "big-boi-pants.wav";
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
    public void playSound(int musicNumber) {
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
        this.volume = volume * 0.8f;
        gainControl.setValue(this.volume);
    }
}
