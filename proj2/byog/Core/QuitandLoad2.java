package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;

public class QuitandLoad2 implements Serializable {
    private static final long serialVersionUID = 123123123123123L;

    protected TETile[][] world;
    protected int[] point;
    protected int[][] position;
    protected int[] player;

    public QuitandLoad2(TETile[][] world, int[] point, int[][] position, int[] player) {
        this.world = world;
        this.point = point;
        this.position = position;
        this.player = player;
    }

    public QuitandLoad2() {
        world = new TETile[80][30];
        point = new int[1];
        position = new int[30][2];
    }
}
