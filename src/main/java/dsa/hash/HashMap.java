package dsa.hash;

public class HashMap<Key, Value> implements Map<Key, Value> {

    private final int DEFAULT_SIZE = 16;

    private Node<Key, Value>[] table;
    private int size = 0, collsions = 0, capacity = DEFAULT_SIZE;
    private double loadFactor = 0.75;

    private int hash(Key key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public HashMap() {
        table = (Node<Key, Value>[])new Node[capacity];
    }

    @Override
    public void clear() {
        Node<Key, Value>[] tab = table;
        for(int i = 0, size = tab.length; i < size; i++) {
            if(tab[i] != null) {
                Node<Key, Value> n = tab[i], t;
                while(n != null) {
                    t = n.next;
                    n.next = null;
                    n = t;
                }
                tab[i] = null;
            }
        }
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Key key) {
        Node<Key, Value>[] tab = table;
        return get(tab, key) != null;
    }

    @Override
    public Value get(Key key) {
        Node<Key, Value>[] tab = table;
        Node<Key, Value> n = get(tab, key);
        return n == null ? null : n.getValue();
    }

    private Node<Key, Value> get(Node<Key, Value>[] table, Key key) {
        Node<Key, Value>[] tab = table;
        Node<Key, Value> p;
        int n =  tab.length;

        p = tab[(n - 1) & hash(key)];
        if(p != null) {
            Node<Key, Value> node = p;
            while(node != null) {
                if(node.getKey() == key || key.equals(node.getKey())) {
                    return node;
                }
                node = node.next;
            }
        }

        return null;
    }

    @Override
    public Value put(Key key, Value value) {
        Node<Key, Value>[] tab = table;
        Node<Key, Value> p;
        int index, hash,  n =  tab.length;

        index = (n - 1) & (hash = hash(key));
        p = tab[index];
        if(p == null) {
            tab[index] = new Node<>(hash, key, value, null);
        } else {
            Node<Key, Value> node = p;
            while(node != null) {
                if(node.getKey() == key || key.equals(node.getKey())) {
                    return node.setValue(value);
                }
                node = node.next;
                collsions++;
            }
            Node<Key, Value> newNode = new Node<>(hash, key, value, p);
            tab[index] = newNode;
        }

        size++;
        rehash();
        return null;
    }

    @Override
    public Value remove(Key key) {
        Node<Key, Value>[] tab = table;
        Node<Key, Value> first;
        int  index, n = tab.length;

        Value old = null;
        first = tab[index = ((n - 1) & hash(key))];
        if(first != null) {
            Node<Key, Value> node = first, lastVisited = first;
            while(node != null) {
                if(node.getKey() == key || key.equals(node.getKey())) {
                    if(first == lastVisited) {
                        table[index] = null;
                    } else {
                        lastVisited.next = node.next;
                    }

                    old =  node.getValue();
                    break;
                }
                lastVisited = node;
                node = node.next;
            }
            size--;
        }
        return old;
    }

    private void rehash() {
        if((capacity * loadFactor) < size) {
            int newCapacity =  (capacity << 1);
            int oldSize = size;
            if(newCapacity < capacity) { capacity = Integer.MAX_VALUE; }

            capacity = newCapacity;
            Node<Key, Value>[] newTable = new Node[capacity], oldTab  = table;
            table = newTable;
            for(int i = 0, size = oldTab.length; i < size; i++) {
                if(oldTab[i] != null) {
                    Node<Key, Value> p = oldTab[i];
                    int hash, index;
                    for(;p != null; p = p.next) {
                        index = (capacity - 1) & (hash = hash(p.key));
                        Node<Key, Value> t = table[index];
                        if(t == null) {
                            table[index] = new Node<>(hash, p.key, p.value, null);
                        } else {
                            Node<Key, Value> newNode = new Node<>(hash, p.key, p.value, t);
                            table[index] = newNode;
                        }
                    }
                    oldTab[i] = null;
                }
            }
            size = oldSize;
        }
    }

    static class Node<Key, Value> {
        final int hash;
        final Key key;
        Value value;
        Node<Key, Value> next;

        public Node(int hash, Key key, Value value, Node<Key, Value> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public Value setValue(Value v) {
            Value old = value;
            value = v;
            return old;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("key=").append(key);
            sb.append(", value=").append(value);
            sb.append('}');
            return sb.toString();
        }
    }
}
