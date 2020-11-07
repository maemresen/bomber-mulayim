package Blocks;

/**
 * Created by maemr on 6.05.2016.
 */
public class FrangibleBlock extends Block{

    public FrangibleBlock(double x, double y){
        super("frangible",1,x,y);
    }

    public FrangibleBlock(){
        this(0,0);
    }
}
