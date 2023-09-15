package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MapGenerator {
    // This class will generate our world, which will have rooms as rectangles.
    // Thus, we should have a rectangle class
    // We also need to know where to start drawing the rectangles, and we can use the Point class for this
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    static TETile[][] world = new TETile[WIDTH][HEIGHT];

    int mapIdx = 0;
    HashMap<Integer, List<Point>> map = new HashMap<>();

    private static class Rectangle {
        Point botCorner;
        Point startPos;
        int length;
        int width;
        int ID;
        private Rectangle(int length, int width, Point startPos) {
            this.length = length;
            this.width = width;
            this.startPos = startPos;
            this.botCorner = this.startPos.shift(length, width);
            // Draws the widths
        }
        public Point getStartPos() {
            return startPos;
        }
        public Point getBotCorner() {
            return botCorner;
        }
        public Point getCenter() {
            return new Point(width + width / 2, length + length / 2);
        }
    }
    private static class Point {
        int x;
        int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public Point shift(int dx, int dy) {
            return new Point(x + dx, y + dy);
        }

        public static Point add(Point one, Point two) {
            return new Point(one.x + two.x, one.y + two.y);
        }
        public static boolean equals(Point one, Point two) {
            return (one.x == two.x && one.y == two.y);
        }
        public double[][] getHyps() {
            double[][] hyps = new double[WIDTH][HEIGHT];
            for (int x = 0; x < hyps.length; x++) {
                for (int y = 0; y < hyps[x].length; y++) {
                    if (world[x][y] == Tileset.WALL || world[x][y] == Tileset.FLOOR || world[x][y] == Tileset.UNLOCKED_DOOR) {
                        hyps[x][y] = 100000;
                    } else {
                        hyps[x][y] = Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
                    }
//                    if (hyps[x][y] == 0.0) {
//                        System.out.println(x + " " + y + " " + this.x + " " + this.y);
//                        System.out.println("hehexd");
//                        System.out.println(hyps[x][y]);
//                    }
//                    System.out.println(x + " " + y + " " + this.x + " " + this.y);
//                    System.out.println(hyps[x][y]);
                }
            }
            return hyps;

        }
        public void printPoint() {
            List<Integer> pt = new ArrayList<>();
            pt.add(this.x);
            pt.add(this.y);
            System.out.println(pt);
        }


    }
    public void drawEmptyWorld() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        for (int x = 5; x < WIDTH; x+= 10) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.FLOOR;
                world[x + 1][y] = Tileset.WALL;
                world[x - 1][y] = Tileset.WALL;
            }
        }
        for (int y = 5; y < HEIGHT; y+= 10) {
            for (int x = 0; x < WIDTH; x++) {
                world[x][y] = Tileset.FLOOR;
                world[x][y + 1] = Tileset.WALL;
                world[x][y - 1] = Tileset.WALL;
            }
        }
        for (int x = 5; x < WIDTH; x+= 10) {
            for (int y = 5; y < HEIGHT; y += 10) {
                world[x][y + 1] = Tileset.FLOOR;
                world[x][y - 1] = Tileset.FLOOR;
            }
        }
    }

    public static void refineWorld() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT - 2; y++) {
                if (world[x][y] == Tileset.FLOOR && world[x][y + 1] == Tileset.WALL && world[x][y + 2] == Tileset.FLOOR) {
                    world[x][y+1] = Tileset.FLOOR;
                }
            }
        }

        for (int x = 0; x < WIDTH - 2; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (world[x][y] == Tileset.FLOOR && world[x + 1][y] == Tileset.WALL && world[x + 2][y] == Tileset.FLOOR) {
                    world[x + 1][y] = Tileset.FLOOR;
                }
            }
        }
    }

    public void drawRectangle(Rectangle r) {
        Point startPos = r.getStartPos();
        Point botCorner = r.getBotCorner();
        // Checks the edges and avoids OOB errors
        if (botCorner.y >= HEIGHT) {
            botCorner.y = HEIGHT - 1;
        }
        if (startPos.y >= HEIGHT) {
            startPos.y = HEIGHT - 1;
        }
        if (startPos.x >= WIDTH) {
            startPos.y = WIDTH - 1;
        }
        if (botCorner.x >= WIDTH) {
            botCorner.x = WIDTH - 1;
        }
//        System.out.println(startPos.x + " " + startPos.y);
//        System.out.println(botCorner.x + " " + botCorner.y);

        // Draw a door for one roo
        for (int x = startPos.x; x <= botCorner.x; x++) {
            for (int y = startPos.y; y < botCorner.y; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }

        for (int x = startPos.x; x <= botCorner.x; x++) {
            world[x][startPos.y] = Tileset.WALL;
            world[x][botCorner.y] = Tileset.WALL;
        }
        // Draws the heights
        for (int y = startPos.y; y < botCorner.y; y++) {
            world[startPos.x][y] = Tileset.WALL;
            world[botCorner.x][y] = Tileset.WALL;
        }
        // Draws doors at corresponding boundaries
//        ArrayList<Point> a = new ArrayList<>();
//        int avgX = (int) Math.floor((startPos.x + botCorner.x)/2);
//        if(!(startPos.y == 0 || startPos.y == HEIGHT - 1)) {
//            world[avgX][startPos.y] = Tileset.UNLOCKED_DOOR;
//            a.add(new Point(avgX, startPos.y));
////            map.get(mapIdx).add(new Point(avgX, startPos.y));
//
//
//        }
//        if (!(botCorner.y == 0 || botCorner.y == HEIGHT - 1)) {
//            world[avgX][botCorner.y] = Tileset.UNLOCKED_DOOR;
//            a.add(new Point(avgX, botCorner.y));
////            map.get(mapIdx).add(new Point(avgX, botCorner.y));
//        }
//
//
//
//        map.put(mapIdx, a);
//        mapIdx++;
//
//        for (Point p: a) {
//            p.printPoint();
//        }
//        System.out.println();


    }

    private Point findTopLeft (int ID) {

        return new Point((ID % 6) * 10, Math.floorDiv(ID, 6) * 10);
    }

    private Point findminiTopLeft (int ID) {

        return new Point((ID % 3) * 3, Math.floorDiv(ID, 3) * 3);
    }


    public Rectangle genSquare(int squareID, int valueID, int oneNine) {
        int length =  valueID*10;
//        System.out.println(length + " " + valueID + " " + oneNine);
//        System.out.println(10 - findminiTopLeft(valueID).x);
        return new Rectangle(Math.min(length, 10 - findminiTopLeft(valueID).x),Math.min(length, 10 - findminiTopLeft(valueID).y), Point.add(findminiTopLeft(valueID), findTopLeft(squareID)));
    }
    public static void mergeSquares() {
        for (int i = 10; i < WIDTH; i += 10) {
            for (int j = 0; j < HEIGHT; j++) {
                if(world[i - 1][j].equals(Tileset.FLOOR) && world[i + 1][j].equals(Tileset.FLOOR)) {
                    world[i][j] = Tileset.FLOOR;
 //                   world[i + 1][j] = Tileset.FLOOR;
                }
            }

        }
        for (int i = 10; i < HEIGHT; i += 10) {
            for (int j = 0; j < WIDTH; j++) {

                if (world[j][i - 1].equals(Tileset.FLOOR) && world[j][i + 1].equals(Tileset.FLOOR)) {
                    world[j][i] = Tileset.FLOOR;
   //                 world[j][i + 1] = Tileset.FLOOR;
                }
            }
        }
    }
    public static void genHallway(Point start, Point target) {
        List<Point> marked = new ArrayList<>();
        marked.add(start);
        double[][] distsToTarget = target.getHyps();
        genHallway(start, distsToTarget, marked);
    }
    private static void genHallway(Point start, double[][] distsToTarget, List<Point> marked) {
//        while (!Point.equals(one, two)) {
//            if (one.x != two.x) {
//                if (one.x > two.x) {
//                    one.x -= 1;
//                } else {
//                    one.x += 1;
//                }
//                world[one.x][one.y] = Tileset.GRASS;
//            }
//            if (one.y != two.y) {
//                if (one.y > two.y) {
//                    one.y -= 1;
//                } else {
//                    one.y += 1;
//                }
//                world[one.x][one.y] = Tileset.GRASS;
//            }
//
//        double[][] distsToTarget = target.getHyps();
        if (distsToTarget[start.x][start.y] == 0.0) {
            System.out.println("hehe xd");
            return;
        }
        double left;
        double right;
        double up;
        double down;
        if(start.x == 0) {
            left = 1000;
        } else {
            left = distsToTarget[start.x - 1][start.y];
        }

        if (start.x == WIDTH) {
            right = 1000;
        } else {
            right = distsToTarget[start.x + 1][start.y];
        }

        if (start.y == HEIGHT) {
            up = 1000;
        } else {
            up = distsToTarget[start.x][start.y + 1];
        }

        if (start.y == 0) {
            down = 1000;
        } else {
            down = distsToTarget[start.x][start.y - 1];
        }
        List<Double> sorted = new ArrayList<>();
        sorted.add(left);
        sorted.add(right);
        sorted.add(up);
        sorted.add(down);
        double smallest = left;
        int smallestInd = 0;
        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i) < smallest) {
                smallestInd = i;
                smallest = sorted.get(i);
            }
        }
//        System.out.println(start.x + " " + start.y);
//        System.out.println(smallest + " " + sorted);
        System.out.println(start.x + " " + start.y + " " + smallest);
        if (world[start.x][start.y].equals(Tileset.FLOOR)) {
            System.out.println("Floor");
        } else if (world[start.x][start.y].equals(Tileset.WALL)) {
            System.out.println("Wall");
        } else if (world[start.x][start.y].equals(Tileset.NOTHING)) {
            System.out.println("Nothing");
        } else {
            System.out.println("Door");
        }
        distsToTarget[start.x][start.y] = 10000;
        if (smallestInd == 0) {
            Point leftPt = new Point(start.x - 1, start.y);
            marked.add(leftPt);
            world[start.x + 1][start.y] = Tileset.FLOOR;
            genHallway(leftPt, distsToTarget, marked);
//            world[start.x - 1][start.y] = Tileset.FLOOR;
//            genHallway(leftPt, distsToTarget, marked);
        }
        else if (smallestInd == 1) {
            Point rightPt = new Point(start.x + 1, start.y);
            marked.add(rightPt);
            world[start.x + 1][start.y] = Tileset.FLOOR;
            genHallway(rightPt, distsToTarget, marked);
        }
        else if (smallestInd == 2) {
            Point upPt = new Point(start.x, start.y + 1);
            marked.add(upPt);
            world[start.x + 1][start.y] = Tileset.FLOOR;
            genHallway(upPt, distsToTarget, marked);
//            world[start.x][start.y + 1] = Tileset.FLOOR;
//            genHallway(upPt, distsToTarget, marked);
        } else {
            Point downPt = new Point(start.x, start.y - 1);
            marked.add(downPt);
            world[start.x + 1][start.y] = Tileset.FLOOR;
            genHallway(downPt, distsToTarget, marked);
//            world[start.x][start.y - 1] = Tileset.FLOOR;
//            genHallway(downPt, distsToTarget, marked);
        }
    }

    public static void moveAvatar(String seed, List<Integer> avatar, TERenderer tr) throws IOException {
        boolean gameOver = false;
        while (!gameOver) {
            if (StdDraw.hasNextKeyTyped()) {
                char nextLetter = StdDraw.nextKeyTyped();
                if (nextLetter == 'w') {
                    if (world[avatar.get(0)][0] != Tileset.WALL && avatar.get(1) == HEIGHT - 1) {
                        world[avatar.get(0)][0] = Tileset.AVATAR;
                        world[avatar.get(0)][HEIGHT - 1] = Tileset.FLOOR;
                        avatar.set(1, 0);
                    } else {
                        world[avatar.get(0)][avatar.get(1)] = Tileset.AVATAR;
                    }
                    if (world[avatar.get(0)][avatar.get(1) + 1] != Tileset.WALL && avatar.get(1) < HEIGHT) {
                        world[avatar.get(0)][avatar.get(1) + 1] = Tileset.AVATAR;
                        world[avatar.get(0)][avatar.get(1)] = Tileset.FLOOR;
                        avatar.set(1, avatar.get(1) + 1);
                    }

                } else if (nextLetter == 'a') {
                    if (world[WIDTH - 1][avatar.get(1)] != Tileset.WALL && avatar.get(0) == 0) {
                        world[WIDTH - 1][avatar.get(1)] = Tileset.AVATAR;
                        world[0][avatar.get(1)] = Tileset.FLOOR;
                        avatar.set(0, WIDTH - 1);
                    } else {
                        world[avatar.get(0)][avatar.get(1)] = Tileset.AVATAR;
                    }
                    if (world[avatar.get(0) - 1][avatar.get(1)] != Tileset.WALL && avatar.get(0) > 0) {
                        world[avatar.get(0) - 1][avatar.get(1)] = Tileset.AVATAR;
                        world[avatar.get(0)][avatar.get(1)] = Tileset.FLOOR;
                        avatar.set(0, avatar.get(0) - 1);
                    }
                } else if (nextLetter == 's') {
                    if (world[avatar.get(0)][HEIGHT - 1] != Tileset.WALL && avatar.get(1) == 0) {
                        world[avatar.get(0)][HEIGHT - 1] = Tileset.AVATAR;
                        world[avatar.get(0)][0] = Tileset.FLOOR;
                        avatar.set(1, HEIGHT - 1);
                    } else {
                        world[avatar.get(0)][avatar.get(1)] = Tileset.AVATAR;
                    }
                    if (world[avatar.get(0)][avatar.get(1) - 1] != Tileset.WALL && avatar.get(1) > 0) {
                        world[avatar.get(0)][avatar.get(1) - 1] = Tileset.AVATAR;
                        world[avatar.get(0)][avatar.get(1)] = Tileset.FLOOR;
                        avatar.set(1, avatar.get(1) - 1);
                        System.out.println(avatar.get(1));
                    }
                } else if (nextLetter == 'd') {
                    if (world[0][avatar.get(1)] != Tileset.WALL && avatar.get(0) == WIDTH - 1) {
                        world[0][avatar.get(1)] = Tileset.AVATAR;
                        world[WIDTH - 1][avatar.get(1)] = Tileset.FLOOR;
                        avatar.set(0, 0);
                    } else {
                        world[avatar.get(0)][avatar.get(1)] = Tileset.AVATAR;
                    }
                    if (world[avatar.get(0) + 1][avatar.get(1)] != Tileset.WALL && avatar.get(0) < WIDTH) {
                        world[avatar.get(0) + 1][avatar.get(1)] = Tileset.AVATAR;
                        world[avatar.get(0)][avatar.get(1)] = Tileset.FLOOR;
                        avatar.set(0, avatar.get(0) + 1);
                        System.out.println(avatar.get(0));
                    }
                }
                else if (nextLetter == 'q') {
                    gameOver = true;
                    FileWriter fileWrite = new FileWriter("seed_data.txt");
                    fileWrite.write(seed + "\n");
                    fileWrite.write(avatar.get(0) + " " + avatar.get(1));
                    fileWrite.close();
                    System.exit(0);
                }
                tr.renderFrame(world);
            }
        }

    }



    public void drawDoor(int oneNine) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (world[x][y].equals(Tileset.FLOOR)) {
                    world[x][y] = Tileset.UNLOCKED_DOOR;
                    return;
                }
            }
        }
    }
    public static List<Integer> placeAvatar(int oneNine, boolean move, int xcord, int ycord) {
        List<Integer> p = new ArrayList<>();;
        if (!move) {
            int counter = oneNine;
            for (int x = 0; x < world.length; x++) {
                for (int y = 0; y < world[x].length; y++) {
                    if (world[x][y] == Tileset.FLOOR) {
                        if (counter == 0) {
                            world[x][y] = Tileset.AVATAR;
                            p.add(x);
                            p.add(y);
                        }
                        counter--;
                    }
                }
            }
        } else {
            world[xcord][ycord] = Tileset.AVATAR;
            p.add(xcord);
            p.add(ycord);
        }
        return p;
    }
    public static void main(String[] args) {
        String seed = "1029384756431627486";
        MapGenerator mapGen = new MapGenerator();
        int oneNine = Integer.parseInt(String.valueOf(seed.charAt(0)));
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        mapGen.drawEmptyWorld();
        for (int i = seed.length() - 1; i > 0; i--) {
            int valueID = Integer.parseInt(seed.charAt(i) + "");
            mapGen.drawRectangle(mapGen.genSquare(i, valueID, oneNine));
        }
        mergeSquares();
        refineWorld();
        ter.renderFrame(world);
    }
}
