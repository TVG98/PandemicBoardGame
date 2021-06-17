package Model;

import Exceptions.SoundNotFoundException;

public interface GameSounds {

    String getSoundEffectPath(int soundNumber) throws SoundNotFoundException;
    void playSound(int soundNumber);
    void setVolume(float volume);
}
