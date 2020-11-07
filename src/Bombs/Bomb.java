package Bombs;

import Blocks.Block;
import GameObject.GameObject;
import Mortals.StatusChecker;
import Players.Player;
import Sounds.ExplosionBombSound;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static Common.Common.doubleToInt;

/**
 * Created by maemr on 7.05.2016.
 */
public class Bomb extends GameObject {


    //We have three( count + 1 because first image created when you put the bomb) image for bomb.
    private int count;

    private BoundingBox[] bounds;
    //Player who put this bomb
    private Player ownedPlayer;

    //Shows which flame you have to put to the this.currentMap
    private int flameIndex;

    //It contains all flames that will put to the game
    //when the bomb explode
    private BombFlame[][] flames;

    //This will be owned player's range
    private int range;

    /*
        Start points of the bomb
     */
    private int startPointX;
    private int startPointY;

    //The end point that flames can reach
    private int xLim;
    private int yLim;

    private double unit;

    //If flames will stop it will be true;
    /*
        0 = up
        1 = down
        2 = left
        3 = right
     */
    private boolean[] isStop;

    public Bomb(double x, double y, Player ownedPlayer) {
        super("Bomb", "NormalBomb", "/Bomb" + 3, x, y);

        count = 2;
        flameIndex = 0;
        this.ownedPlayer = ownedPlayer;

        isStop = new boolean[4];

        this.currentMap = ownedPlayer.getCurrentMap();


        /*
            Bomb's position as integer.
            For example
            Your block's size is 64 x 64
            0 = [0,64)
            1 = [64,128)
            2 = [128,192)
            .
            .
            .
            e.g.
            Your location is 65 129
            Then, x = 1, y = 2

        */

        unit = this.currentMap.getUnit();

        startPointX = doubleToInt(this.getLayoutX(), unit);
        startPointY = doubleToInt(this.getLayoutY(), unit);

        xLim = this.currentMap.getColumn();
        yLim = this.currentMap.getRow();

        range = ownedPlayer.getRange();

        flames = new FlameSet(4, range).getFlames();

        this.currentMap.addBomb(this, this.getIntCenterPosY(), getIntCenterPosX());

        bounds = new BoundingBox[4];

        bounds[0] = new BoundingBox(x + 1, y, pxWidth - 2, 1);
        bounds[1] = new BoundingBox(x + 1, y + pxHeight - 1, pxWidth - 2, 1);
        bounds[2] = new BoundingBox(x, y + 1, 1, pxHeight - 2);
        bounds[3] = new BoundingBox(x + pxWidth - 1, y + 1, 1, pxHeight - 2);


    }

    public BoundingBox getBound(int dir) {
        return bounds[dir];
    }

    //Bomb starts count from 3
    public void igniteWick(int startTime) {

        //Add bomb to game this.currentMap
        this.currentMap.getGameMap().getChildren().add(this);

        //Check is it time to put flames
        Timeline igniteChecker = new Timeline(new KeyFrame(Duration.seconds(startTime / 3), event -> {
            if (count != 0) {
                this.setImage(new Image("/GameTextures/GameObjectTextures/BombTextures/Bomb" + count + ".png"));
                count--;
            }
        }));

        igniteChecker.setCycleCount(3);
        igniteChecker.play();

        igniteChecker.setOnFinished(e -> {
            this.dead();
            startToPutFlames();
        });


    }

    public void dead() {
        this.currentMap.getGameMap().getChildren().remove(this);
        ownedPlayer.setCurrBomb(ownedPlayer.getCurrBomb() + 1);
        this.isDead = true;
        new ExplosionBombSound().playSound();
    }

    /*
        As you see it puts flame to the map
     */
    private void startToPutFlames() {

        for (int step = 1; step <= range; step++) {

            for (int dir = 0; dir < 4; dir++) {

                if (isOkToPut(dir, step) && !isStop[dir]) {

                    Block b = getBlockFromDir(dir, step);

                    if (b.isInfrangible()) {
                        isStop[dir] = true;
                    } else {
                        addFlame(isFinish(dir, step, b), dir, step);
                        b.dead();
                    }

                }
            }
        }

        startRemoveFlames();

    }

    /*
        Checks Is next block out of the map ?
     */
    private boolean isOkToPut(int dir, int step) {

        switch (dir) {
            case 0:
                return startPointY - step >= 0;
            case 1:
                return startPointY + step < yLim;
            case 2:
                return startPointX - step >= 0;
            case 3:
                return startPointX + step < xLim;
        }
        return false;
    }

    /*
        It returns the block that flame will put
     */
    private Block getBlockFromDir(int dir, int step) {

        Block[][] blocks = currentMap.getBlocks();
        switch (dir) {
            case 0:
                return blocks[startPointY - step][startPointX];
            case 1:
                return blocks[startPointY + step][startPointX];
            case 2:
                return blocks[startPointY][startPointX - step];
            case 3:
                return blocks[startPointY][startPointX + step];
        }
        return null;
    }

    /*
        Checks Is flames will stop.
        E.g if next block is frangible block
        then flame destroy it and stop.
     */
    private boolean isFinish(int dir, int step, Block b) {

        if (step == range || b.isFrangible()) {
            isStop[dir] = true;
            return true;
        }
        switch (dir) {
            case 0:
                return startPointY - step == 0;
            case 1:
                return startPointY + step == yLim - 1;
            case 2:
                return startPointX - step == 0;
            case 3:
                return startPointX + step == xLim - 1;
        }
        return false;
    }

    /*

    0 = Up
    1 = Down
    2 = Left
    3 = Right

    */
    private void addFlame(boolean isFinish, int dir, int step) {

        //If it will finish now It put the last flame texture( does not put - , put >)
        BombFlame t = (isFinish) ? flames[dir][range - 1] : flames[dir][step - 1];

        switch (dir) {
            case 0:
                toMap(t, startPointX, startPointY - step);
                break;
            case 1:
                toMap(t, startPointX, startPointY + step);
                break;
            case 2:
                toMap(t, startPointX - step, startPointY);
                break;
            case 3:
                toMap(t, startPointX + step, startPointY);
                break;
        }

    }

    /*
        Only add flames to the game map
     */
    private void toMap(BombFlame flame, double x, double y) {

        flame.setLayoutX(x * this.unit);
        flame.setLayoutY(y * this.unit);
        this.currentMap.getGameMap().getChildren().add(flame);
        flame.setCurrentMap(this.currentMap);

        //Checks Is this flame intersects with any mortal on the map
        new StatusChecker(5, flame);


    }

    /*
        For remove flames from map
     */
    private void removeFlames(int x, int y) {
        try {
            BombFlame f = flames[x][y];
            this.currentMap.getGameMap().getChildren().remove(f);
            f.dead();
        } catch (Exception e) {

        }

    }

    /*
        Start a timer for remove flames slowly
     */
    private void startRemoveFlames() {

        Timeline removeFlames = new Timeline(new KeyFrame(Duration.millis(50), event -> {

            ownedPlayer.getOwnedBombs().remove(this);

            for (int i = 0; i < 4; i++) {
                removeFlames(i, flameIndex);
            }
            flameIndex++;
        }));
        removeFlames.setCycleCount(range);
        removeFlames.play();

        //It removes the bomb from the map when it removed all flames.
        removeFlames.setOnFinished(e -> {
            this.currentMap.getGameMap().getChildren().remove(this);
            this.ownedPlayer.setImage(ownedPlayer.getDefaultView());
            this.ownedPlayer.setPutABomb(false);
            //this.currentMap.removeBomb(this);
            this.currentMap.addBomb(null, this.getIntCenterPosY(), this.getIntCenterPosX());
        });

    }


}
