package dsa.tree;

public class BinarySearchTree<Key extends Comparable<Key>, Value> extends BinaryTree<Value> {

    private BinaryNode root;

    private BinaryNode min(BinaryNode x) {
        while(hasLeftChild(x)) {
            x = x.left;
        }

        return x;
    }

    private BinaryNode deleteMin(BinaryNode x) {
        if(x.left == null) { return x.right; }
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    public Value min() {
        return root == null ? null : min(root).value;
    }

    public Value deleteMin() {
        BinaryNode old = min(root);
        root = deleteMin(root);
        return old == null ? null : old.value;
    }

    private BinaryNode max(BinaryNode x) {
        while(hasRightChild(x)) {
            x = x.right;
        }

        return x;
    }

    private BinaryNode deleteMax(BinaryNode x) {
        if(x.right == null) { return x.left; }
        x.right = deleteMax(x.right);
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    public Value max() {
        return root == null ? null : max(root).value;
    }

    public Value deleteMax() {
        BinaryNode old = max(root);
        root = deleteMax(root);
        return old == null ? null : old.value;
    }

    private BinaryNode delete(BinaryNode x, Key key) {
        if(x == null) { return null; }

        int compareResult = key.compareTo(x.key);
        if(compareResult < 0) {
            x.left = delete(x.left, key);
        } else if(0 < compareResult) {
            x.right = delete(x.right, key);
        } else {
            if(!hasRightChild(x)) { return x.left; }
            if(!hasLeftChild(x)) { return x.right; }
            BinaryNode temp = x;
            x = min(temp.right);
            x.right = deleteMax(temp.right);
            x.left = temp.left;
        }

        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return x;
    }

    public Value delete(Key key) {
        BinaryNode old = get(root, key);
        root = delete(root, key);
        return old.value;
    }

    private BinaryNode get(BinaryNode root, Key key) {
        BinaryNode x = root;
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
        BinaryNode x =  get(root, key);
        return x == null ?  null : x.value;
    }

    public void put(Key key, Value value) {
         root = put(null, root, key, value);
    }

    private BinaryNode put(BinaryNode parent, BinaryNode x, Key key, Value value) {
        if(x == null) {
            return new BinaryNode(parent, key, value);
        }

        int compareResult = key.compareTo(x.key);

        if(compareResult < 0) {
            x.left = put(x, x.left, key, value);
        } else if(0 < compareResult) {
            x.right = put(x, x.right, key, value);
        } else {
            x.value = value;
        }

        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return x;

    }

    @Override
    protected Node<Value> getRoot() {
        return root;
    }

    private class BinaryNode implements Node<Value> {

        Key key;
        Value value;
        BinaryNode parent, left, right;
        int size = 1;
        int height = 0;

        public BinaryNode(BinaryNode parent, Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public int height() {
            return height;
        }

        @Override
        public Value getValue() {
            return value;
        }

        @Override
        public Node<Value> getParent() {
            return parent;
        }

        @Override
        public Node<Value> getLeft() {
            return left;
        }

        @Override
        public Node<Value> getRight() {
            return right;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("key=").append(key);
            sb.append(", value=").append(value);
            sb.append(", size=").append(size);
            sb.append(", height=").append(height);
            if(left != null) { sb.append(", left=").append(left.key); }
            if(right != null) { sb.append(", right=").append(right.key); }
            sb.append('}');
            return sb.toString();
        }
    }
}
