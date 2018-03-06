package dsa.tree;

public class BinarySearchTree<Key extends Comparable<Key>, Value> extends BinaryTree<Value> {

    private BinaryNode root;

    private Value get(BinaryNode root, Key key) {
        BinaryNode x = root;
        while(x != null) {
            int compareResult = x.key.compareTo(key);

            if(compareResult < 0) {
                x = x.left;
            } else if(0 < compareResult) {
                x = x.right;
            } else {
                return x.value;
            }
        }

        return null;
    }

    public Value get(Key key) {
        return get(root, key);
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
