package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Move {
    //protected static int[] playerPosition;

    //protected Move(TETile[][] finalWorldFrame) {
        //playerPosition = byog.Core.Wall.getPlayerPosition(finalWorldFrame);
    //}

    public int[] moves(String movement, TETile[][] finalWorldFrame, int[] point, int[][] position,
                      int[] player) {
        for (int i = 0; i < movement.length(); i++) {
            player = move(movement.charAt(i), finalWorldFrame, point, position, player);
        }
        return player;
    }

    public int[] move(char oneMovement, TETile[][] finalWorldFrame, int[] point, int[][] position,
                     int[] playerPosition) {
        int[] newTile = new int[2];
        int[] newnewTile = new int[2];
        int[] original = playerPosition;
        if (oneMovement == 'W' || oneMovement == 'w' || oneMovement == 'A' || oneMovement == 'a'
                || oneMovement == 'S' || oneMovement == 's'
                || oneMovement == 'D' || oneMovement == 'd') {
            if (oneMovement == 'W' || oneMovement == 'w') {
                newTile[0] = playerPosition[0];
                newTile[1] = playerPosition[1] + 1;
                newnewTile[0] = playerPosition[0];
                newnewTile[1] = playerPosition[1] + 2;
            } else if (oneMovement == 'A' || oneMovement == 'a') {
                newTile[0] = playerPosition[0] - 1;
                newTile[1] = playerPosition[1];
                newnewTile[0] = playerPosition[0] - 2;
                newnewTile[1] = playerPosition[1];
            } else if (oneMovement == 'S' || oneMovement == 's') {
                newTile[0] = playerPosition[0];
                newTile[1] = playerPosition[1] - 1;
                newnewTile[0] = playerPosition[0];
                newnewTile[1] = playerPosition[1] - 2;
            } else if (oneMovement == 'D' || oneMovement == 'd') {
                newTile[0] = playerPosition[0] + 1;
                newTile[1] = playerPosition[1];
                newnewTile[0] = playerPosition[0] + 2;
                newnewTile[1] = playerPosition[1];
            }
            if (finalWorldFrame[newTile[0]][newTile[1]].equals(Tileset.FLOOR)
                    || finalWorldFrame[newTile[0]][newTile[1]].equals(Tileset.COIN)
                    || finalWorldFrame[newTile[0]][newTile[1]].equals(Tileset.WATER)) {
                if (finalWorldFrame[newTile[0]][newTile[1]].equals(Tileset.COIN)) {
                    point[0] += 3;
                } else if (finalWorldFrame[newTile[0]][newTile[1]] == Tileset.WATER) {
                    point[0] -= 1;
                }
                playerPosition = newTile;
                finalWorldFrame[newTile[0]][newTile[1]] = Tileset.PLAYER;
                finalWorldFrame[original[0]][original[1]] = Tileset.FLOOR;
            } else if (finalWorldFrame[newTile[0]][newTile[1]].equals(Tileset.TREE)) {
                if (finalWorldFrame[newnewTile[0]][newnewTile[1]].equals(Tileset.FLOOR)
                        || finalWorldFrame[newnewTile[0]][newnewTile[1]].equals(Tileset.COIN)
                        || finalWorldFrame[newnewTile[0]][newnewTile[1]].equals(Tileset.WATER)) {
                    if (finalWorldFrame[newnewTile[0]][newnewTile[1]].equals(Tileset.COIN)) {
                        point[0] += 3;
                    } else if (finalWorldFrame[newnewTile[0]][newnewTile[1]]
                            .equals(Tileset.WATER)) {
                        point[0] -= 1;
                    }
                    point[0] -= 2;
                    playerPosition = newnewTile;
                    finalWorldFrame[newnewTile[0]][newnewTile[1]] = Tileset.PLAYER;
                    finalWorldFrame[original[0]][original[1]] = Tileset.FLOOR;
                }
            }
        }
        byog.Core.Water.updateWater(finalWorldFrame, position);
        return playerPosition;
    }
}
