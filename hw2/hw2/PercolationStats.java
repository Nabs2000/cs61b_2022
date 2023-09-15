package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {

    private Percolation perc;
    private double[] arr;

    private int lenArr;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Incorrect values for N or T!");
        }
        arr = new double[T];
        lenArr = T;
        for (int i = 0; i < arr.length; i += 1) {
            perc = pf.make(N);
            while (!perc.percolates()) {
                // Now, we must fill up random tiles
                int randRow = StdRandom.uniform(N);
                int randCol = StdRandom.uniform(N);
                perc.open(randRow, randCol);
            }
            arr[i] = (double) perc.numberOfOpenSites() / (N * N);
        }
    }
    public double mean() {
        return StdStats.mean(arr);
    }
    public double stddev() {
        return StdStats.stddev(arr);
    }
    public double confidenceLow() {
        return StdStats.mean(arr) - (1.96 * stddev()) / Math.sqrt(lenArr);
    }
    public double confidenceHigh() {
        return StdStats.mean(arr) + (1.96 * stddev()) / Math.sqrt(lenArr);
    }
}
