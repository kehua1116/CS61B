package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Draws a world consisting of hexagonal regions.
 *
 * @source http://sp18.datastructur.es/materials/lab/lab5/drawhexagon.txt
 */
public class HexWorld {

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static int width(int s) {
        return 3 * s - 2;
    }

    private static int height(int s) {
        return 2 * s;
    }

    private static int[] num(int s, int n) {
        int diff = n - s;
        int[] result = new int[2];
        if (diff >= 0) {
            result[0] = width(s) - 2 * diff;
            result[1] = diff;
        } else {
            result[0] = width(s) + 2 * diff + 2;
            result[1] = -diff - 1;
        }
        return result;
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WATER;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.TREE;
            case 5: return Tileset.PLAYER;
            default: return Tileset.NOTHING;
        }
    }

    private static void addRow(TETile[][] place, int s, int n, TETile t, int w, int h) {
        int start = num(s, n)[1] + w;
        int end = start + num(s, n)[0] - 1;
        for (int i = start; i <= end; i++) {
            place[i][n + h] = t;
        }
    }

    public static void addHexagon(TETile[][] place, int s, TETile t, int w, int h) {
        for (int i = 0; i < height(s); i++) {
            addRow(place, s, i, t, w, h);
        }
    }

    private static int adjustWidth(int s) {
        return 2 * s - 1;
    }

    private static int adjustHeight(int s) {
        return s;
    }

    public static void draw(TETile[][] place, int s) {
        addHexagon(place,s,randomTile(),adjustWidth(s) * 2, 0);
        addHexagon(place,s,randomTile(),adjustWidth(s), adjustHeight(s));
        addHexagon(place,s,randomTile(),adjustWidth(s) * 3, adjustHeight(s));
        addHexagon(place,s,randomTile(),adjustWidth(s) * 2, height(s));
        addHexagon(place,s,randomTile(),0,adjustHeight(s) * 2);
        addHexagon(place,s,randomTile(),adjustWidth(s) * 4, adjustHeight(s) * 2);
        addHexagon(place,s,randomTile(),adjustWidth(s),adjustHeight(s) + height(s));
        addHexagon(place,s,randomTile(),adjustWidth(s) * 3, adjustHeight(s) + height(s));
        addHexagon(place,s,randomTile(),0, 2 * adjustHeight(s) + height(s));
        addHexagon(place,s,randomTile(),adjustWidth(s) * 2, 2 * adjustHeight(s) + height(s));
        addHexagon(place,s,randomTile(),adjustWidth(s) * 4, 2 * adjustHeight(s) + height(s));
        addHexagon(place,s,randomTile(),adjustWidth(s) * 1, adjustHeight(s) + 2 * height(s));
        addHexagon(place,s,randomTile(),adjustWidth(s) * 3, adjustHeight(s) + 2 * height(s));
        addHexagon(place,s,randomTile(),0, 2 * adjustHeight(s) + 2 * height(s));
        addHexagon(place,s,randomTile(),adjustWidth(s) * 2, 2 * adjustHeight(s) + 2 * height(s));
        addHexagon(place,s,randomTile(),adjustWidth(s) * 4, 2 * adjustHeight(s) + 2 * height(s));
        addHexagon(place,s,randomTile(),adjustWidth(s) * 1, adjustHeight(s) + 3 * height(s));
        addHexagon(place,s,randomTile(),adjustWidth(s) * 3, adjustHeight(s) + 3 * height(s));
        addHexagon(place,s,randomTile(),adjustWidth(s) * 2,  4 * height(s));
    }

    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        int WIDTH = width(3) * 3 + 3 * 2;
        int HEIGHT = height(3) * 5;
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] place = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i< WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                place[i][j] = Tileset.NOTHING;
            }
        }
        draw(place,3);
        ter.renderFrame(place);
    }

    @Test
    public void test() {
        assertEquals(4, width(2));
        assertEquals(7, width(3));
        assertEquals(10, width(4));
        assertEquals(13, width(5));
        assertEquals(2, num(2, 0)[0]);
        assertEquals(4, num(2, 1)[0]);
        assertEquals(4, num(2, 2)[0]);
        assertEquals(2, num(2, 3)[0]);
        assertEquals(1, num(2, 0)[1]);
        assertEquals(0, num(2, 1)[1]);
        assertEquals(0, num(2, 2)[1]);
        assertEquals(1, num(2, 3)[1]);
    }
}
