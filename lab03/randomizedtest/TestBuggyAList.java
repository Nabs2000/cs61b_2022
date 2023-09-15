package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> normal = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();
        int count = 0;
        while (count < 3) {
            normal.addLast(count + 1);
            buggy.addLast(count + 1);
            count += 1;
        }

        assertEquals(normal.removeLast(), buggy.removeLast()); // Will be for the integer '3' and should return true
        assertEquals(normal.removeLast(), buggy.removeLast()); // Will be for the integer '2' and should return true
        assertEquals(normal.removeLast(), buggy.removeLast()); // Will be for the integer '1' and should return true
    }
    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggy.addLast(randVal);
//                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
//                System.out.println("size: " + size);
            } else if (operationNumber == 2 && L.size() > 0) {
                // removeLast
                int normalItem = L.removeLast();
                int buggyItem = buggy.removeLast();
//                System.out.println("removeLast() return value: " + lastItem);
                assertEquals(normalItem, buggyItem);
            } else if (operationNumber == 3 && L.size() > 0) {
                // getLast
                int normalItem = L.getLast();
                int buggyItem = buggy.getLast();
//                System.out.println("getLast() return value: " + lastItem);
                assertEquals(normalItem, buggyItem);
            }
        }
    }
}
