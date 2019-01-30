package byog.Core;

import byog.TileEngine.TETile;

import static edu.princeton.cs.introcs.StdDraw.*;
public class PlayWithKeyBoard implements UserInput {

    //Process the command key to adjust the position of the character.
    @Override
    public int[] keySpecifying(String key, TETile[][] world,
                               int[] point, int[][] position, int[] player) {
        byog.Core.Move a = new byog.Core.Move();
        if (key.equals("W") || key.equals("S") || key.equals("A") || key.equals("D")
                || key.equals("w") || key.equals("s") || key.equals("a") || key.equals("d")) {
            player = a.moves(key, world, point, position, player);
        } else if (key.equals(":")) {
            keyProcessing();
            MainMenu b = new MainMenu();
            QuitandLoad.serialize(world, point, position, player);
            System.exit(0);
        } else {
            keyProcessing();
        }
        return player;
    }

    public int[] moveCharacter(TETile[][] world, int[] point, int[][] position, int[] player) {
        String target = keyProcessing();
        player = keySpecifying(target, world, point, position, player);
        return player;
    }
}
