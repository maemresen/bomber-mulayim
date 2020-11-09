package Blocks;


import GameObject.GameObject;
import javafx.geometry.BoundingBox;

/**
 * Created by maemr on 6.05.2016.
 */
public abstract class Block extends GameObject {



    private BoundingBox[] bounds;

    private int blockType;  // 0 = infrangible, 1 = frangible, 2 = ground

    //Not used for now
    //private Block northBlock, southBlock, eastBlock, westBlock; //Link blocks each other

    public Block(String objId, int blockType, double x, double y) {
        super("Block", objId, "/" + objId, x, y);

        bounds = new BoundingBox[4];

        bounds[0] = new BoundingBox(x + 1, y, pxWidth - 2, 1);
        bounds[1] = new BoundingBox(x + 1, y + pxHeight - 1, pxWidth - 2, 1);
        bounds[2] = new BoundingBox(x, y + 1, 1, pxHeight - 2);
        bounds[3] = new BoundingBox(x + pxWidth - 1, y + 1, 1, pxHeight - 2);

        this.blockType = blockType;

    }


    /*
        To change block type. e.g. Frangible to Ground
     */
    public void changeTo(int blockType) {

        switch (blockType) {
            case 0:
                change(new InfrangibleBlock());
                break;
            case 1:
                change(new FrangibleBlock());
                break;
            case 2:
                change(new GroundBlock());
                break;
        }
    }

    private void change(Block t) {
        this.setBlockType(t.getBlockType());
        this.setObjId(t.getObjId());
        this.setImage(t.getImage());
    }

    public void dead(){
        if(this.isFrangible()){
            this.changeTo(2);
            this.toBack();
            this.isDead = true;
        }
    }

    public boolean isFrangible() {
        return blockType == 1;
    }

    public boolean isInfrangible() {
        return blockType == 0;
    }

    public boolean isGround() {
        return blockType == 2;
    }


    //Get bounds of this block
    //It's very important for move players
    public BoundingBox getBound(int dir) {
        return bounds[dir];
    }

    public int getBlockType() {
        return blockType;
    }

    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }


}
