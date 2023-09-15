package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Nabeel Sabzwari
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public void clear() {
        buckets = createTable(DEFAULT_INIT_SIZE);
        size = 0;
    }
    private Node getNode(K key) {
        int bucketIndex = calculateBucketIndex(key);
        for (Node n: buckets[bucketIndex]) {
            if (n.key.equals(key)) {
                return n;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        // If not null, then we know that the key is in the map
        return getNode(key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public int size() {
        return size;
    }
    private int calculateBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }
    private void rebucket(int newNumBuckets) {
        // Create a reference to the buckets array
        Collection<Node>[] oldBuckets = buckets;
        // Assign buckets to a new reference pointer; oldBuckets still points to the old reference
        buckets = createTable(newNumBuckets);
        // Set size to 0 so that it is properly updated for the new underlying structure
        size = 0;
        for (Collection<Node> b: oldBuckets) {
            for (Node n: b) {
                // Copying all the data from original, underlying structure to new, underlying structure
                put(n.key, n.value);
            }
        }
    }
    @Override
    public void put(K key, V value) {
        // calculate the bucket index
        // add new node to the bucket
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
            return;
        }
        int bucketIndex = calculateBucketIndex(key);
        buckets[bucketIndex].add(createNode(key, value));
        size += 1;
        double currentLoadFactor = (double) size / buckets.length;
        if (currentLoadFactor > maxLoadFactor) {
            rebucket(buckets.length * 2);
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Not needed!");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Not needed!");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Not needed!");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Not needed!");
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!

    private double maxLoadFactor;
    private static final int DEFAULT_INIT_SIZE = 16;
    private static final double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    private int size;

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_INIT_SIZE, DEFAULT_MAX_LOAD_FACTOR);
    }
    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_MAX_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        maxLoadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {

        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] newTable = new Collection[tableSize];
            for (int i = 0; i < newTable.length; i += 1) {
                newTable[i] = createBucket();
            }
            return newTable;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

}
