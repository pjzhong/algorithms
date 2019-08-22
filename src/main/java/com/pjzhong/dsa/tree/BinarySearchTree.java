package com.pjzhong.dsa.tree;

public class BinarySearchTree<Key extends Comparable<Key>, Value> extends BinaryTree<Key, Value> {

  private Node root;

  private Node min(Node x) {
    while (hasLeftChild(x)) {
      x = x.left;
    }

    return x;
  }

  private Node deleteMin(Node x) {
    if (x.left == null) {
      return x.right;
    }
    x.left = deleteMin(x.left);
    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));

    return x;
  }

  public Value min() {
    return root == null ? null : min(root).value;
  }

  public Value deleteMin() {
    Node old = min(root);
    root = deleteMin(root);
    return old == null ? null : old.value;
  }

  private Node max(Node x) {
    while (hasRightChild(x)) {
      x = x.right;
    }

    return x;
  }

  private Node deleteMax(Node x) {
    if (x.right == null) {
      return x.left;
    }
    x.right = deleteMax(x.right);
    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));

    return x;
  }

  public Value max() {
    return root == null ? null : max(root).value;
  }

  public Value deleteMax() {
    Node old = max(root);
    root = deleteMax(root);
    return old == null ? null : old.value;
  }

  private Node delete(Node x, Key key) {
    if (x == null) {
      return null;
    }

    int compareResult = key.compareTo(x.key);
    if (compareResult < 0) {
      x.left = delete(x.left, key);
    } else if (0 < compareResult) {
      x.right = delete(x.right, key);
    } else {
      if (!hasRightChild(x)) {
        return x.left;
      }
      if (!hasLeftChild(x)) {
        return x.right;
      }
      Node temp = x;
      x = min(temp.right);
      x.right = deleteMax(temp.right);
      x.left = temp.left;
    }

    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));
    return x;
  }

  public Value delete(Key key) {
    Node old = get(root, key);
    root = delete(root, key);
    return old.value;
  }

  private Node get(Node root, Key key) {
    Node x = root;
    while (x != null) {
      int compareResult = key.compareTo(x.key);

      if (compareResult < 0) {
        x = x.left;
      } else if (0 < compareResult) {
        x = x.right;
      } else {
        return x;
      }
    }

    return null;
  }

  public Value get(Key key) {
    Node x = get(root, key);
    return x == null ? null : x.value;
  }

  public void put(Key key, Value value) {
    root = put(null, root, key, value);
  }

  private Node put(Node parent, Node x, Key key, Value value) {
    if (x == null) {
      return new Node(parent, key, value);
    }

    int compareResult = key.compareTo(x.key);

    if (compareResult < 0) {
      x.left = put(x, x.left, key, value);
    } else if (0 < compareResult) {
      x.right = put(x, x.right, key, value);
    } else {
      x.value = value;
    }

    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));
    return x;

  }

  @Override
  protected Node getRoot() {
    return root;
  }

}
