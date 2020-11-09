package PowerUps;

import GameObject.GameObject;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Random;

/**
 * Created by maemr on 15.05.2016.
 */
public class PowerUp extends GameObject {

    public PowerUp(String objId, String path, double x, double y) {
        super("PowerUp", objId, path, x, y);
        flipAnimation(this);
    }

    public void dead() {
        System.out.println("herejhhjgjlghjgljg");
        this.currentMap.getGameMap().getChildren().remove(this);
        System.out.println("old is dead = "+isDead());
        isDead = true;
        System.out.println("new is dead = "+isDead());
    }

    public void flipAnimation(Node node) {
        TranslateTransition tr = new TranslateTransition(Duration.seconds(1.5), node);
        tr.setFromY(0);
        tr.setToY(5);
        tr.setAutoReverse(true);
        tr.setCycleCount(Animation.INDEFINITE);
        tr.play();

        Random rand = new Random();
        RotateTransition rt = new RotateTransition(Duration.millis(rand.nextInt(50) + 2000), node);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setAxis(new Point3D(0, 1, 0));
        rt.play();
    }

    // 0 = AddBomb, 1 = AddRAnge, 2 = AddSpeed,
    public static PowerUp getPowerUp(int p, double x, double y) {
        switch (p) {
            case 0:
                return new AddBomb(x, y);
            case 1:
                return new AddRange(x, y);
            case 2:
                return new AddSpeed(x, y);
        }
        return null;
    }

    public static PowerUp getRandPowerUp(double x, double y) {
        Random rand = new Random();
        //AddBomb PowerUp is disabled for now
        return getPowerUp(rand.nextInt(2)+1, x, y);
    }
}
