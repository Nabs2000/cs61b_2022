package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> comp;

    public MaxArrayDeque(Comparator<T> c) { //
        comp = c;
    }

    public T max() {
        if (size() == 0) {
            return null;
        }
        T maxItem = get(0);
        for (int i = 1; i < size(); i += 1) {
            if (comp.compare(get(i), maxItem) > 0) {
                maxItem = get(i);
            }
        }
        return maxItem;
    }

    public T max(Comparator<T> c) {
        comp = c;
        return max();
    }
}
