package org.example;

public class LinkedList<E> {

    private Node<E> head;
    private Node<E> tail;

    public LinkedList() {
    }

    public void add(E e) {
        // check the head of LinkedList
        // if it's empty fill it.
        if (head == null) {
            head = new Node<>(e);
            tail = head;
        } else {
            // adding a pointer to the next node
            Node<E> newTail = new Node<>(e);
            tail.setNext(newTail);
            this.tail = newTail;
        }
    }

    public void addFirst(E e) {
        Node<E> newHead = new Node<>(e);
        newHead.setNext(this.head);
        this.head = newHead;
        if (this.head.getNext() == null) {
            this.tail = newHead;
        }
    }

    public void addLast(E e) {
        if (tail == null) {
            addFirst(e);
            return;
        }
        Node<E> newTail = new Node<>(e);
        this.tail.setNext(newTail);
        this.tail = newTail;
    }

    public void removeFirst() {
        if (head != null) {
            if (head.getNext() == null)
                this.tail = null;
            this.head = head.getNext();
        }
    }

    public void removeLast() {
        int size = size();
        if (size <= 1) {
            this.head = null;
            this.tail = null;
        } else {
            Node<E> current = head;
            for (int i = 1; i < size - 1; i++) {
                current = current.getNext();
            }
            this.tail = current;
            current.setNext(null);
        }
    }

    public boolean removeFirstMatch(E e) {
        int index = indexOf(e);
        if (index == -1)
            return false;

        if (0 == index)
            removeFirst();
        else {
            int prevIndex = index - 1;

            Node<E> prev = head;
            for (int i = 0; i < prevIndex; i++) {
                prev = prev.getNext();
            }
            Node<E> next = prev.getNext().getNext();
            prev.setNext(next);
            if (prev.getNext() == null)
                tail = prev;
        }
        return true;
    }

    public int indexOf(E e) {
        int index = 0;
        Node<E> current = head;
        while (current != null) {
            if (current.getData().equals(e))
                return index;

            current = current.getNext();
            index++;
        }
        return -1;
    }

    public boolean contains(E e) {
        return -1 < indexOf(e);
    }

    public E get(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Index cannot be negative");

        Node<E> current = head;
        int i = 0;
        while (current != null) {
            if (i == index)
                return current.getData();

            current = current.getNext();
            i++;
        }
        throw new IndexOutOfBoundsException("Input Index is bigger than the LinkedList size: " + index);
    }

    public E getFirst() {
        if (head == null) throw new IllegalStateException("List is empty");
        return head.getData();
    }

    public E getLast() {
        if (tail == null) throw new IllegalStateException("List is empty");
        return tail.getData();
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        this.head = null;
        this.tail = null;
    }

    public int size() {
        int size = 0;
        Node<E> current = head;
        while (current != null) {
            size++;
            current = current.getNext();
        }
        return size;
    }

    public String print() {
        if (head == null)
            return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.getData()).append(",");
            current = current.getNext();
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append("]");
        return sb.toString();
    }

    public LinkedList<E> reverse() {
        LinkedList<E> reversed = new LinkedList<>();
        Node<E> current = head;
        while (current != null) {
            reversed.addFirst(current.getData());
            current = current.getNext();
        }
        return reversed;
    }

    public void insertAt(int index, E data) {
        if (index == 0)
            addFirst(data);
        else {
            Node<E> prev = getNode(index - 1);
            Node<E> next = prev.getNext();
            Node<E> middle = new Node<>(data);
            prev.setNext(middle);
            middle.setNext(next);

            if (next == null)
                tail = middle;
        }
    }

    private Node<E> getNode(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Index cannot be negative");

        Node<E> current = head;
        int i = 0;
        while (current != null) {
            if (i == index)
                return current;

            current = current.getNext();
            i++;
        }
        throw new IndexOutOfBoundsException("Input Index is bigger than the LinkedList size: " + index);
    }

    @Override
    public String toString() {
        return print();
    }
}
