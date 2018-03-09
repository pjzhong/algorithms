package dsa.tree;

public class AVLBinarySearchTree<Key extends Comparable<Key>, Value> extends BinaryTree<Key, Value> {

    private Node root;

    private int balanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    private Node updateNode(Node node) {
        node.size = 1 + size(node.left) + size(node.right);
        node.height = 1 + Math.max(height(node.left), height(node.right));

        return node;
    }

    private Node rotateLeft(Node x) {
        Node rotate = x.right;
        if(hasLeftChild(rotate)) { rotate.left.parent = x; }
        x.right = rotate.left;
        rotate.left = x;
        rotate.parent = x.parent;
        x.parent = rotate;
        updateNode(x);
        updateNode(rotate);
        return rotate;
    }

    private Node rotateRight(Node x) {
        Node rotate = x.left;
        if(hasRightChild(rotate)) { rotate.right.parent = x; }
        x.left = rotate.right;
        rotate.right = x;
        rotate.parent = x.parent;
        x.parent = rotate;
        updateNode(x);
        updateNode(rotate);
        return rotate;
    }

    private Node balance(Node x) {
        if(balanceFactor(x) < -1) {
            if(0 < balanceFactor(x.right)) {
                x.right = rotateRight(x.right);//todo rotateRight
            }
            x = rotateLeft(x);//todo rotateLeft
        } else if(1 < balanceFactor(x)) {
            if(balanceFactor(x.left) < 0) {
                x.left = rotateLeft(x.left);//todo rotateLeft
            }
            x = rotateRight(x); //todo rotateRight
        }
        return x;
    }

    private Node min(Node x) {
        while(hasLeftChild(x)) {
            x = x.left;
        }
        return x;
    }

    private Node deleteMin(Node x) {
        if(x.left == null) { return x.right; }
        x.left = deleteMin(x.left);
        updateNode(x);

        return balance(x);
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
        while(hasRightChild(x)) {
            x = x.right;
        }

        return x;
    }

    private Node deleteMax(Node x) {
        if(x.right == null) { return x.left; }
        x.right = deleteMax(x.right);
        updateNode(x);

        return balance(x);
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
        if(x == null) { return null; }

        int compareResult = key.compareTo(x.key);
        if(compareResult < 0) {
            x.left = delete(x.left, key);
        } else if(0 < compareResult) {
            x.right = delete(x.right, key);
        } else {
            if(!hasRightChild(x)) { return x.left; }
            if(!hasLeftChild(x)) { return x.right; }
            Node temp = x;
            x = min(temp.right);
            x.right = deleteMax(temp.right);
            x.left = temp.left;
        }

        updateNode(x);
        return balance(x);
    }

    public Value delete(Key key) {
        Node old = get(root, key);
        root = delete(root, key);
        return old.value;
    }

    private Node get(Node root, Key key) {
        Node x = root;
        while(x != null) {
            int compareResult = key.compareTo(x.key);

            if(compareResult < 0) {
                x = x.left;
            } else if(0 < compareResult) {
                x = x.right;
            } else {
                return x;
            }
        }

        return null;
    }

    public Value get(Key key) {
        Node x =  get(root, key);
        return x == null ?  null : x.value;
    }

    public void put(Key key, Value value) {
        root = put(null, root, key, value);
    }

    private Node put(Node parent, Node x, Key key, Value value) {
        if(x == null) {
            return new Node(parent, key, value);
        }

        int compareResult = key.compareTo(x.key);

        if(compareResult < 0) {
            x.left = put(x, x.left, key, value);
        } else if(0 < compareResult) {
            x.right = put(x, x.right, key, value);
        } else {
            x.value = value;
        }

        updateNode(x);
        return balance(x);
    }

    @Override
    protected Node getRoot() {
        return root;
    }

}
