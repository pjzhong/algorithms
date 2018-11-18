package dsa.tree;

/**
 * @link https://www.geeksforgeeks.org/b-tree-set-1-introduction-2/
 */
public class BTree {

    private BTreeNode root;
    //All nodes (including root) may contain at most 2t – 1 keys
    //The factor of 2 will guarantee that nodes can be split or combined.
    //A B-Tree is defined by the term minimum degree 't'. The value of t depends upon disk block size
    //t also mean the maximum num of child
    private int t;

    public BTree(int t) {
        this.t = t;
    }

    private void traverse() {
        traverse(root);
    }

    private void traverse(BTreeNode root) {
        if (root == null) {
            return;
        }

        int[] keys = root.keys;
        BTreeNode[] child = root.child;
        int i;
        for (i = 0; i < root.n; i++) {
            if (!root.leaf) {
                traverse(child[i]);
            }
            System.out.println(keys[i]);
        }

        if (!root.leaf) {
            traverse(child[i]);
        }
    }

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
    private void insert(int k) {
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
    }

    public static void main(String[] args) {
        BTree bTree = new BTree(3);
        for (int i = 0; i <= 10000000; i++) {
            bTree.insert(i);
        }

        bTree.traverse();

        int k = 6;
        System.out.println(bTree.search(k));
    }
}
