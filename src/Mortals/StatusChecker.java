package Mortals;

import GameObject.GameObject;
import InerfacesClasses.Mortal;
import Players.Player;
import PowerUps.PowerUp;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


import java.util.ArrayList;

/**
 * Created by maemr on 12.05.2016.
 */
public class StatusChecker {

    //This checks is any player dead with a bomb
    private Timeline flameChecker;

    //This checks is any player pick up a power up
    private Timeline powerUpChecker;

    //This checks is any player dead with an enemy
    private Timeline enemyChecker;

    private Mortal mortal;

    private ArrayList<Player> players;


    public StatusChecker(double interval, Mortal mortal) {


        this.mortal = mortal;
        String objType = ((GameObject) (mortal)).getObjType();
        players = ((GameObject) (mortal)).getCurrentMap().getPlayers();

        if (objType.equals("PowerUp")) {
            powerUpChecker = new Timeline(new KeyFrame(Duration.ZERO, event -> {
                if (mortal.isDead()) {
                    powerUpChecker.stop();
                    return;
                }
                checkPlayers();
            }), new KeyFrame(Duration.millis(interval)));
            powerUpChecker.setCycleCount(Animation.INDEFINITE);
            powerUpChecker.play();
        } else if (objType.equals("Player")) {

            enemyChecker = new Timeline(new KeyFrame(Duration.ZERO, event -> {
                if (mortal.isDead()) {
                    enemyChecker.stop();
                    return;
                }
                checkHeros();
            }), new KeyFrame(Duration.millis(interval)));
            enemyChecker.setCycleCount(Animation.INDEFINITE);
            enemyChecker.play();

        } else {

        /*
            It will finish when the all flames removed from the map
         */
            flameChecker = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                if (mortal.isDead()) {
                    flameChecker.stop();
                    return;
                }
                checkDead();
            }),
                    new KeyFrame(Duration.millis(interval)));
            flameChecker.setCycleCount(Animation.INDEFINITE);
            flameChecker.play();
        }
    }


    public void stopAngel() {
        flameChecker.stop();
    }

    /*
        It checks all players on the map
        If their coordinates intersects with
        this mortal that means this player
        is dead now
     */
    private void checkDead() {
        try {
            for (Player p : players) {
                if (isIntersects(p)) {
                    p.dead();
                }
            }
        } catch (Exception e) {

        }
    }

    /*
        It look for power up.
        If a power up image intersects with
        a player image then player pick that
        power up
     */
    private void checkPlayers() {
        try {
            for (Player p : players) {

                if (isIntersects(p)) {
                    p.pickPowerUp(((PowerUp) (mortal)));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
        }

    }

    private void checkHeros() {
        try {
            for (Player p : players) {
                if (isIntersects(p) && !p.isEnemy()) {
                    p.dead();
                }
            }
        } catch (Exception e) {

        }
    }


    private boolean isIntersects(Player p) {
        int x = p.getIntCenterPosX();
        int y = p.getIntCenterPosY();

        int fY = ((GameObject) (mortal)).getIntCenterPosY();
        int fX = ((GameObject) (mortal)).getIntCenterPosX();
        return x == fX && y == fY;
    }

}
