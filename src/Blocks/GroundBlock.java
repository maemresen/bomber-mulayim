package Blocks;

/**
 * Created by maemr on 6.05.2016.
 */
public class GroundBlock extends Block {

    public GroundBlock(double x, double y){
        super("ground",2, x, y);
        this.isDead = true;
    }

    public GroundBlock(){
        this(0,0);
    }

}
