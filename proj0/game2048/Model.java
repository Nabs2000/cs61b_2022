package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author Nabeel Sabzwari
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private final Board _board;
    /** Current score. */
    private int _score;
    /** Maximum score so far.  Updated when game ends. */
    private int _maxScore;
    /** True iff game is ended. */
    private boolean _gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        _board = new Board(size);
        _score = _maxScore = 0;
        _gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        _board = new Board(rawValues);
        this._score = score;
        this._maxScore = maxScore;
        this._gameOver = gameOver;
    }

    /** Same as above, but gameOver is false. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore) {
        this(rawValues, score, maxScore, false);
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     * */
    public Tile tile(int col, int row) {
        return _board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return _board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (_gameOver) {
            _maxScore = Math.max(_score, _maxScore);
        }
        return _gameOver;
    }

    /** Return the current score. */
    public int score() {
        return _score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return _maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        _score = 0;
        _gameOver = false;
        _board.clear();
        setChanged();
    }

    /** Allow initial game board to announce a hot start to the GUI. */
    public void hotStartAnnounce() {
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        _board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    public void tilt(Side side) {
        // TODO: Fill in this function.
        _board.setViewingPerspective(side);
        for (int col = 0; col < _board.size(); col += 1) {
            tiltPerCol(col);
        }
        _board.setViewingPerspective(side.NORTH);
        checkGameOver();
    }
    public void tiltPerCol(int col) {
        for (int currRow = _board.size() - 1; currRow > 0; currRow -= 1) {

            Tile curr = _board.tile(col, currRow); // Initializes the current tile
            for (int nextRow = currRow - 1; nextRow >= 0; nextRow -= 1) { // Iterates down from curr location
                Tile next = _board.tile(col, nextRow); // Initializes tile under curr
                if (!isNull(curr)) { // Checks if curr is a value
                    if (!isNull(next)) { // Checks if next is a value, and whether a merge is possible
                        if (curr.value() == next.value()) {
                            _score += (2 * curr.value());
                            _board.move(col, currRow, next);
                            break; // Breaks to initialize curr to the next row; prevents double merging
                        }
                        break; // Breaks if there is no merge (if the tile being compared to curr is a blank or a diff value)
                    }
                } else { // This means that curr is a blank
                    if (!isNull(next)) { // If the next tile contains a num, moves it to the curr spot
                        _board.move(col, currRow, next);
                        curr = _board.tile(col, currRow); // Re-initializes curr to prevent double merging
                    }
                }
            }
        }
    }
    private void checkGameOver() {
        _gameOver = checkGameOver(_board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Helper function for emptySpaceExists() to check if a tile is empty */
    public static boolean isNull(Tile t){
        return t == null;
    }
    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        /* Iterate over the entire board and if b.tile() == null, then return true. Otherwise, return False!
         */

        for (int row = 0; row < b.size(); row += 1) {
            for (int col = 0; col <b.size(); col += 1) {
                Tile t = b.tile(col, row);
                if (isNull(t)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.

        /* Check the entire board to see if MAX_PIECE exists. Iterate thru the entire board, and return true if it does. Else return false!
         */

        for (int row = 0; row < b.size(); row += 1) {
            for (int col = 0; col < b.size(); col += 1) {
                Tile temp = b.tile(col, row);
                if (temp != null && temp.value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */

//    public static boolean checkCorners(Board b, Tile curr) {
//        int firstRow = 0;
//        int lastRow = b.size() - 1;
//        int firstCol = 0;
//        int lastCol = b.size() - 1;
//        if (firstRow && firstCol) {
//            Tile right = b.tile(firstCol + 1, firstRow);
//            Tile below = b.tile(firstCol, firstRow + 1);
//            if (curr.value() == right.value() || curr.value() == below.value())
//                return true;
//        }
//        if (row == 0 && col == b.size() - 1) {
//            left = b.tile(col - 1, row);
//            below = b.tile(col, row + 1);
//            if (curr.value() == left.value() || curr.value() == below.value())
//                return true;
//        }
//        if (row == b.size() - 1 && col == 0) {
//            right = b.tile(col + 1, row);
//            above = b.tile(col, row - 1);
//            if (curr.value() == right.value() || curr.value() == above.value())
//                return true;
//        }
//
//        if (row == b.size() - 1 && col == b.size() - 1) {
//            left = b.tile(col - 1, row);
//            above = b.tile(col, row - 1);
//            if (curr.value() == left.value() || curr.value() == above.value())
//                return true;
//        }
//    }
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if (emptySpaceExists(b) || maxTileExists(b))
            return true;
        /* to check for adjacent spaces, I will create a helper function that will check each
        individual tile and see the tiles around it. If the values are the same, return true,
        otherwise will return false
         */
        for (int row = 0; row < b.size(); row += 1) {
            for (int col = 0; col < b.size(); col += 1) {
                // Capture the current tile
                Tile curr = b.tile(col, row);
                /* These sets of booleans describe two things:
                    To check if each respective direction can be actually checked and won't go out of bounds(first cond)
                    To check if the adjacent position corresponding to the direction is equal to the current value (second cond)

                    Had some help from this link describing short-circuit booleans: https://stackoverflow.com/questions/20381284/multi-dimensional-array-how-can-i-check-all-adjacent-tiles-that-are-in-bounds-t
                 */

                boolean left = (col != 0) && b.tile(col - 1, row).value() == curr.value();
                boolean right = (col != b.size() - 1) && b.tile(col + 1, row).value() == curr.value();
                boolean below = (row != 0) && b.tile(col, row - 1).value() == curr.value();
                boolean above = (row != b.size() - 1) && b.tile(col, row + 1).value() == curr.value();
                if (left || right || below || above) { // If any are adjacent
                    return true;
                }
            }
        }
        return false;
    }

    /** Returns the model as a string, used for debugging. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    /** Returns whether two models are equal. */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    /** Returns hash code of Model’s string. */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
