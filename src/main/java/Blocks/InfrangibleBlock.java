package Blocks;

/**
 * Created by maemr on 6.05.2016.
 */
public class InfrangibleBlock extends Block{

    public InfrangibleBlock(double x, double y){
        super("infrangible",0, x, y);
    }
    public InfrangibleBlock(){
        this(0,0);
    }

}
