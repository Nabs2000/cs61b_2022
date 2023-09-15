package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.nio.channels.Pipe;
import java.util.Random;
import java.util.Scanner;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    // Plan
    // I need to make a plus sign based on the size
    // The plus consists of s rows with s walls (for beginning), s rows with (s * 3) walls (for middle),
    // and s rows with s walls (for end)

    // I should start at the middle and build my plus from there (middle is width/2, height/2)

    private static class Point {
        int x;
        int y;
        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static TETile[][] addPlus(int s) {
        // First, find middle point
        Point middle = new Point(WIDTH / 2, HEIGHT / 2);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        boolean leftCheck;
        boolean rightCheck;
        boolean upCheck;
        boolean downCheck;
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        // first set of rows
        for (int row = middle.x; row < middle.x + s; row++) {
            for (int col = middle.y; col < middle.y + s; col++) {
                leftCheck = col < 0;
                rightCheck = col > HEIGHT;
                upCheck = row < 0;
                downCheck = row > WIDTH;
                if (leftCheck || rightCheck || upCheck || downCheck) {
                    continue;
                }
                world[row][col] = Tileset.WALL;
            }
        }
        // middle set of rows
        Point startMidRows = new Point(middle.x - s, middle.y - s);
        for (int row = startMidRows.x; row < startMidRows.x + (s * 3); row++) {
            for (int col = startMidRows.y; col < startMidRows.y + s; col++) {
                leftCheck = col < 0;
                rightCheck = col > HEIGHT;
                upCheck = row < 0;
                downCheck = row > WIDTH;
                if (leftCheck || rightCheck || upCheck || downCheck) {
                    continue;
                }
                world[row][col] = Tileset.WALL;
            }
        }
        Point startEndRows = new Point(middle.x, startMidRows.y - s);
        for (int row = startEndRows.x; row < startEndRows.x + s; row++) {
            for (int col = startEndRows.y; col < startEndRows.y + s; col++) {
                System.out.println(row + " " + col);
                leftCheck = col < 0;
                rightCheck = col > HEIGHT;
                upCheck = row < 0;
                downCheck = row > WIDTH;
                if (leftCheck || rightCheck || upCheck || downCheck) {
                    continue;
                }
                world[row][col] = Tileset.WALL;
            }
        }
        // last set of rows
        return world;
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Hello Hamza! Enter a size for your plus :D");
        int n = reader.nextInt(); // Scans the next token of the input as an int.
        while (n > HEIGHT) {
            reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("Sorry Hamza! Wrong number... D Please enter another one:");
            n = reader.nextInt();
        }
        reader.close();
        TETile[][] world = addPlus(n);
        // draws the world to the screen

        ter.renderFrame(world);
    }
}