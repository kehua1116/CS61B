package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Water {
    private int[] drawOneWater(TETile[][] world) {
        int[] position = new int[2];
        int x = byog.Core.Game.RANDOM.nextInt(byog.Core.Game.WIDTH - 2) + 1;
        int y = byog.Core.Game.RANDOM.nextInt(byog.Core.Game.HEIGHT - 2) + 1;
        while (world[x][y] != Tileset.FLOOR) {
            x = byog.Core.Game.RANDOM.nextInt(byog.Core.Game.WIDTH - 2) + 1;
            y = byog.Core.Game.RANDOM.nextInt(byog.Core.Game.HEIGHT - 2) + 1;
        }
        world[x][y] = Tileset.WATER;
        position[0] = x;
        position[1] = y;
        return position;
    }

    public void drawWaters(TETile[][] world, int[][] positions) {
        for (int i = 0; i < 30; i++) {
            int[] position = drawOneWater(world);
            positions[i][0] = position[0];
            positions[i][1] = position[1];
        }
    }

    public static void updateWater(TETile[][] world, int[][] position) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[i].length; j++) {
                for (int k = 0; k < position.length; k++) {
                    if (world[position[k][0]][position[k][1]] != Tileset.PLAYER) {
                        world[position[k][0]][position[k][1]] = Tileset.WATER;
                    }
                }
            }
        }

    }
}
