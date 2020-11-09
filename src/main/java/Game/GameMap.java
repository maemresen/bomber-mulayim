package Game;


import Blocks.Block;
import Blocks.FrangibleBlock;
import Blocks.GroundBlock;
import Blocks.InfrangibleBlock;
import Bombs.Bomb;
import GameObject.GameObject;
import Mortals.StatusChecker;
import Movement.MoveChecker;
import Players.Player;
import Players.Zombie;
import PowerUps.PowerUp;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by maemr on 21.04.2016.
 */
public class GameMap {


    /*
    --------------------------------------------------------------------------------------
           * Each gameMaps has an id.

           * Called by this id
    --------------------------------------------------------------------------------------
     */

    int id;
    private int row;
    private int col;
    private double xBound;
    private double yBound;


    int unit;

    private ArrayList<Player> players;

    private Block[][] blocks; //For blocks

    private PowerUp[][] powerUps;

    private Bomb[][] bombs;


    Group gameMap; // This will be combine of everything characters blocks...

    public GameMap(int id, int row, int col) {

        this.id = id;
        this.row = row;
        this.col = col;

        blocks = new Block[row][col];
        powerUps = new PowerUp[row][col];
        bombs = new Bomb[row][col];

        unit = (int) new GroundBlock(0, 0).getImage().getHeight();

        xBound = col * unit;
        yBound = row * unit;

        gameMap = new Group();

        players = new ArrayList<>();

        fillGameMap();
    }

    /*
    --------------------------------------------------------------------------------------
           * It is for fill the game gameMaps. put blocks, characters etc
    --------------------------------------------------------------------------------------
     */
    private void fillGameMap() {

        /*
            i shows rows
            j shows columns


          i |
            |
            |
            |
            |
            ▾ ------------► j

         */
        for (int i = 0; i < row; i++) {

            for (int j = 0; j < col; j++) {

                int x = j * unit;
                int y = i * unit;
                if ((j * i) % 2 != 0) {
                    addBlock(new InfrangibleBlock(x, y), i, j);
                } else if (shall_I_Put() && !isItCorner(j, i)) {
                    if (!shall_I_Put()) {
                        addPowerUp(PowerUp.getRandPowerUp(x + 7, y + 5), i, j);
                    }
                    addBlock(new FrangibleBlock(x, y), i, j);

                } else {
                    addBlock(new GroundBlock(x, y), i, j);


                    if (!isItCorner(j, i) && ((i >= 2 && i <= row - 3) && (j >= 2 && j <= col - 3))) {
                        this.addPlayer(new Zombie(x, y));
                    }
                }
            }

        }
        updatePlayers();


    }

    /*
    --------------------------------------------------------------------------------------
          * It sets possibility of putting blocks.

          * It returns % 75 true
    --------------------------------------------------------------------------------------
     */
    private boolean shall_I_Put() {

        Random rand = new Random();

        return rand.nextBoolean() || rand.nextBoolean();
    }


    /*
    --------------------------------------------------------------------------------------
           * It never put a block on this points with represent o

            o o        o o
            o            o


            o            o
            o o        o o
    --------------------------------------------------------------------------------------
     */
    private boolean isItCorner(int xC, int yC) {

        xC = xC * unit;
        yC = yC * unit;
        int sum = xC + yC;
        if (sum <= unit || sum >= ((row - 1) * unit + (col - 1) * unit) - unit) {
            return true;
        }

        if (xC * yC == 0) {
            return xC >= ((col - 1) * unit - unit) || yC >= ((row - 1) * unit - unit);
        }

        if (xC == (col - 1) * unit || yC == (row - 1) * unit) {
            return xC == unit || yC == unit;
        }
        return false;

    }


    /*
    --------------------------------------------------------------------------------------
           * Methods for add blocks power-ups and players to gameMaps
    --------------------------------------------------------------------------------------
     */

    private void addBlock(Block b, int r, int c) {

        b.setCurrentMap(this);

        blocks[r][c] = b;
        gameMap.getChildren().add(blocks[r][c]);
    }

    private void addPowerUp(PowerUp p, int r, int c) {
        p.setCurrentMap(this);
        powerUps[r][c] = p;
        gameMap.getChildren().add(powerUps[r][c]);
        new StatusChecker(5, powerUps[r][c]);
    }

    public void addPlayer(Player p) {
        p.setCurrentMap(this);
        int interval;
        if (p.isEnemy()) {
            interval = 15;

            new StatusChecker(5, p);
        } else {
            interval = 5;
        }
        p.setMoveChecker(new MoveChecker(p, interval));
        players.add(p);
        gameMap.getChildren().add(p);
    }

    public Group getGameMap() {
        return gameMap;
    }


    public Block[][] getBlocks() {
        return blocks;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    public int getUnit() {
        return unit;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public double getXBound() {
        return xBound;
    }

    public double getYBound() {
        return yBound;
    }

    private void updatePlayers() {
        for (Player p : players) {
            p.toFront();
        }
    }

    public Bomb[][] getBombs() {
        return bombs;
    }
    public void addBomb(Bomb b, int y, int x){
        bombs[y][x] = b;
    }


    /*
                int for look dir.
                dir = 0 => it looks upper bound of the map
                dir = 1 => it looks bottom bound of the map
             */
    public static boolean isInBoundsVer(GameObject obj, int dir) {

        GameMap map = obj.getCurrentMap();
        double y = obj.getLayoutY();
        double yLim = map.getYBound() - obj.getPxHeight();
        return (dir == 0) ? y > 0 : y < yLim;

    }


    /*
        int for look dir.
        dir = 0 => it looks left bound of the map
        dir = 1 => it looks right bound of the map
     */
    public static boolean isInBoundHor(GameObject obj, int dir) {

        GameMap map = obj.getCurrentMap();
        double x = obj.getLayoutX();
        double xLim = map.getXBound() - obj.getPxWidth();

        return (dir == 0) ? x > 0 : x < xLim;

    }
}
