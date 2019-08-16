package dsa.list;

import java.util.Iterator;
import java.util.Random;

/**
 * ------------------------------------------------------------------------------[13,13]
 * ------------------------------------------------------------------------------[13,13]
 * ------------------------------------[7,7]-------------[10,10]-----------------[13,13]---------[15,15]-----------------[18,18]
 * ------------------------------------[7,7]-------------[10,10]-----------------[13,13]---------[15,15]---------[17,17]-[18,18]
 * ------------------------[5,5]-------[7,7]-------------[10,10]-[11,11]---------[13,13]---------[15,15]---------[17,17]-[18,18]-[19,19]
 * [1,1]-[2,2]-[3,3]-[4,4]-[5,5]-[6,6]-[7,7]-[8,8]-[9,9]-[10,10]-[11,11]-[12,12]-[13,13]-[14,14]-[15,15]-[16,16]-[17,17]-[18,18]-[19,19]-[20,20]
 *
 *
 * Fore more info, please check the link below
 *
 * @link https://opendsa-server.cs.vt.edu/ODSA/Books/CS3/html/SkipList.html#id1
 * @since 2019年08月15日 21:47:53
 **/
public class SkipListMap<K extends Comparable<K>, V> {

  private int level;
  private int size;
  private Random ran;
  private float p;

  private Node<K, V> head;

  public SkipListMap() {
    ran = new Random();
    size = 0;
    level = -1;
    head = new SkipListMap.Node<>(null, null, 0);
  }

  public V get(K key) {
    Node<K, V> x = head;// Dummy head node
    for (int i = level; i >= 0; i--) {//For each level... start from highest
      // the next value is greater or equal than the search key or null.
      // Yes, move down
      // No, keep going
      while (x.forward[i] != null && x.forward[i].key.compareTo(key) < 0) {
        x = x.forward[i];
      }
    }
    x = x.forward[0];//The next value
    return (x != null && x.key.compareTo(key) == 0) ? x.val : null;
  }

  public Iterable<K> keys() {
    return () -> new Iterator<K>() {
      Node<K, V> h = head;


      @Override
      public boolean hasNext() {
        return h.forward[0] != null;
      }

      @Override
      public K next() {
        K k = h.forward[0].key;
        h = h.forward[0];
        return k;
      }
    };
  }

  public Iterable<V> values() {
    return () -> new Iterator<V>() {
      Node<K, V> h = head;


      @Override
      public boolean hasNext() {
        return h.forward[0] != null;
      }

      @Override
      public V next() {
        V k = h.forward[0].val;
        h = h.forward[0];
        return k;
      }
    };
  }

  public void insert(K key, V val) {
    int newLevel = randomLevel();
    if (newLevel > level) {
      adjustHead(newLevel);
    }

    Node<K, V>[] update = new Node[level + 1];
    Node<K, V> x = head;//Start at header node
    for (int i = level; i >= 0; i--) {
      // the next value is greater or equal than the search key or null.
      // Yes, move down
      // No, keep going
      while (x.forward[i] != null && x.forward[i].key.compareTo(key) < 0) {
        x = x.forward[i];
      }
      update[i] = x;//track end at level i, node need to be update
    }
    x = new Node<>(key, val,
        newLevel);//overwrite this node, It doesn't care it find the old null or not exists
    for (int i = 0; i <= newLevel; i++) {
      x.forward[i] = update[i].forward[i];//Who x points to
      update[i].forward[i] = x;//Who points to x
    }
    size++;
  }

  public V remove(K key) {
    Node<K, V>[] update = new Node[level + 1];
    Node<K, V> x = head;//Start at header node
    for (int i = level; i >= 0; i--) {
      // the next value is greater or equal than the search key or null.
      // Yes, move down
      // No, keep going
      while (x.forward[i] != null && x.forward[i].key.compareTo(key) < 0) {
        x = x.forward[i];
      }
      update[i] = x;//track end at level i, node need to be update
    }
    x = x.forward[0];
    V old = null;
    if (x != null && x.key.compareTo(key) == 0) {
      size--;
      old = x.val;
      for (int i = 0, size = x.forward.length; i < size; i++) {
        update[i].forward[i] = x.forward[i];//Who points to x
      }
    }

    return old;
  }

  int randomLevel() {
    int lev;
    for (lev = 0; Math.abs(ran.nextInt()) % 2 == 0; lev++) {
    }
    return lev;
  }

  private void adjustHead(int newLevel) {
    Node<K, V> temp = head;
    head = new Node<>(null, null, newLevel);
    if (level + 1 >= 0) {
      System.arraycopy(temp.forward, 0, head.forward, 0, level + 1);
    }
    level = newLevel;
  }

  public int size() {
    return size;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Node<K, V>[] forward = head.forward;

    for (int i = level; i >= 0; i--) {
      Node x = forward[i];
      while (x != null) {
        sb.append(x).append(" ");
        x = x.forward[i];
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public String prettyString() {
    StringBuilder sb = new StringBuilder();
    Node<K, V>[] forward = head.forward;

    StringBuilder base = new StringBuilder();
    Node zero = forward[0];
    while (zero != null) {
      base.append(zero);
      zero = zero.forward[0];
      if (zero != null) {
        base.append("-");
      }
    }

    StringBuilder builder = new StringBuilder();
    for (int i = level; i > 0; i--) {
      Node x = forward[i];
      builder.setLength(0);
      while (x != null) {
        String xStr = x.toString();
        for (int l = builder.length(), idx = base.indexOf(xStr); l < idx; l++) {
          builder.append('-');
        }
        builder.append(xStr);
        x = x.forward[i];
        if (x != null) {
          builder.append("-");
        }
      }
      sb.append(builder).append("\n");
    }
    sb.append(base);
    return sb.toString();
  }

  private static class Node<K, V> {

    final K key;
    final V val;
    final Node<K, V>[] forward;

    public K key() {
      return key;
    }

    public V val() {
      return val;
    }


    Node(K key, V val, int level) {
      this.key = key;
      this.val = val;
      this.forward = new Node[level + 1];
    }

    @Override
    public String toString() {
      return "[" + key + "," + val + "]";
    }
  }
}
