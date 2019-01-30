package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Hallway {

    private static boolean needClosestRight = true;

    // Helper function that returns the information of the leftmost room in the world
    private static int[] leftmost(int[][] rooms) {
        int[] result = new int[5];
        int pos = 0;
        for (int i = 1; i < rooms.length - 1; i++) {
            if (rooms[i][0] < rooms[pos][0]) {
                pos = i;
            }
        }
        result[0] = rooms[pos][0];
        result[1] = rooms[pos][1];
        result[2] = rooms[pos][2];
        result[3] = rooms[pos][3];
        result[4] = pos;
        return result;
    }

    // Calculate the distance between two lower left quarters
    private int[] distance(int[] room1, int[] room2) {
        int[] result = new int[2];
        int x = room2[0] - room1[0];
        int y = room2[1] - room1[1];
        result[0] = x;
        result[1] = y;
        return result;
    }

    // Test if the second room is on the right of the first room
    private boolean right(int[] room1, int[] room2) {
        if (room1[3] == room2[3] && distance(room1, room2)[0] > 0
                && distance(room1, room2)[1] == 0) {
            return true;
        } else if (room1[3] < room2[3] && distance(room1, room2)[0] > 0
                && distance(room1, room2)[1] <= 0
                && room2[1] + room2[3] - room1[1] - room1[3] >= 0) {
            return true;
        } else if (room1[3] > room2[3] && distance(room1, room2)[0] > 0
                && distance(room1, room2)[1] >= 0
                && room2[1] + room2[3] - room1[1] - room1[3] <= 0) {
            return true;
        }
        return false;
    }

    // Test if the second room is above the first room
    private boolean up(int[] room1, int[] room2) {
        if (room1[2] == room2[2] && distance(room1, room2)[1] > 0
                && distance(room1, room2)[0] == 0) {
            return true;
        } else if (room1[2] < room2[2] && distance(room1, room2)[1] > 0
                && distance(room1, room2)[0] <= 0
                && room2[0] + room2[2] - room1[0] - room1[2] >= 0) {
            return true;
        } else if (room1[2] > room2[2] && distance(room1, room2)[1] > 0
                && distance(room1, room2)[0] >= 0
                && room2[0] + room2[2] - room1[0] - room1[2] <= 0) {
            return true;
        }
        return false;
    }

    // Test if the second room is on the left of the first room
    private boolean left(int[] room1, int[] room2) {
        return right(room2, room1);
    }

    private boolean down(int[] room1, int[] room2) {
        return up(room2, room1);
    }

    // Test if the second room is on the northwest of the first room
    private boolean leftup(int[] room1, int[] room2) {
        return !left(room1, room2) && !up(room1, room2)
                && distance(room1, room2)[0] < 0 && distance(room1, room2)[1] > 0;
    }

    // Test if the second room is on the southeast of the first room
    private boolean rightdown(int[] room1, int[] room2) {
        return leftup(room2, room1);
    }

    // Test if the second room is on the southwest of the first room
    private boolean leftdown(int[] room1, int[] room2) {
        return !left(room1, room2) && !up(room1, room2)
                && distance(room1, room2)[0] < 0 && distance(room1, room2)[1] < 0;
    }

    // Test if the second room is on the northeast of the first room
    private boolean rightup(int[] room1, int[] room2) {
        return leftdown(room2, room1);
    }

    private boolean dueNeedRightClosest(int[] room1, int[] room2, int[][] get) {
        for (int i = 0; i < get.length; i++) {
            if ((room1[0] == get[i][0] && room1[1] == get[i][1])
                    || (room2[0] == get[i][0] && room2[1] == get[i][1])) {
                return false;
            }
        }
        return true;
    }

    // Draw the hallway for due up and due down rooms
    // Decrease superposition, e.g. three consecutive rooms only have two hallways
    private void dueUpDown(TETile[][] finalWorldFrame,
                           int[] room1, int[] room2, int[][] get) {
        needClosestRight = dueNeedRightClosest(room1, room2, get);
        int width, smaller;
        if (room1[2] <= room2[2]) {
            width = room1[2];
            smaller = room1[0];
        } else {
            width = room2[2];
            smaller = room2[0];
        }
        int down = room1[1] + room1[3];
        int up = room2[1];
        int locationX = byog.Core.Game.RANDOM.nextInt(width) + smaller;
        int locationY = down;
        int length = up - down;
        boolean[] hasRoom = new boolean[2];
        int room = locationY - 1;
        for (int i = locationY + 1; i < locationY + length; i++) {
            if (finalWorldFrame[locationX][i] == Tileset.FLOOR) {
                hasRoom[0] = true;
                room = i;
                break;
            }
        }
        if (room != locationY - 1 && finalWorldFrame[locationX][room + 1] == Tileset.FLOOR) {
            hasRoom[1] = true;
        }

        if (!hasRoom[1]) {
            for (int i = locationY + 1; i < locationY + length; i++) {
                finalWorldFrame[locationX][i] = Tileset.FLOOR;
            }
        }
    }

    // Draw the hallway for due left and due right rooms
    private void dueLeftRight(TETile[][] finalWorldFrame,
                              int[] room1, int[] room2, int[][] get) {
        needClosestRight = dueNeedRightClosest(room1, room2, get);
        int height, smaller;
        if (room1[3] <= room2[3]) {
            height = room1[3];
            smaller = room1[1];
        } else {
            height = room2[3];
            smaller = room2[1];
        }
        int left = room1[0] + room1[2];
        int right = room2[0];
        int locationY = byog.Core.Game.RANDOM.nextInt(height) + smaller;
        int locationX = left;
        int length = right - left;
        boolean hasRoom = false;
        for (int i = locationX + 1; i < locationX + length; i++) {
            if (finalWorldFrame[i][locationY] == Tileset.FLOOR) {
                hasRoom = true;
            }
        }
        if (!hasRoom) {
            for (int i = locationX + 1; i < locationX + length; i++) {
                finalWorldFrame[i][locationY] = Tileset.FLOOR;
            }
        }
    }

    // Find a room on the left that is closest to the given room
    private int[] closestLeft(int[] room, int[][] get) {
        int[] closest = new int[3];
        closest[0] = byog.Core.Game.WIDTH;
        int dist;
        for (int i = 0; i < get.length; i++) {
            if (leftup(room, get[i])) {
                dist = -distance(room, get[i])[0] - get[i][2] + distance(room, get[i])[1] - room[3];
                if (dist < closest[0]) {
                    closest[0] = dist;
                    closest[1] = 1;
                    closest[2] = i;
                }
            } else if (leftdown(room, get[i])) {
                dist = -distance(room, get[i])[0] - get[i][2]
                        - distance(room, get[i])[1] - get[i][3];
                if (dist < closest[0]) {
                    closest[0] = dist;
                    closest[1] = -1;
                    closest[2] = i;
                }
            } else if (left(room, get[i])) {
                dist = -distance(room, get[i])[0] - get[i][2];
                if (dist < closest[0]) {
                    closest[0] = dist;
                    closest[1] = 0;
                    closest[2] = i;
                }
            }
        }
        return closest;
    }

    // Draw the hallway between the closest room on the left and the given room
    private void drawLeft(TETile[][] finalWorldFrame,
                          int[] room, int[] closest, int[][] get) {
        int[] closestRoom = get[closest[2]];
        int startingX, startingY, endingX, endingY;
        if (closest[1] == 0) {
            return;
        } else if (closest[1] == 1) {
            startingX = closestRoom[0] + closestRoom[2];
            startingY = byog.Core.Game.RANDOM.nextInt(closestRoom[3]) + closestRoom[1];
            endingX = byog.Core.Game.RANDOM.nextInt(room[2]) + room[0];
            endingY = room[1] + room[3];

            for (int i = startingX; i <= endingX; i++) {
                finalWorldFrame[i][startingY] = Tileset.FLOOR;
            }

            for (int i = startingY; i >= endingY; i--) {
                finalWorldFrame[endingX][i] = Tileset.FLOOR;
            }
        } else {
            startingX = byog.Core.Game.RANDOM.nextInt(room[2]) + room[0];
            startingY = room[1];
            endingX = closestRoom[0] + closestRoom[2];
            endingY = byog.Core.Game.RANDOM.nextInt(closestRoom[3]) + closestRoom[1];

            for (int i = startingX; i >= endingX; i--) {
                finalWorldFrame[i][endingY] = Tileset.FLOOR;
            }

            for (int i = startingY; i >= endingY; i--) {
                finalWorldFrame[startingX][i] = Tileset.FLOOR;
            }
        }
    }

    // Find a room on the right that is closest to the given room (leftmost one)
    private int[] closestRight(int[] room, int[][] get) {
        int[] closest = new int[3];
        closest[0] = byog.Core.Game.WIDTH;
        int dist = 0;
        for (int i = 0; i < get.length; i++) {
            if (rightup(room, get[i])) {
                dist = distance(room, get[i])[0] - room[2] + distance(room, get[i])[1] - room[3];
                if (dist < closest[0]) {
                    closest[0] = dist;
                    closest[1] = 1;
                    closest[2] = i;
                }
            } else if (rightdown(room, get[i])) {
                dist = distance(room, get[i])[0] - room[2] - distance(room, get[i])[1] - get[i][3];
                if (dist < closest[0]) {
                    closest[0] = dist;
                    closest[1] = -1;
                    closest[2] = i;
                }
            } else if (right(room, get[i])) {
                dist = -distance(room, get[i])[0] - room[2];
                if (dist < closest[0]) {
                    closest[0] = dist;
                    closest[1] = 0;
                    closest[2] = i;
                }
            }
        }
        return closest;
    }

    // Draw the hallway between the closest room on the left and the given room (leftmost one)
    private void drawRight(TETile[][] finalWorldFrame, int[] leftMostInfo, int[] closest) {
        int startingX, startingY, endingX, endingY;
        if (right(leftMostInfo, closest)) {
            return;
        } else if (rightup(leftMostInfo, closest)) {
            startingX = byog.Core.Game.RANDOM.nextInt(leftMostInfo[2]) + leftMostInfo[0];
            startingY = leftMostInfo[1] + leftMostInfo[3];
            endingX = closest[0];
            endingY = byog.Core.Game.RANDOM.nextInt(closest[3]) + closest[1];

            for (int i = startingX; i <= endingX; i++) {
                finalWorldFrame[i][endingY] = Tileset.FLOOR;
            }

            for (int i = startingY; i <= endingY; i++) {
                finalWorldFrame[startingX][i] = Tileset.FLOOR;
            }
        } else {
            startingX = byog.Core.Game.RANDOM.nextInt(leftMostInfo[2]) + leftMostInfo[0];
            startingY = leftMostInfo[1];
            endingX = closest[0];
            endingY = byog.Core.Game.RANDOM.nextInt(closest[3]) + closest[1];
            for (int i = startingX; i <= endingX; i++) {
                finalWorldFrame[i][endingY] = Tileset.FLOOR;
            }

            for (int i = startingY; i >= endingY; i--) {
                finalWorldFrame[startingX][i] = Tileset.FLOOR;
            }
        }
    }

    // Main method for drawing hallways
    // Make sure that all rooms are connected
    public void drawHallways(TETile[][] finalWorldFrame, int[][] get) {
        for (int i = 0; i < get.length; i++) {
            for (int j = 0; j < get.length; j++) {
                if (right(get[i], get[j])) {
                    dueLeftRight(finalWorldFrame, get[i], get[j], get);
                } else if (up(get[i], get[j])) {
                    dueUpDown(finalWorldFrame, get[i], get[j], get);
                }
            }
        }
        for (int i = 0; i < get.length; i++) {
            int[] closestLeftRoom = closestLeft(get[i], get);
            if (closestLeftRoom[2] == leftmost(get)[4]
                    && closestLeftRoom[2] == leftmost(get)[4]) {
                needClosestRight = false;
            }
            drawLeft(finalWorldFrame, get[i], closestLeftRoom, get);
        }
        if (needClosestRight) {
            int[] leftMostInfo = leftmost(get);
            drawRight(finalWorldFrame, leftMostInfo, closestRight(leftMostInfo, get));
        }
    }

    // Test the positional relation
    @Test
    public void test() {
        int[] room1 = new int[4];
        int[] room2 = new int[4];

        room1[2] = 5;
        room1[3] = 3;
        room2[0] = 6;
        room2[2] = 6;
        room2[3] = 3;
        assertEquals(true, right(room1, room2));
        assertEquals(true, left(room2, room1));

        room2[3] = 4;
        assertEquals(true, right(room1, room2));
        assertEquals(true, left(room2, room1));

        room2[3] = 2;
        assertEquals(true, right(room1, room2));
        assertEquals(true, left(room2, room1));

        room2[0] = 0;
        room2[1] = 4;
        room2[3] = 3;
        assertEquals(true, up(room1, room2));
        assertEquals(true, down(room2, room1));

        room2[3] = 4;
        assertEquals(true, up(room1, room2));
        assertEquals(true, down(room2, room1));

        room2[3] = 2;
        assertEquals(true, up(room1, room2));
        assertEquals(true, down(room2, room1));

        room2[0] = 6;
        room2[1] = 4;
        room2[2] = 6;
        room2[3] = 4;
        assertEquals(true, rightup(room1, room2));
        assertEquals(true, leftdown(room2, room1));

        room1[0] = 0;
        room1[1] = 9;
        room1[2] = 7;
        room1[3] = 5;
        assertEquals(true, rightdown(room1, room2));
        assertEquals(true, leftup(room2, room1));
    }
}
