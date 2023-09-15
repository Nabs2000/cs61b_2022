package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    /* The deque class for linked lists
     */

    /* Returns an iterator into current instantiation */
    public Iterator<T> iterator() {
        return new LLDequeIterator();
    }

    private class LLDequeIterator implements Iterator<T> {
        private IntNode wizLoc;

        public LLDequeIterator() {
            wizLoc = sentinel.next;
        }

        public boolean hasNext() {
            return wizLoc != sentinel;
        }

        public T next() {
            T currItem = wizLoc.item;
            wizLoc = wizLoc.next;
            return currItem;
        }
    }

    private class IntNode {
        private T item;
        private IntNode prev;
        private IntNode next;

        public IntNode(T i, IntNode p, IntNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private IntNode sentinel;
    //    Returns the number of items in the deque.
    private int size;

    public LinkedListDeque() {
        /* For empty deque */
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) { // DONE
        /* Adds an item of type T to the front of the deque. You can assume that item is never null.
         */
        if (size > 0) {
            sentinel.next = new IntNode(item, sentinel, sentinel.next);
            sentinel.next.next.prev = sentinel.next;
        } else {
            sentinel.next = new IntNode(item, sentinel, sentinel);
            sentinel.prev = sentinel.next;
        }
        size += 1;
    }

    @Override
    public void addLast(T item) {
        /* Adds an item of type T to the back of the deque. You can assume that item is never null.
         */
        if (size > 0) {
            sentinel.prev.next = new IntNode(item, sentinel.prev, sentinel);
            sentinel.prev = sentinel.prev.next;
        } else {
            sentinel.next = new IntNode(item, sentinel, sentinel);
            sentinel.prev = sentinel.next;
        }
        size += 1;
    }

    @Override
    public int size() { // DONE
        /* Returns the size of the deque */
        return size;
    }

    @Override
    public void printDeque() {
        IntNode p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();

    }

    @Override
    public T removeFirst() { // DONE
        /* Removes and returns the item at the front of the deque. If no such item exists, returns null.
         */

        if (size > 0) { // If not an empty list
            T temp = sentinel.next.item;
            if (size == 1) { // Case 1: If size is 1
                sentinel.prev = sentinel;
                sentinel.next = sentinel;

            } else { // Case 2: If size is N
                sentinel.next = sentinel.next.next;
                sentinel.next.prev = sentinel;
            }
            size -= 1;
            return temp;
        }
        // Case 3: If empty list
        return null;
    }

    @Override
    public T removeLast() { // DONE
        /* Removes and returns the item at the back of the deque. If no such item exists, returns null.
         */
        if (size > 0) {
            T temp = sentinel.prev.item;
            if (size == 1) {
                sentinel.prev = sentinel;
                sentinel.next = sentinel;
            } else {
                sentinel.prev = sentinel.prev.prev;
                sentinel.prev.next = sentinel;
            }
            size -= 1;
            return temp;
        }
        return null;
    }

    @Override
    public T get(int index) {
        int count = 0;
        IntNode p = sentinel.next;
        while (p != sentinel) {
            if (count == index) {
                return p.item;
            }
            count += 1;
            p = p.next;
        }
        return null;
    }

    private T getRecursive(int index, IntNode temp) {
        if (index == 0) {
            return temp.item;
        }
        return getRecursive(index - 1, temp.next);
    }

    public T getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Deque<?> oLLD) {
            if (this.size() != oLLD.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i += 1) {
                if (!this.get(i).equals(oLLD.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
