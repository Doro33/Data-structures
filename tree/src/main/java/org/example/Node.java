package org.example;

import lombok.Data;

@Data
public class Node<T> {
    private T data;
    private Node<T> right;
    private Node<T> left;
    private int height;

    public Node(T data) {
        this.data = data;
        this.height = 0;
    }
}
