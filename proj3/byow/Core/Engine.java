package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import org.eclipse.jetty.toolchain.test.jupiter.WorkDir;


import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static byow.Core.MapGenerator.world;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;

    private static boolean resetAvatar = false;

    private static int[] a = new int[]{0, 0};

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public static void moveAvatar(List<Integer> avatar, TETile[][] world) {
        while (StdDraw.hasNextKeyTyped()) {
            char nextLetter = StdDraw.nextKeyTyped();
            System.out.println(nextLetter);
            world[avatar.get(0)][avatar.get(1)] = Tileset.FLOOR;
            if (nextLetter == 'W') {
                world[avatar.get(0)][avatar.get(1) + 1] = Tileset.AVATAR;
            } else if (nextLetter == 'A') {
                world[avatar.get(0) - 1][avatar.get(1)] = Tileset.AVATAR;
            } else if (nextLetter == 'S') {
                world[avatar.get(0)][avatar.get(1) - 1] = Tileset.AVATAR;

            } else if (nextLetter == 'D') {
                world[avatar.get(0) + 1][avatar.get(1)] = Tileset.AVATAR;
            }
        }
    }

    public static String startMainMenu() throws FileNotFoundException {
        String seed = "";
        StdDraw.setCanvasSize(WIDTH * 10, HEIGHT * 10);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        boolean gameOver = false;
        while (!gameOver) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenRadius(5);
            Font fontBig = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(fontBig);
            StdDraw.setPenColor(Color.RED);
            StdDraw.text((double) WIDTH / 2, 25, "CS61B Game!!!");
            StdDraw.text((double) WIDTH / 2, 20, "New Game (N)");
            StdDraw.text((double) WIDTH / 2, 15, "Load Game (L)");
            StdDraw.text((double) WIDTH / 2, 10, "Quit (Q)");
            StdDraw.show();
            if (StdDraw.hasNextKeyTyped()) {
                System.out.println("hi");
                char choice = StdDraw.nextKeyTyped();
                if (choice == 'n') {
                    gameOver = true;
                    Scanner s = new Scanner(System.in);
                    seed = s.next();
                } else if (choice == 'l') {
                    gameOver = true;
                    In reader = new In("seed_data.txt");
                    seed = reader.readLine();
                    a[0] = reader.readInt();
                    reader.readChar();
                    a[1] = reader.readInt();
                    reader.close();
                    resetAvatar = true;

                }
            }

        }
        return seed;
    }

    public void interactWithKeyboard() {
//        return userStr.toString();
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, running both of these:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] interactWithInputString(String input) throws IOException {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
//        if ()
        String seed = "";
        for (char i : input.substring(1).toCharArray()) {
            if (Character.isDigit(i)) {
                seed += i;
            }
        }
        if (seed.length() < 19) {
            while (seed.length() != 19) {
                seed = "0" + seed;
            }
        }

        MapGenerator mapGen = new MapGenerator();
        int oneNine = Integer.parseInt(String.valueOf(seed.charAt(0)));
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        mapGen.drawEmptyWorld();
        for (int i = seed.length() - 1; i > 0; i--) {
            int valueID = Integer.parseInt(seed.charAt(i) + "");
            mapGen.drawRectangle(mapGen.genSquare(i, valueID, oneNine));
        }
        MapGenerator.mergeSquares();
        MapGenerator.refineWorld();
        List<Integer> avatar = MapGenerator.placeAvatar(9, resetAvatar, a[0], a[1]);

        ter.renderFrame(world);

        MapGenerator.moveAvatar(seed, avatar, ter);
        TETile[][] finalWorldFrame = null;
        return world;
    }

    public static void main(String[] args) throws IOException {

        interactWithInputString(startMainMenu());
    }
}
