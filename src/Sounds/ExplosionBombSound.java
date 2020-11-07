package Sounds;

import javafx.scene.media.Media;

/**
 * Created by maemr on 27.05.2016.
 */
public class ExplosionBombSound extends GameSound {
    public ExplosionBombSound() {
        super(new Media(ButtonClickSound.class
                .getResource("/SoundFiles/ExplosionBomb.mp3").toString()));

    }

}
