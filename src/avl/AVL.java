package avl;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;


public class AVL<T extends Comparable<T>> extends AbstractSet<T> {
    private static class Node<T> {
        Node<T> left = null;
        Node<T> right = null;
        final T value;
        int height = 1;

        Node(T value) {
            this.value = value;
        }

        int getHeight() {
            return height;
        }

        boolean isBalance() {
            int lHeight = (left == null) ? 0 : left.getHeight();
            int rHeight = (right == null) ? 0 : right.getHeight();
            int delta = Math.abs(lHeight - rHeight);
            return delta == 1 || delta == 0;
        }

        int getBalance() {
            int lHeight = (left == null) ? 0 : left.getHeight();
            int rHeight = (right == null) ? 0 : right.getHeight();
            return rHeight - lHeight;
        }

        void isHeight() {
            int lHeight = (left == null) ? 0 : left.getHeight();
            int rHeight = (right == null) ? 0 : right.getHeight();
            this.height = (lHeight > rHeight ? lHeight : rHeight) + 1;
        }
    }

    private int size = 0;
    private Node<T> root = null;

    @Override
    public boolean add(T t) {
        if (root == null) {
            root = new Node<>(t);
            size++;
            return true;
        }
        Node<T> near = findNear(t);
        if (near.value == t) {
            return false;
        }
        root = add(root, t);
        size++;
        return true;
    }

    private Node<T> add(Node<T> node, T t) {
        if (node == null) {
            return new Node<>(t);
        }
        int comparation = t.compareTo(node.value);
        if (comparation == 0) {
            return node;
        }
        else if (comparation < 0) {
            node.left = add(node.left, t);
        }
        else {
            node.right = add(node.right, t);
        }
        return balance(node);
    }

    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> node = node(t);
        if (node == null) return false;
        Node<T> parent = parent(node);
        if (parent == null) {
            root = delete(node);
            size--;
            return true;
        }
        int comparation = node.value.compareTo(parent.value);
        assert comparation != 0;
        if (comparation < 0) {
            parent.left = delete(node);
        }
        else {
            parent.right = delete(node);
        }
        size--;
        return true;
    }

    private Node<T> findNear(T value) {
        if (root == null) return null;
        return findNear(root, value);
    }

    private Node<T> findNear(Node<T> start, T value) {
        int comparation = value.compareTo(start.value);
        if (comparation == 0) {
            return start;
        }
        else if (comparation < 0) {
            if (start.left == null) return start;
            return findNear(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return findNear(start.right, value);
        }
    }

    private Node<T> parent(Node<T> child) {
        if (root == null || child == null) return null;
        return parent(root, child.value);
    }

    private Node<T> parent(Node<T> start, T value) {
        int comparation = value.compareTo(start.value);
        if (comparation == 0) {
            return null;
        }
        else if (comparation < 0) {
            if (start.left.value == value) return start;
            return parent(start.left, value);
        }
        else {
            if(start.right.value == value) return start;
            return parent(start.right, value);
        }
    }

    private Node<T> node(T value) {
        if (root == null) return null;
        return node(root, value);
    }

    private Node<T> node(Node<T> start, T value) {
        int comparation = value.compareTo(start.value);
        if (comparation == 0) {
            return start;
        }
        else if (comparation < 0) {
            if (start.left == null) return null;
            return node(start.left, value);
        }
        else {
            if(start.right == null) return null;
            return node(start.right, value);
        }
    }

    private Node<T> rightTurn(Node<T> node) {
        Node<T> turnedNode = node.left;
        node.left = turnedNode.right;
        turnedNode.right = node;
        node.isHeight();
        turnedNode.isHeight();
        return turnedNode;
    }

    private Node<T> leftTurn(Node<T> node) {
        Node<T> turnedNode = node.right;
        node.right = turnedNode.left;
        turnedNode.left = node;
        node.isHeight();
        turnedNode.isHeight();
        return turnedNode;
    }

    private Node<T> balance(Node<T> node) {
        node.isHeight();
        if(node.isBalance()) {
            return node;
        }
        int balance = node.getBalance();
        assert balance < 2 || balance > -2;
        if(balance == 2) {
            if(node.right.getBalance() < 0) {
                node.right = rightTurn(node.right);
            }
            return leftTurn(node);
        }
        else {
            if(node.left.getBalance() > 0) {
                node.left = leftTurn(node.left);
            }
            return rightTurn(node);
        }
    }

    public boolean isBalance() {
        return root.isBalance();
    }

    private Node<T> delete(Node<T> node) {
        assert node.isBalance();
        if (node.right == null) {
            return node.left;
        }
        else {
            if (node.left == null) {
                return node.right;
            }
            Node<T> minLeft = node.right;
            Node<T> minLeftParent = node;
            while (minLeft.left != null) {
                minLeftParent = minLeft;
                minLeft = minLeft.left;
            }
            minLeft.left = node.left;
            if (minLeftParent != node) {
                minLeftParent.left = minLeft.right;
                minLeft.right = deleteMin(node.right);
            }
            return balance(minLeft);
        }
    }

    private Node<T> deleteMin(Node<T> node) {
        if (node.left == null) {
            return node;
        }
        node.left = deleteMin(node.left);
        return balance(node);
    }

    public boolean isInvariant() {
        if (root == null) {
            return true;
        }
        return isInvariant(root);
    }

    private boolean isInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !isInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && isInvariant(right);
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> near = findNear(t);
        return near != null && t.compareTo(near.value) == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new AvlIterator();
    }

    @Override
    public int size() {
        return size;
    }

    public class AvlIterator implements Iterator<T> {
        private Node<T> current;
        private Stack<Node<T>> stack = new Stack();

        private AvlIterator() {
            Node<T> node = root;
            while (node != null) {
                stack.push(node);
                current = node;
                node = node.left;
            }
        }

        private Node<T> findNext() {
            Node<T> node = current.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            return stack.pop();
        }

        @Override
        public boolean hasNext() {
            return !stack.empty() || current.right != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }
    }
}