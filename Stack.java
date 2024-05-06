
public interface Stack<E> {
    /**
     * Check if the stack is empty
     * @return If the stack is empty, return true, otherwise return false
     */
    boolean isEmpty();

    /**
     * Pop out of stack
     * @return If the stack is not empty, pop the top element of the stack and return, otherwise return null
     */
    E pop();

    /**
     * Get the top element of the stack
     * @return Top element
     */
    E peek();

    /**
     * Push into the stack
     * @param element Element to be pushed
     */
    void push(E element);
}
