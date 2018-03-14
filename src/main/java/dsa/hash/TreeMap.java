package dsa.hash;

import dsa.tree.RedBlackTree;

public class TreeMap<Key extends Comparable<Key>, Value> implements Map<Key, Value> {

    RedBlackTree<Key, Value> tree = new RedBlackTree<>();

    @Override
    public void clear() {
        tree.clear();
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public boolean contains(Key key) {
        return tree.get(key) != null;
    }

    @Override
    public Value get(Key key) {
        return tree.get(key);
    }

    @Override
    public Value put(Key key, Value value) {
        return tree.put(key, value);
    }

    @Override
    public Value remove(Key key) {
        return tree.remove(key);
    }
}
