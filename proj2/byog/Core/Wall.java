package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Wall {
    protected static int width = byog.Core.Game.WIDTH;
    protected static int height = byog.Core.Game.HEIGHT;
    //protected static int[] playerPosition = new int[2];

    //Set the player's position according to the position of the door.
    public static int[] getPlayerPosition(TETile[][] world, int[] player) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (world[i][j] == Tileset.PLAYER) {
                    player[0] = i;
                    player[1] = j;
                }
            }
        }
        return player;
    }

    //check if the given tile is Tile.Floor.
    public boolean checkFloor(int x, int y, TETile[][] world) {
        return world[x][y] == Tileset.FLOOR;
    }

    //Generate walls around a single unit of floor.
    public void drawOneWall(int x, int y, TETile[][] world) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (!checkFloor(i, j, world)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    //Main draw method that draw walls around all the rooms and hallways.
    public void drawWall(TETile[][] world) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (checkFloor(i, j, world)) {
                    drawOneWall(i, j, world);
                }
            }
        }
    }

    //Main draw method that draws the door and the player.
    public void drawDoor(TETile[][] world, int[] player) {
        while (true) {
            int x = byog.Core.Game.RANDOM.nextInt(byog.Core.Game.WIDTH - 2) + 1;
            int y = byog.Core.Game.RANDOM.nextInt(byog.Core.Game.HEIGHT - 2) + 1;
            if (world[x][y] == Tileset.FLOOR) {
                player[0] = x;
                player[1] = y;
                if (world[x - 1][y] == Tileset.WALL) {
                    world[x - 1][y] = Tileset.LOCKED_DOOR;
                    world[x][y] = Tileset.PLAYER;
                    break;
                } else if (world[x + 1][y] == Tileset.WALL) {
                    world[x + 1][y] = Tileset.LOCKED_DOOR;
                    world[x][y] = Tileset.PLAYER;
                    break;
                } else if (world[x][y - 1] == Tileset.WALL) {
                    world[x][y - 1] = Tileset.LOCKED_DOOR;
                    world[x][y] = Tileset.PLAYER;
                    break;
                } else if (world[x][y + 1] == Tileset.WALL) {
                    world[x][y + 1] = Tileset.LOCKED_DOOR;
                    world[x][y] = Tileset.PLAYER;
                    break;
                }
            }
        }
    }
}

