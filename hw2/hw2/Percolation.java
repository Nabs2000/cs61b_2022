package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] grid;
    private final WeightedQuickUnionUF arr;

    private WeightedQuickUnionUF arrWOBot;
    private final int topSite;

    private final int botSite;
    private int openSites;
    /* This constructor will initialize an NxN weighted quick union structure
     */
    public Percolation(int N) {
        openSites = 0;
        if (N <= 0) {
            throw new IllegalArgumentException("Cannot have negative size!");
        }
        grid = new boolean[N][N];
        for (int row = 0; row < N; row += 1) {
            for (int col = 0; col < N; col += 1) {
                grid[row][col] = false;
            }
        }
        topSite = N * N;
        botSite = (N * N) + 1;
        arr = new WeightedQuickUnionUF((N * N) + 2);
        arrWOBot = new WeightedQuickUnionUF(N * N + 1);
    }

    private int xyToNum(int row, int col) {
        return row * grid.length + col;
    }

    private void throwException(int row, int col) {
        if (row > grid.length - 1 || row < 0) {
            throw new IndexOutOfBoundsException("Row is outside prescribed range");
        }
        if (col > grid.length - 1 || col < 0) {
            throw new IndexOutOfBoundsException("Column is outside prescribed range");
        }
    }
    public void open(int row, int col) {
        throwException(row, col);
        if (grid[row][col]) {
            return;
        }

        grid[row][col] = true;

        if (row == 0) { // Union the entry with topSite if the curr entry is in the top row
            arr.union(xyToNum(row, col), topSite);
            arrWOBot.union(xyToNum(row, col), topSite);
        }

        if (row == grid.length - 1) { // Same for bottom row
            arr.union(xyToNum(row, col), botSite);
        }
        // Now need to check if surrounding tiles are open
        boolean checkLeft = (col != 0) && grid[row][col - 1];
        boolean checkRight = (col != grid.length - 1) && grid[row][col + 1];
        boolean checkBelow = (row != 0) && grid[row - 1][col];
        boolean checkAbove = (row != grid.length - 1) && grid[row + 1][col];

        if (checkLeft) {
            arr.union(xyToNum(row, col), xyToNum(row, col - 1));
            arrWOBot.union(xyToNum(row, col), xyToNum(row, col - 1));
        }
        if (checkRight) {
            arr.union(xyToNum(row, col), xyToNum(row, col + 1));
            arrWOBot.union(xyToNum(row, col), xyToNum(row, col + 1));
        }
        if (checkBelow) {
            arr.union(xyToNum(row, col), xyToNum(row - 1, col));
            arrWOBot.union(xyToNum(row, col), xyToNum(row - 1, col));
        }
        if (checkAbove) {
            arr.union(xyToNum(row, col), xyToNum(row + 1, col));
            arrWOBot.union(xyToNum(row, col), xyToNum(row + 1, col));
        }

        openSites += 1;

    }

    public boolean isOpen(int row, int col) {
        throwException(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        throwException(row, col);
        int curr = xyToNum(row, col);
        return arrWOBot.connected(curr, topSite);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return arr.connected(topSite, botSite);
    }
}
