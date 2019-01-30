package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Tree {
    private void drawOneTree(TETile[][] world) {
        int x = byog.Core.Game.RANDOM.nextInt(byog.Core.Game.WIDTH - 2) + 1;
        int y = byog.Core.Game.RANDOM.nextInt(byog.Core.Game.HEIGHT - 2) + 1;
        while (world[x][y] != Tileset.FLOOR) {
            x = byog.Core.Game.RANDOM.nextInt(byog.Core.Game.WIDTH - 2) + 1;
            y = byog.Core.Game.RANDOM.nextInt(byog.Core.Game.HEIGHT - 2) + 1;
        }
        world[x][y] = Tileset.TREE;
    }

    public void drawTrees(TETile[][] world) {
        for (int i = 0; i < 30; i++) {
            drawOneTree(world);
        }
    }
}
