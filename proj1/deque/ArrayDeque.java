package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {

    public Iterator<T> iterator() {
        return new ADIterator();
    }

    private class ADIterator implements Iterator<T> {
        private int wizPos = 0;


        public boolean hasNext() {
            return wizPos < size();
        }

        public T next() {
            T currItem = get(wizPos);
            wizPos += 1;
            return currItem;
        }
    }

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    private double uf;

    private static final int INITCAP = 8;

    public ArrayDeque() {
        items = (T[]) new Object[INITCAP];
        size = 0;
        nextFirst = (items.length / 2) - 1;
        nextLast = items.length / 2;
    }

    private void resize(int cap) {
        // First check value of nextLast and nextFirst
        T[] temp = (T[]) new Object[cap];
        int startPsn = (int) Math.floor(items.length / (2 * 2));
        System.arraycopy(items, nextFirst + 1, temp, startPsn, size);
        nextFirst = startPsn - 1;
        nextLast = startPsn + size;
        items = temp;
    }

    @Override
    public void addFirst(T item) {
        if (nextFirst < 0) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        size += 1;
        nextFirst -= 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addLast(T item) {
        if (nextLast >= items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        size += 1;
        nextLast += 1;
    }

    @Override
    public void printDeque() {
        int counter = nextFirst + 1;
        while (counter != nextLast) {
            System.out.print(items[counter] + " ");
            counter += 1;

        }
        System.out.println();
    }

    private boolean checkEdges() {
        return (size == 0 && (nextLast >= items.length || nextFirst < 0));

    }
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        uf = (float) size / items.length;
        int firstItemIdx = nextFirst + 1;
        T removed = items[firstItemIdx];
        items[firstItemIdx] = null;
        nextFirst += 1;
        size -= 1;
        if (checkEdges()) {
            nextFirst = (items.length / 2) - 1;
            nextLast = items.length / 2;
        }
        return removed;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        uf = (float) size / items.length;
        int lastItemIdx = nextLast - 1;
        T removed = items[lastItemIdx];
        items[lastItemIdx] = null;
        nextLast -= 1;
        size -= 1;
        if (checkEdges()) {
            nextFirst = (items.length / 2) - 1;
            nextLast = items.length / 2;
        }
        return removed;
    }

    @Override
    public T get(int index) {
        if (size == 0 || index >= size) {
            return null;
        }

        return items[nextFirst + index + 1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Deque<?> oad) {
            if (this.size() != oad.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i += 1) {
                if (!this.get(i).equals(oad.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false; // not an AD
    }
}

