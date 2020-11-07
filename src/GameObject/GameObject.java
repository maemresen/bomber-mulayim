package GameObject;

import Game.GameMap;
import InerfacesClasses.Mortal;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static Common.Common.doubleToInt;
import static GameObject.GameObject.GameObjects.*;

/**
 * Created by maemr on 6.05.2016.
 */
public abstract class GameObject extends ImageView implements Mortal {

    /*-----------------------------------------------------

     * Each GameObject has an object type
     * eg. Block, Player, Bomb

     * Each GameObject has an object Id
     * e.g. ground, frangible, hero, zombie etc..

     * A hero name must be.
     * It will be no name for other object types

     * Of course, currentMap must be for a GameObject
     * because every GameObject have a map.

    ----------------------------------------------------- */

    protected String objType;
    protected String objId;
    protected String objName;
    protected GameMap currentMap;
    protected boolean isDead;
    protected GameObjects objTyp;
    //Size of GameObject's view
    protected double pxHeight;
    protected double pxWidth;

    public GameObject(String objType, String objId, String path) {
        this(objType, objId, path, 0, 0);
    }

    public GameObject(String objType, String objId, String path, double x, double y) {
        this(objType, objId, "no-name", path, x, y);
    }

    public GameObject(String objType, String objId, String objName, String path, String ext,double x, double y) {
        super(new Image("/GameTextures/GameObjectTextures" + "/" + objType + "Textures" + path + "."+ext));

        this.isDead = false;

        pxHeight = this.getImage().getHeight();
        pxWidth = this.getImage().getWidth();

        this.setLayoutX(x);
        this.setLayoutY(y);

        this.objType = objType;
        this.objId = objId;
        this.objName = objName;

    }


    public GameObject(String objType, String objId, String objName, String path ,double x, double y) {
        this(objType,objId,objName,path,"png",x,y);
    }


    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }


    public GameMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(GameMap currentMap) {
        this.currentMap = currentMap;
    }

    public double getPxHeight() {
        return pxHeight;
    }

    public double getPxWidth() {
        return pxWidth;
    }

    /*
        Get int value of center positions of your GameObject's view
     */
    public int getIntCenterPosX() {
        return ((int) ((this.getLayoutX() + (pxWidth / 2)) / this.currentMap.getUnit()));
    }

    public int getIntCenterPosY() {
        return ((int) ((this.getLayoutY() + pxHeight / 2) / this.currentMap.getUnit()));
    }

    /*
        They get row and column position as integer in current map of that GameObject
        E.g x = 68 then getColInMap()= 1 etc
     */
    public int getRowInMap() {
        return doubleToInt(this.getLayoutY(), this.currentMap.getUnit());
    }

    public int getColInMap() {
        return doubleToInt(this.getLayoutX(), this.currentMap.getUnit());
    }

    public boolean isDead() {
        return this.isDead;
    }

    public String getObjType() {
        return objType;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public String toString() {
        return String.format("\n--------------------\n" +
                        "Object Type = %s\n" +
                        "Object Id = %s\n" +
                        "Name = %s\n" +
                        "Position = ( %.2f ; %.2f )\n" +
                        "--------------------\n"
                , objType, objId, objName, this.getLayoutX(), this.getLayoutY());
    }

    public void setObjTypEnum() {
        switch (objType) {
            case "Player":
                objTyp = PLAYER;
            case "Block":
                objTyp = BLOCK;
            case "Bomb-Flame":
                objTyp = BOMBFLAME;
            case "Bomb":
                objTyp = BOMB;
            case "PowerUp":
                objTyp = POWERUP;
        }
    }

    public GameObjects getObjTyp(){
        return objTyp;
    }

    public enum GameObjects {
        PLAYER, BLOCK, BOMBFLAME, BOMB, POWERUP;

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
