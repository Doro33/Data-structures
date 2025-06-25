package org.example;

import lombok.Data;

@Data
public class AVLTree<T extends Comparable<T>> {
    private Node<T> root;

    public int updateHeight() {
        return updateHeight(root);
    }

    public int updateHeight(Node<T> node) {
        if (node == null) return -1;
        int left = updateHeight(node.getLeft());
        int right = updateHeight(node.getRight());
        node.setHeight(Math.max(left, right) + 1);
        return Math.max(left, right) + 1;
    }

    private int getBalance(Node<T> node) {
        if (node == null) return 0;
        int leftHeight = node.getLeft() != null ? node.getLeft().getHeight() : -1;
        int rightHeight = node.getRight() != null ? node.getRight().getHeight() : -1;
        return leftHeight - rightHeight;
    }

    private void singleNodeHeightUpdate(Node<T> node) {
        if (node == null) return;
        int left = node.getLeft() == null ? -1 : node.getLeft().getHeight();
        int right = node.getRight() == null ? -1 : node.getRight().getHeight();
        int height = Math.max(left, right) + 1;
        node.setHeight(height);
    }

    private Node<T> rightRotate(Node<T> node) {
        if (node == null)
            return null;
        Node<T> newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);
        singleNodeHeightUpdate(node);
        singleNodeHeightUpdate(newRoot);
        return newRoot;
    }

    private Node<T> leftRotate(Node<T> node) {
        if (node == null)
            return null;
        Node<T> newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);
        singleNodeHeightUpdate(node);
        singleNodeHeightUpdate(newRoot);
        return newRoot;
    }

    private Node<T> leftRightRotate(Node<T> node) {
        if (node == null)
            return null;
        node.setLeft(leftRotate(node.getLeft()));
        return rightRotate(node);
    }

    private Node<T> rightLeftRotate(Node<T> node) {
        if (node == null)
            return null;
        node.setRight(rightRotate(node.getRight()));
        return leftRotate(node);
    }

    public void insert(T data) {
        if (root == null)
            root = new Node<>(data);
        else
            root = insert(root, data);
    }

    private Node<T> insert(Node<T> node, T data) {
        //inserting
        if (node == null) return new Node<>(data);

        if (data.compareTo(node.getData()) == 0)
            return node;

        if (data.compareTo(node.getData()) < 0)
            node.setLeft(insert(node.getLeft(), data));
        else
            node.setRight(insert(node.getRight(), data));
        //height updating
        singleNodeHeightUpdate(node);

        //rotating and returning
        int balance = getBalance(node);
        if (1 < balance && data.compareTo(node.getLeft().getData()) < 0)
            return rightRotate(node);

        if (1 < balance && data.compareTo(node.getLeft().getData()) > 0)
            return leftRightRotate(node);

        if (balance < -1 && data.compareTo(node.getRight().getData()) < 0)
            return rightLeftRotate(node);

        if (balance < -1 && data.compareTo(node.getRight().getData()) > 0)
            return leftRotate(node);

        return node;
    }

    public boolean contains(T data) {
        if (root == null)
            return false;

        Node<T> current = root;
        while (current != null) {
            if (data.compareTo(current.getData()) == 0)
                return true;

            current = data.compareTo(current.getData()) < 0 ? current.getLeft() : current.getRight();
        }
        return false;
    }

    public void prettyPrint() {
        printTree(root, "", true);
    }

    private void printTree(Node<T> node, String prefix, boolean isTail) {
        if (node == null) return;

        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.getData());

        // Collect non-null children
        boolean hasLeft = node.getLeft() != null;
        boolean hasRight = node.getRight() != null;

        if (hasLeft || hasRight) {
            if (hasRight) {
                printTree(node.getRight(), prefix + (isTail ? "    " : "│   "), !hasLeft);
            }
            if (hasLeft) {
                printTree(node.getLeft(), prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }

    private int countChildren(Node<T> node) {
        if (node == null)
            return 0;
        int counter = 0;
        if (node.getLeft() != null)
            counter++;
        if (node.getRight() != null)
            counter++;
        return counter;
    }

    private Node<T> delete(Node<T> node, T data) {
        if(node != null){
            if (data.compareTo(node.getData()) < 0)
                node.setLeft(delete(node.getLeft(), data));
            else if (data.compareTo(node.getData()) > 0)
                node.setRight(delete(node.getRight(), data));
            else{
                int childNu = countChildren(node);
                if (childNu == 0)
                    return null;
                if (childNu == 1)
                    return node.getLeft() != null ? node.getLeft() : node.getRight();
                if(childNu == 2){
                    T predecessor = findPredecessor(node);
                    node.setData(predecessor);
                    node.setLeft(delete(node.getLeft(), predecessor));
                }
            }

            singleNodeHeightUpdate(node);
            int balance = getBalance(node);

            if (1 < balance && getBalance(node.getLeft()) >= 0)
                return rightRotate(node);

            if (1 < balance && getBalance(node.getLeft()) < 0)
                return leftRightRotate(node);

            if (balance < -1 && getBalance(node.getRight()) <= 0)
                return leftRotate(node);

            if (balance < -1 && getBalance(node.getRight()) > 0)
                return rightLeftRotate(node);

            return node;
        }
        return null;
    }

    private T findPredecessor(Node<T>  node) {
        Node<T> current = node.getLeft();
        while (current.getRight() != null)
            current = current.getRight();
        return current.getData();
    }

    public void delete(T data) {
        root = delete(root, data);
    }
}
