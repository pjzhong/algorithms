package com.pjzhong.dsa.tree;


/***
 * for more details, check the link below
 * https://algs4.cs.princeton.edu/33balanced/SplayBST.java.html
 */
public class SplayBinarySearchTree<Key extends Comparable<Key>, Value> extends
    BinaryTree<Key, Value> {

  Node root;

  private Node rotateLeft(Node x) {
    Node rotate = x.right;
    if (hasLeftChild(rotate)) {
      rotate.left.parent = x;
    }
    x.right = rotate.left;
    rotate.left = x;
    rotate.parent = x.parent;
    x.parent = rotate;
    return rotate;
  }

  private Node rotateRight(Node x) {
    Node rotate = x.left;
    if (hasRightChild(rotate)) {
      rotate.right.parent = x;
    }
    x.left = rotate.right;
    rotate.right = x;
    rotate.parent = x.parent;
    x.parent = rotate;
    return rotate;
  }

  private Node splay(Node h, Key key) {
    if (h == null) {
      return null;
    }

    int compareResult = key.compareTo(h.key);

    if (compareResult < 0) {
      if (!hasLeftChild(h)) {
        return h;
      }

      int leftCompare = key.compareTo(h.left.key);
      if (leftCompare < 0) {
        h.left.left = splay(h.left.left, key);
        h = rotateRight(h);
      } else if (0 < leftCompare) {
        h.left.right = splay(h.left.right, key);
        if (hasRightChild(h.left)) {
          h.left = rotateLeft(h.left);
        }
      }

      return hasLeftChild(h) ? rotateRight(h) : h;
    } else if (0 < compareResult) {
      if (!hasRightChild(h)) {
        return h;
      }

      int rightCompare = key.compareTo(h.right.key);
      if (rightCompare < 0) {
        h.right.left = splay(h.right.left, key);
        if (hasLeftChild(h.right)) {
          h.right = rotateRight(h.right);
        }
      } else if (0 < rightCompare) {
        h.right.right = splay(h.right.right, key);
        h = rotateLeft(h);
      }

      return hasRightChild(h) ? rotateLeft(h) : h;
    }

    return h;
  }

  public Value remove(Key key) {
    if (root == null) {
      return null;
    }

    Value removeValue = null;
    root = splay(root, key);

    int compareResult = key.compareTo(root.key);
    if (compareResult == 0) {
      removeValue = root.value;
      if (root.left == null) {
        root = root.right;
      } else {
        Node x = root.right;
        root.left.parent = null;
        root = splay(root.left, key);
        root.right = x;
        x.parent = root;
      }
      if (root != null) {
        root.parent = null;
      }
    }

    return removeValue;
  }

  public Value get(Key key) {
    root = splay(root, key);
    int compareResult = key.compareTo(root.key);
    return compareResult == 0 ? root.value : null;
  }

  public Value put(Key key, Value value) {
    if (root == null) {
      root = new Node(null, key, value);
      return null;
    }

    root = splay(root, key);

    int compareResult = key.compareTo(root.key);

    Value old = null;
    if (compareResult < 0) {
      Node n = new Node(null, key, value);
      if (hasLeftChild(root)) {
        root.left.parent = n;
      }
      n.left = root.left;
      n.right = root;
      root.parent = n;
      root.left = null;
      root = n;
    } else if (0 < compareResult) {
      Node n = new Node(null, key, value);
      if (hasRightChild(root)) {
        root.right.parent = n;
      }
      n.right = root.right;
      n.left = root;
      root.right = null;
      root.parent = n;
      root = n;
    } else {
      old = root.value;
      root.value = value;
    }

    return old;
  }

  @Override
  public int height() {
    return height(root);
  }

  @Override
  protected int height(Node x) {
    if (x == null) {
      return -1;
    }
    return 1 + Math.max(height(x.left), height(x.right));
  }

  @Override
  public int size() {
    return size(root);
  }

  @Override
  protected int size(Node x) {
    if (x == null) {
      return 0;
    }
    return 1 + size(x.left) + size(x.right);
  }

  @Override
  protected Node getRoot() {
    return root;
  }
}
