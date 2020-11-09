package Sounds;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Created by maemr on 27.05.2016.
 */
public abstract class GameSound {

    protected MediaPlayer mediaPlayer;
    private boolean isStarted;


    public GameSound(Media media) {
        mediaPlayer = new MediaPlayer(media);

    }

    public void playSound() {
        mediaPlayer.stop();
        mediaPlayer.play();
        this.isStarted = true;

    }

    public void stopSound() {
        mediaPlayer.stop();
        this.isStarted = false;
    }

    public boolean isStarted() {
        return this.isStarted;
    }


}
