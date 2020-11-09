package Players;

/**
 * Created by maemr on 7.05.2016.
 */
public class Zombie extends Enemy {

    public Zombie(double x, double y){
        super("zombie",x,y);
    }

    public Zombie(){
        this(0,0);
    }

}
