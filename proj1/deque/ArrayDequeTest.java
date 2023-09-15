package deque;

import org.junit.Test;

import java.sql.Array;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        ArrayDeque<String> deque1 = new ArrayDeque<>();

        assertTrue("The array should be empty and thus have a size of 0", deque1.isEmpty());
    }

    @Test
    public void addCoupleFirsts() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addFirst(2);
//        deque1.addLast(4);
        deque1.addFirst(5);
        deque1.addFirst(7);
        deque1.addFirst(1);
//        deque1.addLast(2);
//        deque1.addLast(8);
//        deque1.addLast(1);
//        assertEquals(8, deque1.size());
    }

    @Test
    public void addCoupleLasts() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addLast(2);
//        deque1.addLast(4);
        deque1.addLast(5);
        deque1.addLast(7);
        deque1.addLast(1);
    }
    @Test
    public void testComboAdds() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addLast(4);
        deque1.addLast(5);
        deque1.addFirst(5);
        deque1.addFirst(7);
        deque1.addFirst(1);
        deque1.addLast(7);
        deque1.addLast(1);
        deque1.addFirst(20);
    }
    @Test
    public void testPrint() {
        // Empty deque
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addFirst(2);
        deque1.addLast(4);
        deque1.addFirst(5);
        deque1.addFirst(7);
        deque1.addFirst(1);
        deque1.addLast(2);
        deque1.addLast(8);
        deque1.addFirst(1);
        deque1.printDeque();
    }

    @Test
    public void testGet1() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addFirst(2);
        deque1.addLast(4);
        deque1.addFirst(5);
        deque1.addFirst(7);
        deque1.addFirst(1);
        deque1.addLast(2);
        deque1.addLast(8);
        deque1.addFirst(1);
        System.out.println(deque1.get(2));
        deque1.addLast(100);
        System.out.println(deque1.get(2));
        deque1.addFirst(25);
        deque1.addLast(2000);
        Integer test1 = deque1.get(99);
        assertNull(test1);
        Integer test2 = deque1.get(1);
        Integer test3 = deque1.get(15);
        deque1.printDeque();
    }

    @Test
    public void testResize() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        int i = 0;
        while (i < 10) {
            deque1.addFirst(i);
            deque1.addLast(i);
            deque1.addLast(i);
            i += 1;
        }
        System.out.println();
    }

    @Test
    public void testGet() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        int i = 0;
        while (i < 2) {
            deque1.addFirst(i);
            deque1.addLast(i);
            i += 1;
        }
        int temp = deque1.get(2);
    }

    @Test
    public void testRemove() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        int i = 0;
        while (i < 4) {
            deque1.addFirst(i);
            deque1.addLast(i);
            i += 1;
        }
        i = 0;
        while (i < 8) {
            deque1.removeFirst();
            i += 1;
        }
        i = 0;
        while (i < 8) {
            deque1.addLast(i);
            i += 1;
        }
//        int temp = deque1.get(0);
        int temp = deque1.removeLast();
//        temp = deque1.get(0);
        deque1.addLast(50);
        deque1.addLast(100);
//        temp = deque1.get(0);
    }

    @Test
    public void testResizeDown() {
        int i = 0;
        ArrayDeque<String> deque1 = new ArrayDeque<>();
        while (i < 5) {
            deque1.addFirst("hehexd");
            i += 1;
        }
        i = 0;
        while (i < 5){
            deque1.addFirst("hehexd");
            deque1.addLast("hehexd");
            i += 1;
        }
        i = 0;
        while (i < 10) {
            deque1.removeFirst();
            deque1.removeFirst();
            i += 1;
        }
    }
}
