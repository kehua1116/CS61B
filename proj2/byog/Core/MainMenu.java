package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;
import java.awt.Color;
import static edu.princeton.cs.introcs.StdDraw.*;

public class MainMenu implements UserInput {
    private static int WIDTH = byog.Core.Game.WIDTH;
    private static int HEIGHT = byog.Core.Game.HEIGHT;

    //draw the main menu.
    public static void draw() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font fontTitle = new Font("Monaco", Font.BOLD, 30);
        Font fontFunction = new Font("Monaco", Font.TYPE1_FONT, 15);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(fontTitle);
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(fontTitle);
        StdDraw.text(WIDTH / 2, HEIGHT * 4 / 5, "Coin Eater");
        StdDraw.setFont(fontFunction);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 5, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 10, "Quit (Q)");
        StdDraw.show();
    }

    public static void mouseTouch(TETile[][] finalWorldFrame) {
        StdDraw.setPenColor(Color.WHITE);
        int x = (int) mouseX();
        int y = (int) mouseY();
        if (x >= WIDTH || y >= HEIGHT || x < 0 || y < 0) {
            return;
        } else if (finalWorldFrame[x][y] == Tileset.FLOOR) {
            StdDraw.text(6, HEIGHT - 1, "FLOOR: tread");
            StdDraw.show();
        } else if (finalWorldFrame[x][y] == Tileset.WALL) {
            StdDraw.text(6, HEIGHT - 1, "WALL: border");
            StdDraw.show();
        } else if (finalWorldFrame[x][y] == Tileset.LOCKED_DOOR) {
            StdDraw.text(6, HEIGHT - 1, "LOCKED_DOOR: cannot open");
            StdDraw.show();
        } else if (finalWorldFrame[x][y] == Tileset.PLAYER) {
            StdDraw.text(6, HEIGHT - 1, "PLAYER: yourself");
            StdDraw.show();
        } else if (finalWorldFrame[x][y] == Tileset.TREE) {
            StdDraw.text(6, HEIGHT - 1, "TREE: jump but punish");
            StdDraw.show();
        } else if (finalWorldFrame[x][y] == Tileset.COIN) {
            StdDraw.text(6, HEIGHT - 1, "COIN: earn points");
            StdDraw.show();
        } else if (finalWorldFrame[x][y] == Tileset.WATER) {
            StdDraw.text(8, HEIGHT - 1, "WATER: through but punish");
        }
    }

    private static void newOption() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontTitle = new Font("Monaco", Font.TYPE1_FONT, 15);
        StdDraw.setFont(fontTitle);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Please Enter Seed");
        StdDraw.show();
    }

    public static void showPoint(int[] point) {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH - 2, HEIGHT - 2, Integer.toString(point[0]));
        StdDraw.show();
    }

    //Ask user for game options(new game/reload/quit)
    @Override
    public int[] keySpecifying(String key, TETile[][] finalWorldFrame,
                              int[] point, int[][] position, int[] player) {
        if (key.equals("N") || key.equals("n")) {
            newOption();
            byog.Core.Game.SEED = Long.parseLong(seedProcessing());
            byog.Core.Start a = new byog.Core.Start();
            a.standardStart(finalWorldFrame, position, player);
            byog.Core.PlayWithKeyBoard b = new byog.Core.PlayWithKeyBoard();
            while (true) {
                while (!hasNextKeyTyped()) {
                    byog.Core.MainMenu.mouseTouch(finalWorldFrame);
                    showPoint(point);
                    byog.Core.Game.ter.renderFrame(finalWorldFrame);
                }
                player = b.moveCharacter(finalWorldFrame, point, position, player);
                byog.Core.Game.ter.renderFrame(finalWorldFrame);
                QuitandLoad.serialize(finalWorldFrame, point, position, player);
                if (point[0] >= 30) {
                    System.out.println("Congratulations! You Win!");
                    QuitandLoad.serialize(finalWorldFrame, point, position, player);
                    System.exit(0);
                } else if (point[0] <= 0) {
                    System.out.println("Sorry, you lose");
                    QuitandLoad.serialize(finalWorldFrame, point, position, player);
                    System.exit(0);
                }
            }
        } else if (key.equals("Q") || key.equals("q")) {
            finalWorldFrame = QuitandLoad.deserialize().world;
            point = QuitandLoad.deserialize().point;
            position = QuitandLoad.deserialize().position;
            player = QuitandLoad.deserialize().player;
            QuitandLoad.serialize(finalWorldFrame, point, position, player);
            System.exit(0);
        } else if (key.equals("L") || key.equals("l")) {
            finalWorldFrame = QuitandLoad.deserialize().world;
            point = QuitandLoad.deserialize().point;
            position = QuitandLoad.deserialize().position;
            player = QuitandLoad.deserialize().player;
            byog.Core.PlayWithKeyBoard b = new byog.Core.PlayWithKeyBoard();
            byog.Core.Game.ter.renderFrame(finalWorldFrame);
            while (true) {
                while (!hasNextKeyTyped()) {
                    byog.Core.MainMenu.mouseTouch(finalWorldFrame);
                    showPoint(point);
                    byog.Core.Game.ter.renderFrame(finalWorldFrame);
                }
                player = b.moveCharacter(finalWorldFrame, point, position, player);
                byog.Core.Game.ter.renderFrame(finalWorldFrame);
                QuitandLoad.serialize(finalWorldFrame, point, position, player);
                if (point[0] >= 30) {
                    System.out.println("Congratulations! You Win!");
                    System.exit(0);
                } else if (point[0] <= 0) {
                    System.out.println("Sorry, you lose");
                    System.exit(0);
                }
            }
        } else {
            keyProcessing();
        }
        return player;
    }

    public void main(TETile[][] finalWorldFrame, int[] point, int[][] position, int[] player) {
        draw();
        String nql = keyProcessing();
        keySpecifying(nql, finalWorldFrame, point, position, player);
    }
}
