package Bombs;

import GameObject.GameObject;

/**
 * Created by maemr on 20.05.2016.
 */
public class BombFlame extends GameObject {

    public BombFlame(String path){
        super("Flame","Bomb-Flame",path);
    }

    public void dead(){
        this.isDead = true;
    }


}
