package Players;

/**
 * Created by maemr on 7.05.2016.
 */
public class Hero extends Player {



    public Hero(String objName, double x, double y, int cType,boolean isAI) {
        super("hero"+cType, objName, "gif",x, y, cType,isAI);
        maxBomb = 1;
        currBomb = 1;
        range = 1;
        isEnemy = false;

    }

    public Hero(String objName, int cType,boolean isAI) {
        this(objName, 0, 0, cType,isAI);
    }

}
