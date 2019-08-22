package com.pjzhong.dsa.tree;

import java.util.function.Consumer;

/**
 * @link https://www.geeksforgeeks.org/b-tree-set-1-introduction-2/
 */
public class BTree {

  private BTreeNode root;
  //Every node except root must contain at least t-1 keys. Root may contain minimum 1 key.
  //All nodes (including root) may contain at most 2t – 1 keys
  //The factor of 2 will guarantee that nodes can be split or combined.
  //A B-Tree is defined by the term minimum degree 't'. The value of t depends upon disk block size
  //t also mean the maximum num of child
  private int t;

  public BTree(int t) {
    this.t = t;
  }

  public void traverse(Consumer<Integer> c) {
    traverse(root, c);
  }

  private void traverse(BTreeNode root, Consumer<Integer> c) {
    if (root == null) {
      return;
    }

    int[] keys = root.keys;
    BTreeNode[] child = root.child;
    int i;
    for (i = 0; i < root.n; i++) {
      if (!root.leaf) {
        traverse(child[i], c);
      }
      c.accept(keys[i]);
    }

    if (!root.leaf) {
      traverse(child[i], c);
    }
  }

  /***
   * search would require 2d-1* (Log N base on 2d)
   */
  public BTreeNode search(int k) {
    return search(k, root);
  }

  private BTreeNode search(int k, BTreeNode root) {
    if (root == null) {
      return null;
    }

    int i = 0;
    int[] keys = root.keys;
    while (i < root.n && keys[i] < k) {
      i++;
    }

    if (keys[i] == k) {
      return root;
    }

    if (root.leaf) {
      return null;
    }

    return search(k, root.child[i]);
  }

  //a) A new key is always inserted at leaf node
  //Insertion
  public void insert(int k) {
    //If tree is empty
    if (root == null) {
      root = new BTreeNode(t, true);
      root.keys[0] = k;
      root.n = 1;
    } else { // if tree is not empty

      //If root is full, then tree grows in height
      if (root.n == 2 * t - 1) {
        //create new node
        BTreeNode s = new BTreeNode(t, false);

        //Make old root as child of new root
        s.child[0] = root;

        //Split the old root and move 1 key to the new root
        splitChild(0, s);

        //New root has two children now. Decide which of the
        //two children is going to have new key
        int i = 0;
        if (s.keys[0] < k) {
          i++;
        }
        insertNotFull(k, s.child[i]);

        root = s;
      } else {
        insertNotFull(k, root);
      }
    }
  }

  private void insertNotFull(int k, BTreeNode node) {
    // Initialize index as index of rightmost element
    int i = node.n - 1;
    int[] keys = node.keys;
    if (node.leaf) {
      //The following loop does two things
      //a) Finds the location of new key to be inserted
      //b) Moves all greater keys to one place ahead
      while (0 <= i && k < keys[i]) {
        keys[i + 1] = keys[i];
        i--;
      }

      keys[i + 1] = k;
      ++node.n;
    } else {//If this node is not leaf
      //Find the child which is going to have the new key
      while (0 <= i && k < keys[i]) {
        i--;
      }
      BTreeNode[] child = node.child;
      if (child[i + 1].n == 2 * t - 1) {
        splitChild(i + 1, node);

        //After split, the middle key of child[i] goes up and
        //child[i] is splitted into two. See which of the two
        // is going to have the new key
        if (keys[i + 1] < k) {
          i++;
        }
      }
      insertNotFull(k, child[i + 1]);
    }
  }

  // A utility function to split the child indexed by i of this node
  // Note that node must be full when this function is called
  private void splitChild(int i, BTreeNode node) {
    //Split y into two nodes,  y and z
    BTreeNode y = node.child[i];

    //Create a new node which is gong to store
    //the last(t - 1) keys of y
    BTreeNode z = new BTreeNode(t, y.leaf);
    z.n = t - 1;

    //Copy the last(t-1) keys of y to z
    System.arraycopy(y.keys, t, z.keys, 0, t - 1);
    for (int j = t, size = 2 * t - 1; j < size; j++) {
      //z.keys[j] = y.keys[j + t];
      y.keys[j] = 0;
    }

    //Copy the last t child of y to z
    if (!y.leaf) {
      System.arraycopy(y.child, t, z.child, 0, t);
      for (int j = t, size = 2 * t; j < size; j++) {
        //z.child[j] = y.child[j + t];
        y.child[j] = null;
      }
    }

    //Reduce the number of keys in y
    y.n = t - 1;

    //Since this node going to have a new child,
    //create space of new child
/*/       for (int j = node.n; i + 1 <= j; j--) {
            node.child[j + 1] = node.child[j];
        }*/
    int start = i + 1;
    if (start <= node.n) {//Have something to move?
      System.arraycopy(node.child, start, node.child, start + 1, node.n - i);
    }
    // Link the new child to this node
    node.child[start] = z;

    //A key of y will move to this node,FInd location of
    //new key and move all greater keys one space ahead
/*        for (int j = node.n - 1; i <= j; j--) {
            node.keys[j + 1] = node.keys[j];
        }*/
    System.arraycopy(node.keys, i, node.keys, i + 1, node.n - i);

    //Copy the middle key of y to this node, increment count of keys in this node
    node.keys[i] = y.keys[t - 1];
    y.keys[t - 1] = 0;
    node.n++;
  }

  public void remove(int k) {
    if (root == null) {
      System.out.println("The tree is empty");
      return;
    }
    remove(k, root);
    if (root.n == 0) {
      if (root.leaf) {
        root = null;
      } else {
        root = root.child[0];
      }
    }
  }

  private void remove(int k, BTreeNode node) {

    int idx = node.findKey(k);
    //The key to be removed is present in this node
    if (idx < node.n && node.keys[idx] == k) {
      //If the node is a leaf node - removeFromLeaf is called
      //Otherwise, removeFromNonLeaf is called
      if (node.leaf) {
        removeFromLeaf(idx, node);
      } else {
        removeFromNonLeaf(idx, node);
      }
    }
    //The key is not in this node, maybe in the somewhere of subtree
    else {
      //If this node is a leaf node, then the key is not present in tree
      if (node.leaf) {
        System.out.format("The key %s is does not exist in the tree\n", k);
        return;
      }

      //The key to be removed is present in the sub-tree rooted with this node
      //The flag indicates whether the key is present in the sub-tree rooted
      //with the last child of this node
      boolean flag = (idx == node.n);

      //If the child where the key is supposed to exist has less than k keys
      // we fill that child
      if (node.child[idx].n < t) {
        fill(idx, node);
      }

      //If the last child has been merge, it must have merged with the
      //previous child and so we recurse on the (idx - 1)th child. Else, we
      //recurse on t he (idx)th child which now has at least t keys
      if (flag && node.n < idx) {
        remove(k, node.child[idx - 1]);
      } else {
        remove(k, node.child[idx]);
      }
    }
  }

  //A function to remove the idx-th key from this node - which is a leaf node
  private void removeFromLeaf(int idx, BTreeNode node) {
    if (idx + 1 < node.n) {
      System.arraycopy(node.keys, idx + 1, node.keys, idx, node.n - (idx + 1));
    }
    //Reduce the count of keys
    System.out.println("Deleting " + node.keys[idx]);
    node.keys[--node.n] = 0;
  }

  //A function to remove the idx-th key from this node - which is a non-leaf node
  private void removeFromNonLeaf(int idx, BTreeNode node) {
    int k = node.keys[idx];

    // If the child that precedes K (node.child[idx]) has at least t keys,
    // find the predecessor 'pred' of k in the subtree rooted at
    // node.child[idx]. Replace k by pred. Recursively delete pred
    // in node.child[idx]
    if (t <= node.child[idx].n) {
      int pred = getPred(idx, node);
      node.keys[idx] = pred;
      remove(pred, node.child[idx]);
    }
    // If the node.child[idx] has less than t keys, examine node.child[idx + 1].
    // If node.child[idx + 1] has at least t keys, find the successor 'succ' of k
    // in the subtree rooted at node.child[idx + 1]
    //Replace k by succ
    //Recursively delete succ in C[idx + 1]
    else if (t <= node.child[idx + 1].n) {
      int succ = getSucc(idx, node);
      node.keys[idx] = succ;
      remove(succ, node.child[idx + 1]);
    }
    // If both node.child[idx] and node.child[idx+1] has less than t keys,
    // merge k and all of node.child[idx+1] into node.child[idx]
    // Now node.child[idx] contains 2t - 1 keys
    // Free node.child[idx+1] and recursively delete k from node.child[idx]
    else {
      merge(idx, node);
      remove(k, node.child[idx]);
    }
  }

  //A function to get predecessor of keys[idx]
  private int getPred(int idx, BTreeNode node) {
    // Keep moving to the right most node until we reach a leaf
    BTreeNode cur = node.child[idx];
    while (!cur.leaf) {
      cur = cur.child[cur.n];
    }

    return cur.keys[cur.n - 1];
  }

  private int getSucc(int idx, BTreeNode node) {
    // Keep moving to the left most node until we reach a leaf
    BTreeNode cur = node.child[idx + 1];
    while (!cur.leaf) {
      cur = cur.child[0];
    }

    return cur.keys[0];
  }

  // A function to fill child node.child[idx] which has less then t-1 keys
  private void fill(int idx, BTreeNode node) {
    BTreeNode[] child = node.child;
    //If the previous child[idx-1] has more than t-1 keys , borrow a key
    //from that child
    if (idx != 0 && t <= child[idx - 1].n) {
      borrowFromPrev(idx, node);
    }
    //If the next child[idx+1] has more than t - 1 keys, borrow a key
    //from that child
    else if (idx != node.n && t <= child[idx + 1].n) {
      borrowFromNext(idx, node);
    }
    //Merge child[idx] with its sibling
    //If C[idx] is the last child, merge it with its previous sibling
    //Otherwise merge it with its next sibling
    else {
      if (idx != node.n) {
        merge(idx, node);
      } else {
        merge(idx - 1, node);
      }
    }
  }

  // A function to borrow a key from node.child[idx-1] and insert it
  // into node.child[idx]
  private void borrowFromPrev(int idx, BTreeNode node) {
    //Everything is child is greater than siblings
    BTreeNode child = node.child[idx];
    BTreeNode sibling = node.child[idx - 1];

    //The last key from sibling goes up to the parent and key[idx-1]
    // from parent is inserted as the first key in child. Thus, the loses
    // siblings one key and child gains one key

    // Moving all key in child one step ahead
    if (0 <= child.n - 1) {
      System.arraycopy(child.keys, 0, child.keys, 1, child.n - 1);
    }

    // If child is not a leaf, move all it child pointers one step ahead
    if (!child.leaf) {
      System.arraycopy(node.child, 0, node.child, 1, child.n);
    }

    child.keys[0] = node.keys[idx - 1];
    // Moving siblings's last child as child first child
    if (!child.leaf) {
      child.child[0] = sibling.child[sibling.n];
      sibling.child[sibling.n] = null;
    }

    //Moving the key from the siblings to the parent
    //This reduce the number of keys in the siblings
    node.keys[idx - 1] = sibling.keys[sibling.n - 1];
    sibling.keys[sibling.n - 1] = 0;

    child.n += 1;
    sibling.n -= 1;
  }

  // A function to borrow a key from node.child[idx+1]
  // and place it node.child[idx]
  private void borrowFromNext(int idx, BTreeNode node) {
    // Everything in child is less than siblings
    BTreeNode child = node.child[idx];
    BTreeNode siblings = node.child[idx + 1];

    //node.keys[idx] is inserted as the lst key in child
    child.keys[child.n] = node.keys[idx];

    //Siblings's first child is inserted as the last child
    //into child
    if (!child.leaf) {
      child.child[child.n + 1] = siblings.child[0];
    }

    //The first key from siblings i inserted to node.key[idx]
    node.keys[idx] = siblings.keys[0];

    //Moving all keys in siblings one step behind
    System.arraycopy(siblings.keys, 1, siblings.keys, 0, siblings.n - 1);
    siblings.keys[siblings.n - 1] = 0;

    //Moving the child pointers one step behind
    if (!siblings.leaf) {
      System.arraycopy(siblings.child, 1, siblings.child, 0, siblings.n);
      siblings.child[siblings.n] = null;
    }

    child.n += 1;
    siblings.n -= 1;
  }

  //A function to merge node.child[idx] with node.child[idx+1]
  //both has less than t keys
  private void merge(int idx, BTreeNode node) {
    BTreeNode child = node.child[idx];
    BTreeNode sibling = node.child[idx + 1];

    //Pulling a key from the current node and inserting it into
    //(t-1)th position of child
    child.keys[t - 1] = node.keys[idx];

    //Copying the keys from sibling to child at the end
    System.arraycopy(sibling.keys, 0, child.keys, t, sibling.n);

    if (!child.leaf) {
      System.arraycopy(sibling.child, 0, child.child, t, sibling.n + 1);
    }

    //Moving all keys after idx in the current node one step before
    //to fill the gap created by moving node.keys[idx] to child[idx]
    if (idx + 1 < node.n) {
      System.arraycopy(node.keys, idx + 1, node.keys, idx, node.n - (idx + 1));
    }

    //remove sibling from this node
    int siblingIdx = idx + 1, childs = node.n;
    if (siblingIdx + 1 <= node.n) {
      System.arraycopy(node.child, siblingIdx + 1, node.child, siblingIdx, childs - siblingIdx);
    }

    //Updating the key count of child and the current node
    child.n += sibling.n + 1;
    node.child[node.n] = null;
    node.keys[--node.n] = 0;
  }

  // All keys of a node are sorted in increasing order.
  // The child between two keys k1 and k2 contains all
  // keys in the range from (k1 k2).
  public static class BTreeNode {

    private int[] keys;
    private int n;// num of keys;
    //Number of children of a node is equal to the number of keys in it plus 1.
    private BTreeNode[] child;
    private boolean leaf;

    private BTreeNode(int t, boolean leaf) {
      this.leaf = leaf;

      keys = new int[2 * t - 1];
      child = new BTreeNode[2 * t];
    }

    //A utility function that return the index of the first key that
    //is greater than or equal to k
    private int findKey(int k) {
      int idx = 0;
      while (idx < n && keys[idx] < k) {
        ++idx;
      }
      return idx;
    }
  }

}
