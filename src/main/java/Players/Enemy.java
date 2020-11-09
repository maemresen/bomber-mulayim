package Players;

/**
 * Created by maemr on 7.05.2016.
 */
public abstract class Enemy extends Player{

    public Enemy(String enemyType,double x,double y){
        super(enemyType, x,y,0,true);
        isEnemy = true;
    }

    public Enemy(String enemyType){
        this(enemyType, 0, 0);
    }
}
