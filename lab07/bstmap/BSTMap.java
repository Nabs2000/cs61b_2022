package bstmap;

import com.sun.source.tree.Tree;
import edu.princeton.cs.algs4.BST;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private BSTNode root;
    private int size;
    private class BSTNode {
        private K key;
        private V val;
        private BSTNode left, right;
        public BSTNode(K k, V v) {
            key = k;
            val = v;
        }


    }
    public BSTMap() {
        size = 0;
    }
    @Override
    public void clear() {
        // Removes all mappings, meaning remove the first node!
        size = 0;
        root.left = null;
        root.right = null;
        root.key = null;
        root.val = null;
    }

    @Override
    public boolean containsKey(K key) { // Check if root has it, and if none of the roots have it then return false
        return containsKey(key, root);
    }
    private boolean containsKey(K key, BSTNode root) {
        if (root == null || root.key == null) {
            return false;
        }
        if (key.equals(root.key)) {
            return true;
        } else if (key.compareTo(root.key) < 0) { // If the curr key is less than the other, recurse to the left
            return containsKey(key, root.left);
        } else { // If the curr key is greater than the other, recurse to the right
            return containsKey(key, root.right);
        }
    }

    @Override
    public V get(K key) {
        return get(key, root);
    }
    private V get(K key, BSTNode root) {
        if (root == null || root.key == null) {
            return null;
        }
        if (key.equals(root.key)) {
            return root.val;
        } else if (key.compareTo(root.key) < 0) { // If given key is less than the current one, recurse to the left
            return get(key, root.left);
        } else {
            return get(key, root.right);
        } // QUESTION: Why do we need a return statement here for the else-ifs, elses?
    }

    @Override
    public int size() {
        if (root == null) {
            return 0;
        }
        return size;
    }

    @Override
    public void put(K key, V value) {
        // We should figure out whether to update the existing key or add a new key to a corresponding spot
        // For updating, this just means that you find the key when recursing and update the value
        // For adding, this means that you need to:
            // Recurse until you find the appropriate spot to add
        root = put(key, value, root);
    }
    private BSTNode put(K key, V value, BSTNode root) {
        if (root == null) { // This condition will add the new node
            root = new BSTNode(key, value);
            size += 1;
        } else if (key.compareTo(root.key) == 0) { // This condition will update the value
            root.val = value;
        } else {
            if (key.compareTo(root.key) < 0) { // This condition will check the left
                root.left = put(key, value, root.left);
            }
            if (key.compareTo(root.key) > 0) { // This condition will check the right
                root.right = put(key, value, root.right);
            }
        }
        return root;
    }
    public void printInOrder() {
        printInOrder(root);
    }
    private void printInOrder(BSTNode root) {
        if (root.left != null) {
            printInOrder(root.left);
        }
        System.out.println(root.key);
        if (root.right != null) {
            printInOrder(root.right);
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

    public static void main(String[] args) {
        TreeMap<Integer, String> test = new TreeMap<>();
        test.put(1, "test");
        test.put(0, "hehexd");
        TreeMap<Integer, String> xd = test.tailMap(0);
    }
}
