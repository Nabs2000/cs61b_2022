package hw2;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class PercolationTest {
    @Test
    public void testOpen() {
        Percolation p = new Percolation(2);
        p.open(0, 1);
        assertTrue(p.isOpen(1, 1));
    }
    @Test
    public void testFull() {
        Percolation p = new Percolation(2);
        p.open(0, 1);
        assertFalse(p.isFull(1, 1));
        assertTrue(p.isFull(0, 1));
    }
    @Test
    public void testOpenSites() {
        Percolation p = new Percolation(2);
        p.open(0, 1);
        p.open(0, 1);
        assertEquals(p.numberOfOpenSites(), 1);
    }
    @Test
    public void testPercolates() {
        Percolation p = new Percolation(2);
        p.open(0, 0);
//        p.open(0, 1);
        assertFalse(p.percolates());
        p.open(1, 0);
        assertTrue(p.percolates());
    }
}
