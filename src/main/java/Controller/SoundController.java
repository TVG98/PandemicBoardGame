package Controller;

import Model.SoundEffects;

/**
 * @created June 05 2021 - 4:26 PM
 * @project testGame
 */

public class SoundController {
    SoundEffects sfx = new SoundEffects();

    public void playSound() {
        sfx.playSound();
    }
}
