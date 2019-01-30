package byog.Core;

import byog.TileEngine.TETile;

import java.util.Random;

public class Start {
    protected void standardStart(TETile[][] finalWorldFrame, int[][] position, int[] player) {
        Game.RANDOM = new Random(byog.Core.Game.SEED);
        byog.Core.Room a = new byog.Core.Room();
        a.draw(finalWorldFrame); //draw room
        //according to the predetermined rooms, draw hallways
        byog.Core.Hallway b = new byog.Core.Hallway();
        b.drawHallways(finalWorldFrame, byog.Core.Room.get);
        //according to predetermined rooms and hallways, draw walls.
        byog.Core.Wall c = new byog.Core.Wall();
        c.drawWall(finalWorldFrame);
        c.drawDoor(finalWorldFrame, player);
        byog.Core.Tree d = new byog.Core.Tree();
        d.drawTrees(finalWorldFrame);
        byog.Core.Coin e = new byog.Core.Coin();
        e.drawCoins(finalWorldFrame);
        byog.Core.Water f = new byog.Core.Water();
        f.drawWaters(finalWorldFrame, position);
    }

    //Move Player to the correct position. (PlaywithString)
    protected int[] drawMove(String movement, TETile[][] world,
                            int[] point, int[][] position, int[] player) {
        byog.Core.Move a = new byog.Core.Move();
        player = a.moves(movement, world, point, position, player);
        return player;
    }
}
