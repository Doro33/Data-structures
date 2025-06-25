package org.example;

import lombok.Data;

@Data
public class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;

    public void insert(T data) {
        if (root == null)
            root = new Node<>(data);
        else
            insert(root, data);
    }

    private void insert(Node<T> node, T data) {
        if (data.compareTo(node.getData()) == 0)
            return;
        if (data.compareTo(node.getData()) < 0)
            if (node.getLeft() == null)
                node.setLeft(new Node<>(data));
            else
                insert(node.getLeft(), data);
        else if (node.getRight() == null)
            node.setRight(new Node<>(data));
        else
            insert(node.getRight(), data);
    }

    public boolean contains(T data) {
        return find(data) != null;
    }

    private Node<T> find(T data) {
        if (root == null)
            return null;

        if (data.compareTo(root.getData()) == 0)
            return root;

        Node<T> parent = findParent(data);
        if (parent == null)
            return null;
        if (parent.getLeft() != null && parent.getLeft().getData().equals(data))
            return parent.getLeft();
        if (parent.getRight() != null && parent.getRight().getData().equals(data))
            return parent.getRight();

        return null;
    }

    public T findMin() {
        if (root == null)
            return null;
        Node<T> leftNode = root;
        while (leftNode.getLeft() != null)
            leftNode = leftNode.getLeft();

        return leftNode.getData();
    }

    public T findMax() {
        if (root == null)
            return null;
        Node<T> rightNode = root;
        while (rightNode.getRight() != null)
            rightNode = rightNode.getRight();
        return rightNode.getData();
    }

    public void delete(T data) {
        if (root == null)
            throw new RuntimeException("Cannot delete an element cause the tree is empty");

        if (data.equals(root.getData())) {
            int childNu = countChildren(root);
            if (childNu == 0) {
                root = null;
            }
            if (childNu == 1) {
                if (root.getLeft() != null) {
                    root.setData(root.getLeft().getData());
                    root.setLeft(root.getLeft().getLeft());
                } else if (root.getRight() != null) {
                    root.setData(root.getRight().getData());
                    root.setRight(root.getRight().getRight());
                }
            }
            if (childNu == 2) {
                T predecessor = findPredecessor(data);
                delete(predecessor);
                root.setData(predecessor);
            }
        } else {
            Node<T> parent = findParent(data);
            if (parent == null)
                return;

            if (parent.getLeft() != null && parent.getLeft().getData().equals(data)) {
                int childNu = countChildren(parent.getLeft());
                if (childNu == 0)
                    parent.setLeft(null);
                if (childNu == 1)
                    parent.setLeft(
                            parent.getLeft().getLeft() != null ? parent.getLeft().getLeft() : parent.getLeft().getRight()
                    );
                if (childNu == 2) {
                    T predecessor = findPredecessor(data);
                    delete(predecessor);
                    parent.getLeft().setData(predecessor);
                }
            } else {
                int childNu = countChildren(parent.getRight());
                if (childNu == 0)
                    parent.setRight(null);
                if (childNu == 1)
                    parent.setRight(
                            parent.getRight().getLeft() != null ? parent.getRight().getLeft() : parent.getRight().getRight()
                    );
                if (childNu == 2) {
                    T predecessor = findPredecessor(data);
                    delete(predecessor);
                    parent.getRight().setData(predecessor);
                }
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

    public Node<T> findParent(T data) {
        if (root == null || data.compareTo(root.getData()) == 0)
            return null;

        Node<T> parentNode = root;

        while (parentNode != null) {
            Node<T> leftNode = parentNode.getLeft();
            Node<T> rightNode = parentNode.getRight();
            if (leftNode != null && leftNode.getData().equals(data) ||
                    (rightNode != null && rightNode.getData().equals(data)))
                return parentNode;

            if (data.compareTo(parentNode.getData()) < 0)
                parentNode = parentNode.getLeft();
            else
                parentNode = parentNode.getRight();
        }
        return null;
    }

    public BinarySearchTree<T> subTree(T data) {
        Node<T> root = find(data);
        BinarySearchTree<T> output = new BinarySearchTree<>();
        output.setRoot(root);
        return output;
    }

    public T findPredecessor(T data) {
        BinarySearchTree<T> tree = subTree(data);
        if (tree == null || tree.getRoot() == null || tree.getRoot().getLeft() == null)
            return null;
        Node<T> predecessor = tree.getRoot().getLeft();

        while (predecessor.getRight() != null) {
            predecessor = predecessor.getRight();
        }
        return predecessor.getData();
    }

    public void inOrderTraversal(Node<T> node) {
        if (node == null)
            return;
        inOrderTraversal(node.getLeft());
        System.out.print(node.getData() + "   ");
        inOrderTraversal(node.getRight());
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

    public int findHeight(Node<T> node) {
        if (node == null) return -1;
        int left = findHeight(node.getLeft());
        int right = findHeight(node.getRight());
        return Math.max(left, right) + 1;
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node<T> node) {
        if (node == null) return true;
        int leftHeight = findHeight(node.getLeft());
        int rightHeight = findHeight(node.getRight());
        if(Math.abs(leftHeight - rightHeight) > 1) return false;

        return isBalanced(node.getLeft()) && isBalanced(node.getRight());
    }
}
