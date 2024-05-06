import java.util.Iterator;

/**
 * Single list
 * @param <E>
 */
public class LinkedList<E> implements Stack<E>, Iterable<E> {
    /**
     * The head node of the linked list, null means that the linked list is currently empty, otherwise it will always point to the first element in the linked list
     */
    protected Node<E> head;
    /**
     * The end node of the linked list always points to the last node in the linked list (convenient to add data to the linked list)
     */
    protected Node<E> tail;
    /**
     * The number of elements in the linked list
     */
    protected int size;

    /**
     * No-parameter constructor
     */
    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Add data to the end of the linked list
     * @param data Data
     */
    public void add(E data) {
        Node<E> node = new Node<>(data);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;

        size++;
    }

    /**
     * Get the number of elements in the linked list
     * @return Number of elements
     */
    public int size() {
        return size;
    }

    /**
     * If the linked list is empty
     * @return If the linked list is empty, return true, otherwise return false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Pop operation, if the stack is not empty, return the top element of the stack and pop the top element of the stack
     * @return If the stack is not empty, return the top element of the stack. Otherwise, return null
     */
    @Override
    public E pop() {
        E result = null;
        if (head != null) {
            result = head.data;;
            head = head.next;
            size--;
        }
        return result;
    }

    /**
     * Get the top element of the stack, but do not pop the stack
     * @return If the stack is not empty, return the top element of the stack
     */
    @Override
    public E peek() {
        if (head != null) {
            return head.data;
        } else {
            return null;
        }
    }

    /**
     * Push operation
     * @param element Element to be pushed
     */
    @Override
    public void push(E element) {
        Node<E> node = new Node<>(element);
        node.next = head;
        head = node;
        size++;
    }

    /**
     * Get the element at the specified index
     * @param index Specified index
     * @return Element
     * @throws IndexOutOfBoundsException If the index is not in the legal range, throw an exception
     */
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.data;
    }

    /**
     * Find the index of the first occurrence of the specified element in the linked list
     * @param data The element to find
     * @return Return the index of the first occurrence of the element in the linked list if the search succeeds, otherwise return -1
     */
    public int find(E data) {
        Node<E> node = head;
        int index = -1;

        while (node != null) {
            index++;
            if (node.data.equals(data)) {
                return index;
            }
            node = node.next;
        }
        return -1;
    }

    /**
     * Modify the value in the specified index node
     * @param index Index
     * @param data The data of the modified element
     * @throws IndexOutOfBoundsException If the specified index is not in the legal range, an exception is thrown
     */
    public void set(int index, E data) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        node.data = data;
    }

    /**
     * Delete the specified element (only delete the node found for the first time)
     * @param data Element
     * @return Return true if the deletion is successful, otherwise false
     */
    public boolean remove(E data) {
        Node<E> pre = null;
        Node<E> cur = head;

        while (cur != null) {

            if (cur.data.equals(data)) {

                if (pre == null) {
                    head = cur.next;
                } else {
                    pre.next = cur.next;
                }

                if (cur == tail) {
                    tail = pre;
                }

                size--;
                return true;
            }

            pre = cur;
            cur = cur.next;
        }
        return false;
    }

    /**
     * Delete the node of the specified index
     * @param index The index to delete
     */
    public void remove(int index)  {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> pre = null;
        Node<E> cur = head;

        for (int i = 0; i < size; i++) {
            if (i == index) {
                if (pre == null) {
                    head = cur.next;
                } else {
                    pre.next = cur.next;
                }

                if (cur == tail) {
                    tail = pre;
                }
                size--;
                return;
            }
            pre = cur;
            cur = cur.next;
        }
    }

    /**
     * Get linked list iterator
     * @return Iterator
     */
    public Iterator<E> iterator() {
        return new LinedListIterator<>(head);
    }

    /**
     * Custom linked list iterator class
     * @param <E>
     */
    public class LinedListIterator<E> implements Iterator<E> {
        private Node<E> node;

        public LinedListIterator(Node<E> node) {
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public E next() {
            E data = node.data;
            node = node.next;
            return data;
        }
    }

    /**
     * Linked list node
     * @param <E>
     */
    private class Node<E> {
        public E data;
        public Node<E> next;

        public Node(E data) {
            this.data = data;
            next = null;
        }

        public Node() {
            this.data = null;
            next = null;
        }
    }
}
