package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        String output = "";
        for (int i = 0; i < n; i++) {
            int num = byog.Core.RandomUtils.uniform(rand, 0, 26);
            output += CHARACTERS[num];
        }
        return output;
    }

    public void drawFrame(String s) {
        StdDraw.clear();
        StdDraw.show();
        StdDraw.pause(500);
        Font font = new Font(s, Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.red);
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.show();
        StdDraw.pause(1000);
    }

    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i++) {
            char target = letters.charAt(i);
            String target2 = Character.toString(target);
            drawFrame(target2);
        }
    }

    public String solicitNCharsInput(int n) {
        String output = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char target = StdDraw.nextKeyTyped();
                output += target;
            }
            if (output.length() == n) {
                break;
            }
        }
        return output;
    }

    public void startGame() {
        round = 1;
        while (true) {
            String message = "Round: " + round;
            String fail = "Game Over! You made it to the Round " + round;
            drawFrame(message);
            String target = generateRandomString(round);
            flashSequence(target);
            drawFrame("");
            String actual = solicitNCharsInput(round);
            if (actual.equals(target)) {
                round += 1;
            } else {
                drawFrame(fail);
                break;
            }
        }
    }

}
