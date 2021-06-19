package Controller;

import Model.GameMusic;
import Model.GameSounds;
import Model.Sound;
import Model.SoundEffects;

public class SoundController {

    static SoundController soundController;

    private final int MAXVOLUME = 100;
    private final float TRANSLATION = 2f;

    private float masterVolume = translateVolume(30);
    private float sfxVolume = masterVolume;
    private float musicVolume = masterVolume;
    private float[] volumes = new float[] {100, 100, 100};


    SoundEffects sfx = new SoundEffects(sfxVolume);
    GameMusic music = new GameMusic(musicVolume);

    private SoundController() {
        playMusic(Sound.MAINMUSIC);
    }

    public static SoundController getInstance() {
        if (soundController == null) {
            soundController = new SoundController();
        }

        return soundController;
    }

    public void playSound(Sound sound) {
        sfx.playSound(sound);
    }

    private void playMusic(Sound sound) {
        music.playSound(sound);
    }

    private float translateVolume(float volumePercentage) {
        return ((MAXVOLUME - volumePercentage) * -1)/TRANSLATION;
    }

    private float translateVolumeWithMasterVolume(float volumePercentage) {
        float volume = translateVolume(volumePercentage) + masterVolume;
        if(volume < -80.0f) {
            return -80.0f;
        } else {
            return volume;
        }
    }

    public void setMasterVolume(float volume) {
        this.masterVolume = translateVolume(volume);
        setSfxVolume(volumes[1]);
        setMusicVolume(volumes[2]);
        volumes[0] = volume;
    }

    public void setSfxVolume(float volume) {
        this.sfxVolume = translateVolumeWithMasterVolume(volume);
        sfx.setVolume(sfxVolume);
        volumes[1] = volume;
    }

    public void setMusicVolume(float volume) {
        this.musicVolume = translateVolumeWithMasterVolume(volume);
        music.setVolume(musicVolume);
        volumes[2] = volume;
    }

    public float[] getVolumes() {
        return volumes;
    }
}
