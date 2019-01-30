package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.util.Random;


public class Game {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    protected static long SEED;
    protected static Random RANDOM;
    protected static String MOVEMENT;
    static TERenderer ter = new TERenderer();


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    /**
     * Helper function used for getting seed, the digital part of the input
     */
    private static long getSeed(String input) {
        String seed = "";
        for (int i = 0; i < input.length(); i++) {
            char num = input.charAt(i);
            if (num >= '0' && num <= '9') {
                seed += num;
            }
        }
        long result = Long.parseLong(seed);
        return result;
    }

    //Process input string (PlaywithString)
    private static String getMovement(String input) {
        String movement = "";
        for (int i = 1; i < input.length() - 1; i++) {
            if (input.charAt(i) > ':') {
                movement += input.charAt(i);
            }
        }
        if (input.charAt(input.length() - 1) != 'S' && input.charAt(input.length() - 1) != 'Q') {
            movement += input.charAt(input.length() - 1);
        }
        return movement;
    }

    public static TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        MOVEMENT = getMovement(input);
        int[] point = new int[1];
        point[0] = 10;
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        int[][] position = new int[30][2];
        int[] player = new int[2];
        byog.Core.MainMenu b = new byog.Core.MainMenu();
        if (input.charAt(0) == 'N'
                || input.charAt(0) == 'n') {
            SEED = getSeed(input);
            byog.Core.Start a = new byog.Core.Start();
            a.standardStart(finalWorldFrame, position, player);
            player = a.drawMove(MOVEMENT, finalWorldFrame, point, position, player);
            byog.Core.QuitandLoad.serialize(finalWorldFrame, point, position, player);
            if (point[0] >= 30) {
                System.out.println("Congratulations! You Win!");
            } else if (point[0] <= 0) {
                System.out.println("Sorry, you lose");
            }
        } else if (input.charAt(0) == 'L'
                || input.charAt(0) == 'l') {
            finalWorldFrame = QuitandLoad.deserialize().world;
            point = QuitandLoad.deserialize().point;
            position = QuitandLoad.deserialize().position;
            player = QuitandLoad.deserialize().player;
            byog.Core.Start c = new byog.Core.Start();
            player = c.drawMove(MOVEMENT, finalWorldFrame, point, position, player);
            QuitandLoad.serialize(finalWorldFrame, point, position, player);
            if (point[0] >= 30) {
                System.out.println("Congratulations! You Win!");
            } else if (point[0] <= 0) {
                System.out.println("Sorry, you lose");
            }
        }
        return finalWorldFrame;
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        int[] point = new int[1];
        point[0] = 10;
        int[][] position = new int[30][2];
        int[] player = new int[2];
        byog.Core.MainMenu a = new byog.Core.MainMenu();
        a.main(finalWorldFrame, point, position, player);
    }
}
