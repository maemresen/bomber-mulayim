package Movement;

import InerfacesClasses.Mover;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


/**
 * Created by maemr on 29.04.2016.
 */
public class MoveChecker {


    /*
        Very simple time line for check your keyEvents
        And move players accordingly your keyEvents.
     */

    private Timeline timer;
    double interval;
    private Mover mover;

    public MoveChecker(Mover mover, double interval) {

        this.interval = interval;
        this.mover = mover;
        startTimer();

    }


    public void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.ZERO, e -> {

            /*
                If your player dead it does not need to work anymore
             */
            if (mover.isDead()) {
                timer.stop();
            }
            mover.move();

        }), new KeyFrame(Duration.millis(interval)));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    public void speedUp() {
        if (interval > 2.5) {
            interval -= 0.5;
        }
        timer.stop();
        startTimer();
    }

}