package byog.Core;

import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

public interface UserInput {

    int[] keySpecifying(String key, TETile[][] world, int[] point, int[][] position, int[] player);

    default String keyProcessing() {
        String target2 = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char target = StdDraw.nextKeyTyped();
                target2 += target;
                break;
            }
        }
        return target2;
    }

    default String seedProcessing() {
        String target2 = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char target = StdDraw.nextKeyTyped();
                if (!(target >= '0' && target <= '9')) {
                    break;
                }
                target2 += target;
            }
        }
        return target2;
    }
}
