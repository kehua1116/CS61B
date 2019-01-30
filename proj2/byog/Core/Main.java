package byog.Core;

import byog.TileEngine.TETile;

/**
 * This is the main entry point for the program. This class simply parses
 * the command line inputs, and lets the byog.Core.Game class take over
 * in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            byog.Core.Game game = new byog.Core.Game();
            byog.Core.Game.ter.initialize(byog.Core.Game.WIDTH, byog.Core.Game.HEIGHT);
            TETile[][] finalWorldFrame = game.playWithInputString(args[0]);
            byog.Core.Game.ter.renderFrame(finalWorldFrame);
            System.out.println(game.toString());
        } else {
            byog.Core.Game game = new byog.Core.Game();
            game.playWithKeyboard();
        }
    }
}
