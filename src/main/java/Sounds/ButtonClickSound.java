package Sounds;

import javafx.scene.media.Media;

/**
 * Created by maemr on 27.05.2016.
 */
public class ButtonClickSound extends GameSound {

    public ButtonClickSound() {
        super(new Media(ButtonClickSound.class
                .getResource("/SoundFiles/ButtonClickSound.wav").toString()));

    }

}
