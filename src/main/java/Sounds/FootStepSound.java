package Sounds;

import javafx.scene.media.Media;

/**
 * Created by maemr on 27.05.2016.
 */
public class FootStepSound extends GameSound {
    public FootStepSound() {
        super(new Media(ButtonClickSound.class
                .getResource("/SoundFiles/FootStepSound.wav").toString()));

    }

}
