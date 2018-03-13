package dsa.tree;

public class RedBlackTree<Key extends Comparable<Key>, Value> extends BinaryTree<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private RedBlackNode root;
    private int size = 0;

    public void put(Key key, Value value) {
        put(root, key, value);
    }

    private void put(RedBlackNode node, Key key, Value value) {
        if(node == null) {
            size++;
            root = new RedBlackNode(null, key, value, BLACK);
            return;
        }

        RedBlackNode x = node, parent = node;
        int comparisonResult = 0;
        while(x != null) {
            comparisonResult = key.compareTo(x.getKey());

            parent = x;
            if(comparisonResult < 0) {
                x = x.getLeft();
            } else if(0 < comparisonResult) {
                x = x.getRight();
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
        size++;
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

    public Value remove(Key key) {
        RedBlackNode node = remove(root, key);
        return node == null ? null : node.value;
    }

    //if the deleted node and its successor, either one is red the process of remove would much easier
    //if not, there four situations would happen
    private RedBlackNode remove(RedBlackNode n, Key key) {
        RedBlackNode node = get(n, key);
        if(node == null) {
            return null;
        } else if(hasLeftChild(node) && hasRightChild(node)) {
            RedBlackNode successor = min(node.getRight());
            node.setKey(successor.key);
            node.setValue(successor.value);
            node = successor;
        }

        //At this point n has zero or one child
        RedBlackNode pullUp = (hasRightChild(node) ? rightOf(node) : leftOf(node));
        if(pullUp != null) {
            if(isRoot(node)) {
                root = pullUp;
            } else if (leftOf(parentOf(node)) == node){
                node.getParent().setLeft(pullUp);
            } else {
                node.getParent().setRight(pullUp);
            }

            if(isBlack(node)) {//If the deleted node is red, no need for balance
                balanceAfterRemove(pullUp);
            }
        } else if(isRoot(node)) {
            root = null;
        } else {
            //The node being deleted acts as double black sentinel
            if(isBlack(node)) {
                balanceAfterRemove(node);
            }
            node.removeFromParent();
        }
        size--;
        return node;
    }

    /**
     * for more info, see the links blow
     * https://www.geeksforgeeks.org/red-black-tree-set-3-delete-2/
     * http://cs.lmu.edu/~ray/notes/redblacktrees/
     * http://www.xuetangx.com/courses/course-v1:TsinghuaX+30240184_2X+sp/pdfbook/0/
     *
     * if you can't figure it out, try to remember it. to my self
     * */
    private void balanceAfterRemove(RedBlackNode n) {
        while(!isRoot(n) && isBlack(n)) {
            if(n == leftOf(parentOf(n))) {//There are mirror case, figure it out.
                RedBlackNode sibling = rightOf(parentOf(n));
                if(isRed(sibling)) {//CASE C III | BB - 3
                    setColor(sibling, BLACK);
                    setColor(parentOf(n), RED);
                    rotateLeft(parentOf(n));
                    sibling = rightOf(parentOf(n));
                }
                if(isBlack(leftOf(sibling)) && isBlack(rightOf(sibling))) {//CASE B  | BB - 2 - B
                    setColor(sibling, RED);
                    n = parentOf(n);
                } else {//CASE A || BB-1
                    if(isBlack(rightOf(sibling))) {
                        setColor(leftOf(sibling), BLACK);
                        setColor(sibling, RED);
                        rotateRight(sibling);
                        sibling = rightOf(parentOf(n));
                    }

                    setColor(sibling, colorOf(parentOf(n)));
                    setColor(parentOf(n), BLACK);
                    setColor(rightOf(sibling), BLACK);
                    rotateLeft(parentOf(n));
                    n = root;
                }
            } else {//The mirror of Left Branch
                RedBlackNode sibling = leftOf(parentOf(n));
                if(isRed(sibling)) {
                    setColor(sibling, BLACK);
                    setColor(parentOf(n), RED);
                    rotateRight(parentOf(n));
                    sibling = leftOf(parentOf(n));
                }

                if(isBlack(leftOf(sibling)) && isBlack(rightOf(sibling))) {
                    setColor(sibling, RED);
                    n = parentOf(n);
                } else {
                    if(isBlack(leftOf(sibling))) {
                        setColor(rightOf(sibling), BLACK);
                        setColor(sibling, RED);
                        rotateLeft(sibling);
                        sibling = leftOf(parentOf(n));
                    }

                    setColor(sibling, colorOf(parentOf(n)));
                    setColor(parentOf(n), BLACK);
                    setColor(leftOf(sibling), BLACK);
                    rotateRight(parentOf(n));
                    n = root;
                }
            }
        }
        setColor(n, BLACK);
    }

    private void rotateRight(RedBlackNode n) {
        if(!hasLeftChild(n)) { return; }

        RedBlackNode oldLeft = n.getLeft();
        n.setLeft(oldLeft.getRight());
        if(!hasParent(n)) {
            root = oldLeft;
        } else if(n.getParent().getLeft() == n) {
            n.getParent().setLeft(oldLeft);
        } else  {
            n.getParent().setRight(oldLeft);
        }
        oldLeft.setRight(n);
    }

    private void rotateLeft(RedBlackNode n) {
        if(!hasRightChild(n)) { return; }

        RedBlackNode oldRight = n.getRight();
        n.setRight(oldRight.getLeft());
        if(!hasParent(n)) {
            root = oldRight;
        } else if(n.getParent().getLeft() == n) {
            n.getParent().setLeft(oldRight);
        } else {
            n.getParent().setRight(oldRight);
        }
        oldRight.setLeft(n);
    }

    public Value get(Key key) {
        RedBlackNode node = get(root, key);
        return node == null ? null : node.value;
    }

    private RedBlackNode get(RedBlackNode root, Key key) {

        RedBlackNode n = root;
        while (n != null) {
            int comparisonResult = key.compareTo(n.key);
            if(comparisonResult < 0) {
                n =  n.getLeft();
            } else if(0 < comparisonResult){
                n =  n.getRight();
            } else {
                return n;
            }
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    private RedBlackNode min(RedBlackNode x) {
        while(hasLeftChild(x)) { x = x.getLeft(); }
        return x;
    }

    private RedBlackNode max(RedBlackNode x) {
        while(hasRightChild(x)) { x = x.getRight(); }
        return x;
    }

    @Override
    protected Node getRoot() {
        return root;
    }

    private boolean isRed(RedBlackNode node) {
        return node != null && node.color;
    }

    private boolean isBlack(RedBlackNode node) {
        return node != null && !node.color;
    }

    private void setColor(RedBlackNode node , boolean color) {
        if(node != null) {
            node.color = color;
        }
    }

    protected boolean colorOf(RedBlackNode node) {
        return node == null ? BLACK : node.color;
    }

    protected RedBlackNode leftOf(RedBlackNode node) {
        return hasLeftChild(node) ? node.getLeft() : null;
    }

    protected RedBlackNode rightOf(RedBlackNode node) {
        return hasRightChild(node) ? node.getRight() : null;
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
        public RedBlackNode getLeft() {
            return (RedBlackNode) left;
        }

        @Override
        public RedBlackNode getRight() {
            return (RedBlackNode) right;
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
