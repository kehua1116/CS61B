package byog.Core;


import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Room {

    // The information of all added rooms: lower left quarter coordinates, width, height
    //For example: get[0][0] represents the first room's x-coordinates,
    // get[][1] represents y-coordinate, get[][2] represents width, get[][3] represents height.
    protected static int[][] get;
    // Helper variable to store room information
    protected int roomIndex = 0;

    // Randomly generate 15 - 29 rooms in the world
    public static int randomRoom() {
        int result = byog.Core.Game.RANDOM.nextInt(15) + 15;
        get = new int[result][4];
        return result;
    }

    // Randomly generate the location of the lower left quarter of a room
    private int[] randomRoomLocation() {
        int[] result = new int[2];
        int x = byog.Core.Game.RANDOM.nextInt(byog.Core.Game.WIDTH - 2) + 1;
        int y = byog.Core.Game.RANDOM.nextInt(byog.Core.Game.HEIGHT - 2) + 1;
        result[0] = x;
        result[1] = y;
        return result;
    }

    // Randomly generate the width and the height of a room
    private int[] randomRoomSize() {
        int[] result = new int[2];
        result[0] = byog.Core.Game.RANDOM.nextInt(5) + 2;
        result[1] = byog.Core.Game.RANDOM.nextInt(5) + 2;
        return result;
    }

    // Main method for drawing default world and rooms
    public void draw(TETile[][] finalWorldFrame) {
        for (int i = 0; i < finalWorldFrame.length; i++) {
            for (int j = 0; j < finalWorldFrame[0].length; j++) {
                finalWorldFrame[i][j] = Tileset.NOTHING;
            }
        }
        int numRoom = randomRoom();
        drawRooms(finalWorldFrame, numRoom);
    }

    // Given the valid places of the world and the number of rooms, draw rooms
    private void drawRooms(TETile[][] finalWorldFrame, int numRoom) {
        boolean[][] isValid = new boolean[finalWorldFrame.length][finalWorldFrame[0].length];

        for (int i = 1; i < finalWorldFrame.length; i++) {
            for (int j = 1; j < finalWorldFrame[0].length; j++) {
                isValid[i][j] = true;
            }
        }
        for (int i = 0; i < numRoom; i++) {
            drawRoom(finalWorldFrame, isValid);
        }
    }

    // Draw a single room, make sure it doesn't coincide with others, and store the information
    private void drawRoom(byog.TileEngine.TETile[][] finalWorldFrame, boolean[][] isValid) {
        int[] size = randomRoomSize();
        int[] position = randomRoomLocation();

        while (position[0] + size[0] >= finalWorldFrame.length - 1
                || position[1] + size[1] >= finalWorldFrame[0].length - 1
                || !checkRoomPositionValid(isValid, position, size)) {
            size = randomRoomSize();
            position = randomRoomLocation();
        }
        get[roomIndex][0] = position[0];
        get[roomIndex][1] = position[1];
        get[roomIndex][2] = size[0];
        get[roomIndex][3] = size[1];
        roomIndex += 1;
        for (int i = position[0]; i <= position[0] + size[0]; i++) {
            for (int j = position[1]; j <= position[1] + size[1]; j++) {
                finalWorldFrame[i][j] = Tileset.FLOOR;
                isValid[i][j] = false;
            }
        }
        for (int i = position[0] - 1; i <= position[0] + size[0] + 1; i++) {
            isValid[i][position[1] - 1] = false;
            isValid[i][position[1] + size[1] + 1] = false;
        }
        for (int i = position[1] - 1; i <= position[1] + size[1] + 1; i++) {
            isValid[position[0] - 1][i] = false;
            isValid[position[0] + size[0] + 1][i] = false;
        }
    }

    // Helper function that helps check if the position of a room is valid
    private boolean checkRoomPositionValid(boolean[][] isValid, int[] position, int[] size) {
        boolean valid = true;
        for (int i = position[0]; i <= position[0] + size[0]; i++) {
            for (int j = position[1]; j <= position[1] + size[1]; j++) {
                if (!isValid[i][j]) {
                    valid = false;
                }
            }
        }
        return valid;
    }
}
