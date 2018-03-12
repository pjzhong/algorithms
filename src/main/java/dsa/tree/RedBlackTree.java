package dsa.tree;

public class RedBlackTree<Key extends Comparable<Key>, Value> extends BinaryTree<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private RedBlackNode root;

    public void put(Key key, Value value) {
        put(root, key, value);
    }

    private void put(RedBlackNode node, Key key, Value value) {
        if(node == null) {
            root = new RedBlackNode(null, key, value, BLACK);
            return;
        }

        RedBlackNode x = node, parent = node;
        int comparisonResult = 0;
        while(x != null) {
            comparisonResult = key.compareTo(x.getKey());

            parent = x;
            if(comparisonResult < 0) {
                x = (RedBlackNode) x.left;
            } else if(0 < comparisonResult) {
                x = (RedBlackNode) x.right;
            } else {
                node.setValue(value);
                return;
            }
        }

        if(comparisonResult < 0) {
            parent.left = new RedBlackNode(parent, key, value);
        } else {
            parent.right = new RedBlackNode(parent, key, value);
        }

        balanceAfterInsert((RedBlackNode) (comparisonResult < 0 ? parent.left : parent.right));
    }

    //A double red problem is corrected with zero or more recoloring followed by zer or one restructuring
    private void balanceAfterInsert(RedBlackNode n) {
        setColor(n, RED);

        if( !isRoot(n) && isRed(parentOf(n))) {
            if(isRed(siblingOf(parentOf(n)))) {//recoloring
                setColor(parentOf(n), BLACK);
                setColor(siblingOf(parentOf(n)), BLACK);
                setColor(grandParentOf(n), RED);
                balanceAfterInsert(grandParentOf(n));
            } else if(parentOf(n) == leftOf(grandParentOf(n))) {//left-left or left-right
                if(n == rightOf(parentOf(n))) {
                    rotateLeft(n = parentOf(n));
                }
                setColor(parentOf(n), BLACK);
                setColor(grandParentOf(n), RED);
                rotateRight(grandParentOf(n));
            } else if(parentOf(n) == rightOf(grandParentOf(n))) {//right-right or right-left
                if(n == leftOf(parentOf(n))) {
                    rotateRight(n = parentOf(n));
                }
                setColor(parentOf(n), BLACK);
                setColor(grandParentOf(n), RED);
                rotateLeft(grandParentOf(n));
            }
        }

        setColor(root, BLACK);
    }

    private void rotateRight(Node n) {
        if(!hasLeftChild(n)) { return; }

        Node oldLeft = n.getLeft();
        n.setLeft(oldLeft.getRight());
        if(!hasParent(n)) {
            root = (RedBlackNode) oldLeft;
        } else if(n.getParent().getLeft() == n) {
            n.getParent().setLeft(oldLeft);
        } else  {
            n.getParent().setRight(oldLeft);
        }
        oldLeft.setRight(n);
    }

    private void rotateLeft(Node n) {
        if(!hasRightChild(n)) { return; }

        Node oldRight = n.getRight();
        n.setRight(oldRight.getLeft());
        if(!hasParent(n)) {
            root = (RedBlackNode) oldRight;
        } else if(n.getParent().getLeft() == n) {
            n.getParent().setLeft(oldRight);
        } else {
            n.getParent().setRight(oldRight);
        }
        oldRight.setLeft(n);
    }

    @Override
    protected Node getRoot() {
        return root;
    }

    private boolean isRed(RedBlackNode node) {
        return node != null && node.color;
    }

    private void setColor(RedBlackNode node , boolean color) {
        if(node != null) {
            node.color = color;
        }
    }

    private RedBlackNode parentOf(RedBlackNode node) {
        return hasParent(node) ? (RedBlackNode) node.getParent() : null;
    }

    private RedBlackNode grandParentOf(RedBlackNode node) {
        return (hasParent(node) && hasParent(node.getParent())) ? (RedBlackNode) node.getParent().getParent() : null;
    }

    private RedBlackNode siblingOf(RedBlackNode node) {
        return hasParent(node) ? (RedBlackNode) (
                node.getParent().getLeft() == node ?
                        node.getParent().getRight() :
                        node.getParent().getLeft())
                : null;
    }

    private class RedBlackNode extends Node {
        boolean color = BLACK;

        public RedBlackNode(Node parent, Key key, Value value) {
            super(parent, key, value);
        }

        public RedBlackNode(Node parent, Key key, Value value, Boolean color) {
            this(parent, key, value);
            this.color = color;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("key=").append(key);
            sb.append(", value=").append(value);
            sb.append(", size=").append(size);
            sb.append(", height=").append(height);
            sb.append(", color=").append(color ? "red" : "black");
            sb.append('}');
            return sb.toString();
        }
    }
}
