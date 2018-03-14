package dsa.hash;

public interface Map<Key, Value> {

    void clear();

    int size();

    boolean isEmpty();

    boolean contains(Key key);

    Value get(Key key);

    Value put(Key key, Value value);

    Value remove(Key key);
}
