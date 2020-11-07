package Bombs;

/**
 * Created by maemr on 7.05.2016.
 */

    public class FlameSet {

    /*

        * This class is for creating a flame for a  bomb.

     */

    private BombFlame[][] flames;

    /*

     * 0 -> ||||||||||||||| Λ
     * 1 -> ||||||||||||||| v
     * 2 -> --------------- <
     * 3 -> --------------- >

    */

    public FlameSet(int h, int w) {

        flames = new BombFlame[h][w];
        for (int i = 0; i < w; i++) {
            if (i == w - 1) {
                /*
                    These are body flame textures ( - )
                 */
                flames[3][i] = FlameSet.getFlame("rightHead");
                flames[2][i] = FlameSet.getFlame("leftHead");
                flames[0][i] = FlameSet.getFlame("upHead");
                flames[1][i] = FlameSet.getFlame("downHead");
            } else {
                /*
                    These are last flame textures ( > )
                 */
                flames[3][i] = FlameSet.getFlame("hor");
                flames[2][i] = FlameSet.getFlame("hor");
                flames[0][i] = FlameSet.getFlame("ver");
                flames[1][i] = FlameSet.getFlame("ver");
            }
        }
    }

    private static BombFlame getFlame(String dir) {
        return new BombFlame("/flamebody" + dir);
    }

    public BombFlame[][] getFlames() {
        return flames;
    }
}
