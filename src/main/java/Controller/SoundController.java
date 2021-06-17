package Controller;

import Model.GameMusic;
import Model.SoundEffects;

public class SoundController {

    static SoundController soundController;

    private float masterVolume = translateVolume(100);
    private float sfxVolume = masterVolume;
    private float musicVolume = masterVolume;
    private float uiVolume = masterVolume;

    SoundEffects sfx = new SoundEffects(sfxVolume);
    GameMusic music = new GameMusic(musicVolume);

    private SoundController() {}

    public static SoundController getInstance() {
        if (soundController == null) {
            soundController = new SoundController();
            //soundController.playMusic();
        }

        return soundController;
    }

    private void playMusic() {
        music.playSound(0);
    }

    private float translateVolume(float volumePercentage) {
        return (100 - volumePercentage) * -1;
    }

    public void setMasterVolume(float volume) {
        this.masterVolume = translateVolume(volume);
    }

    public void setSfxVolume(float volume) {
        this.sfxVolume = translateVolume(volume) + masterVolume;
    }

    public void setMusicVolume(float volume) {
        this.musicVolume = translateVolume(volume) + masterVolume;
        music.setVolume(musicVolume);
    }

    public void setUiVolume(float volume) {
        this.uiVolume = translateVolume(volume) + masterVolume;
    }
}
