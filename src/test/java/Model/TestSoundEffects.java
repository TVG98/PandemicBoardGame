package Model;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author Daniel Paans
 */
public class TestSoundEffects {

    private SoundEffects soundEffects;

    @Before
    public void setup() {
        soundEffects = new SoundEffects(-10);
    }

    @Test
    public void Should_ReturnCorrectSoundEffectPath_When_ReceivingSoundConstant() {
        for(Sound sound : Sound.values()) {
            String correctSoundPath = "src/main/media/GameMusic/" + sound.name().toLowerCase() + ".wav";
            String soundPath = soundEffects.getSoundEffectPath(sound);
            assertThat(soundPath, is(correctSoundPath));
        }
    }

    @Test
    public void Should_PlaySound_When_RecievingASoundPath() {
        for(Sound sound : Sound.values()) {
            try {
                if(sound != Sound.MAINMUSIC) {
                    soundEffects.playSound(sound);
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
