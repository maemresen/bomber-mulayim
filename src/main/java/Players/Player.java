package Players;

import Blocks.Block;
import Bombs.Bomb;
import Game.GameMap;
import GameObject.GameObject;
import InerfacesClasses.AI;
import InerfacesClasses.Bomber;
import InerfacesClasses.Mover;
import Movement.MoveChecker;
import PowerUps.PowerUp;
import Sounds.FootStepSound;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by maemr on 7.05.2016.
 */
public abstract class Player extends GameObject implements Mover, Bomber, AI {


    //for movement direction

    protected boolean[] dirs;
    protected boolean isDead;
    protected boolean isAI;
    private boolean isBombKeyPressed;
    private boolean isPutABomb;

    private MoveChecker moveChecker;
    //Control type of player
    //e.g. secondary control is wasd.
    protected int cType;

    protected FootStepSound stepSound;
    /*
     * maxBomb = Maximum bomb number of player
     * currBomb = Current bomb number of player
     * range = Range of player's bombs.
     */
    protected int maxBomb, currBomb, range;

    //All bombs of player
    private ArrayList<Bomb> ownedBombs;
    private Image wobView;
    private Image defaultView;

    protected boolean isEnemy;
    //Player Key Sets 0 = up, 1 = down, 2 = left, 3 = right, 4 = put a bomb
    private static String[][] playerKeySets = new String[2][5];

    public Player(String objId, String objName, String ext, double x, double y, int cType, boolean isAI) {
        super("Player", objId, objName, "/" + objId, ext, x, y);
        this.cType = cType;
        ownedBombs = new ArrayList<>();
        isDead = false;
        this.isAI = isAI;
        dirs = new boolean[5];
        if (isAI) {
            this.setRandomDir(0, 4);
        } else {
            this.stepSound = new FootStepSound();
        }
        defaultView = this.getImage();
        wobView = new Image("/GameTextures/GameObjectTextures/PlayerTextures/hero" + cType + "wob.gif");

    }

    public Player(String objId, double x, double y, int cType, boolean isAI) {
        this(objId, "no-name", "png", x, y, cType, isAI);
    }

    public static void addKey(int type, int dir, String key) {
        playerKeySets[type][dir] = key;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getCurrBomb() {
        return currBomb;
    }

    public void setCurrBomb(int currBomb) {
        this.currBomb = currBomb;
    }

    public int getRange() {
        return range;
    }

    public ArrayList<Bomb> getOwnedBombs() {
        return ownedBombs;
    }

    public boolean isAI() {
        return isAI;
    }

    public void moveMyHero(KeyEvent e, boolean isMove) {

        String dir = e.getCode().toString();

        /************************************************
         Bu kodu eklediğim zaman oyun takılarak
         çalışmaya başlıyor yapmak istediğim şey eğer
         hareket etmek için bir tuşa basarsak adım atma
         sesini oynatıyor

         if(isMove && !stepSound.isStarted()){
         stepSound.playSound();
         }else {
         stepSound.stopSound();
         }
         *************************************************/
        for (int i = 0; i < 5; i++) {
            if (dir.equals(playerKeySets[cType][i])) {
                if (i < 4) {
                    setDirVal(i, isMove);
                } else if (!this.isBombKeyPressed) {
                    putBomb();
                }
                isBombKeyPressed = isMove;
                return;
            }
        }

    }

    /*
        0 = up
        1 = down
        2 = left
        3 = right
     */
    private void setDirVal(int dir, boolean val) {
        dirs[dir] = val;
    }


    /*
        Move Methods
    */


    //This is the  move method of player.
    public void move() {

        /*
            0 = up, 1 = down, 2 = left, 3 = right boolean
            As you see if the boolean up is true
            that means you press the key that
            moves your character up same thing
            is valid for others
        */

        for (int dir = 0; dir < 4; dir++) {
            if (dirs[dir]) {
                if (isOkToMove(dir)) {
                    moveTo(dir);

                } else if (this.isAI) {
                    moveAI(dir);
                }
            }
        }

    }

    public void moveAI(int dir) {

        if (this.isAI()) {
            if (dir == 0 || dir == 1) {
                setDirVal(dir, false);
                setRandomDir(2, 4);

            } else {
                setDirVal(dir, false);
                setRandomDir(0, 2);
            }
        }

    }

    /*
        0 = up, 1 = down, 2 = left, 3 = right
        It check Is the next block is ok to move.
        For example if dir = right then it looks
        the right block.
     */
    private boolean isOkToMove(int dir) {

        /*
            It is the row or column of your player in his map.
            E.g. if your x or y position is 65 that means you
            are in the 1th row or column.
        */
        int rowInMap = this.getRowInMap();
        int colInMap = this.getColInMap();
        switch (dir) {
            case 0:
                return GameMap.isInBoundsVer(this, 0) &&
                        (rowInMap <= 0 || checkRowN(rowInMap - 1, 1));
            case 1:
                return GameMap.isInBoundsVer(this, 1) &&
                        (rowInMap >= currentMap.getRow() - 1 || checkRowN(rowInMap + 1, 0));
            case 2:
                return GameMap.isInBoundHor(this, 0) &&
                        (colInMap <= 0 || checkColN(colInMap - 1, 3));
            case 3:
                return GameMap.isInBoundHor(this, 1) &&
                        (colInMap + 1 >= currentMap.getColumn() || checkColN(colInMap + 1, 2));
        }
        return false;

    }


    /*
        It is for move player to the given dir
        E.g if dir = UP your player will go 1px up
     */
    private void moveTo(int dir) {
        switch (dir) {
            case 0:
                this.setLayoutY(this.getLayoutY() - 1);
                break;
            case 1:
                this.setLayoutY(this.getLayoutY() + 1);
                break;
            case 2:
                this.setLayoutX(this.getLayoutX() - 1);
                break;
            case 3:
                this.setLayoutX(this.getLayoutX() + 1);
        }

    }

    /*
        It checks all blocks that's in the cTh row in the map
     */
    private boolean checkRowN(int r, int dir) {
        for (int i = 0; i < this.currentMap.getColumn(); i++) {
            Block myBlock = this.currentMap.getBlocks()[r][i];
            if (!myBlock.isGround() &&
                    this.getBoundsInParent().intersects(myBlock.getBound(dir)))
                return false;
        }
        if (isEnemy()) {
            for (int i = 0; i < this.currentMap.getColumn(); i++) {
                try {
                    Bomb bomb = this.currentMap.getBombs()[r][i];
                    if (this.getBoundsInParent().intersects(bomb.getBound(dir))) {
                        return false;
                    }
                } catch (Exception e) {

                }
            }
        }
        return true;
    }

    /*
        It checks all blocks that's in the cTh column in the map
     */
    private boolean checkColN(int c, int dir) {
        for (int i = 0; i < this.currentMap.getRow(); i++) {
            Block myBlock = this.currentMap.getBlocks()[i][c];
            /*
                If it is ground that means it does not affect anything for player
             */
            if (!myBlock.isGround() &&
                    this.getBoundsInParent().intersects(myBlock.getBound(dir)))
                return false;
        }
        if (isEnemy()) {
            for (int i = 0; i < this.currentMap.getRow(); i++) {
                try {
                    Bomb bomb = this.currentMap.getBombs()[i][c];
                    if (this.getBoundsInParent().intersects(bomb.getBound(dir))) {
                        return false;
                    }
                } catch (Exception e) {

                }
            }
        }
        return true;
    }

    private boolean checkBombs() {
        return false;
    }

    public boolean isPutABomb() {
        return isPutABomb;
    }

    public void setPutABomb(boolean putABomb) {
        isPutABomb = putABomb;
    }

    //For put bomb when you plant a bomb key
    public void putBomb() {
        if (!isDead && currBomb > 0) {

            this.isPutABomb = true;
            this.setImage(wobView);

            /*
                These are the center position of your hero
             */
            double cX = (int) ((this.getLayoutX() + this.getImage().getWidth() / 2) / currentMap.getUnit()) * currentMap.getUnit();
            double cY = (int) ((this.getLayoutY() + this.getImage().getHeight() / 2) / currentMap.getUnit()) * currentMap.getUnit();

            /*
                Create new bomb and start count from 3
                This will change when you pick up some power-ups
            */
            Bomb bomb = new Bomb(cX, cY, this);
            //this.currentMap.addBomb(bomb);
            bomb.igniteWick(3);
            this.getOwnedBombs().add(bomb);
            this.currBomb--;
        }

    }


    /*
        As you see it removes your player from the map
     */
    public void dead() {

        this.currentMap.getGameMap().getChildren().remove(this);
        this.currentMap.getPlayers().remove(this);
        this.isDead = true;
    }

    public static String[][] getPlayerKeySets() {
        return playerKeySets;
    }

    /*
        It is not working.
        Only for check Is all keys are true
        I will delete it
     */
    public static void printPlayerKeySet() {
        System.out.println("Player keySetArray");
        for (String[] strings : Player.getPlayerKeySets()) {
            for (String s : strings) {
                System.out.println(s + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Finish");
        System.out.println("--------------------------");
    }

    /*
        It is for check any key is used for control
        your player
     */
    public static boolean isContains(String s) {

        for (String[] strings : playerKeySets) {
            for (String key : strings) {
                if (key.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Image getDefaultView() {
        return this.defaultView;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    /*
        It is for AI.
        It assigns a random direction when you create enemy
        to begin move
        0 and 1 is up and down
        2 and 3 is left and right
     */
    public void setRandomDir(int a, int b) {

        Random random = new Random();
        int dir = random.nextInt(b - a) + a;
        setDirVal(dir, true);
    }


    /*
        int for look dir.
        dir = 0 => it looks upper bound of the map
        dir = 1 => it looks bottom bound of the map
     */
    private boolean isInBoundsVer(int dir) {

        GameMap map = this.currentMap;
        double y = this.getLayoutY();
        double yLim = map.getYBound() - this.pxHeight;
        return (dir == 0) ? y > 0 : y < yLim;

    }


    /*
        int for look dir.
        dir = 0 => it looks left bound of the map
        dir = 1 => it looks right bound of the map
     */
    private boolean isInBoundHor(int dir) {

        GameMap map = this.currentMap;
        double x = this.getLayoutX();
        double xLim = map.getXBound() - this.pxWidth;

        return (dir == 0) ? x > 0 : x < xLim;

    }

    public void pickPowerUp(PowerUp p) {

        if (!this.isEnemy) {
            String type = p.getObjId();
            switch (type) {
                case "AddBomb":
                    this.maxBomb++;
                    this.currBomb++;
                    break;
                case "AddRange":
                    this.range++;
                    break;
                case "AddSpeed":
                    this.moveChecker.speedUp();
                    break;
            }
            p.dead();
        }

    }

    public MoveChecker getMoveChecker() {
        return moveChecker;
    }

    public void setMoveChecker(MoveChecker moveChecker) {
        this.moveChecker = moveChecker;
    }
}
