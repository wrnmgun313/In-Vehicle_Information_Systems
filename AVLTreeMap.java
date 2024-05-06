
public class AVLTreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    private Node root;
    private int size;

    public AVLTreeMap() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V get(K key) {
        Node node = root;
        while (node != null) {
            if (node.element.getKey().equals(key)) {
                return node.element.getValue();
            } else if (key.compareTo(node.element.getKey()) > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    /**
     * Turn left to adjust balance
     * @param node the root node of the subtree to be rotated
     * @return Root node of the rotated subtree
     */
    private Node leftRotate(Node node) {
        Node subRoot = node.right;
        node.right = subRoot.left;
        subRoot.left = node;
        node.balanceFactor = 0;
        subRoot.balanceFactor = 0;
        return subRoot;
    }

    /**
     * Turn right to adjust balance
     * @param node The root node of the subtree to be rotated
     * @return Root node of the rotated subtree
     */
    private Node rightRotate(Node node) {
        Node subRoot = node.left;
        node.left = subRoot.right;
        subRoot.right = node;
        node.balanceFactor = 0;
        subRoot.balanceFactor = 0;
        return subRoot;
    }

    /**
     * First turn left, then turn right to adjust balance
     * @param leftNode The root node of the subtree that needs to be rotate left
     * @param rightNode The root node of the subtree that needs to be rotate right
     * @return Root node after rotation
     */
    private Node leftRightRotate(Node leftNode, Node rightNode) {
        Node subRoot = leftNode.right;

        // first rotate to the left
        leftNode.right = subRoot.left;
        subRoot.left = leftNode;

        // rotate to the right
        rightNode.left = subRoot.right;
        subRoot.right = rightNode;

        switch (subRoot.balanceFactor) {
            case 0:
                leftNode.balanceFactor = 0;
                rightNode.balanceFactor = 0;
                break;
            case 1:
                leftNode.balanceFactor = 0;
                rightNode.balanceFactor = -1;
                subRoot.balanceFactor = 0;
                break;
            case -1:
                leftNode.balanceFactor = 1;
                rightNode.balanceFactor = 0;
                subRoot.balanceFactor = 0;
                break;
        }

        return subRoot;
    }

    /**
     * First turn right, then left to adjust the balance
     * @param leftNode The root node of the subtree that needs to be rotate left
     * @param rightNode The root node of the subtree that needs to be rotate right
     * @return Root node after rotation
     */
    private Node rightLeftRotate(Node rightNode, Node leftNode) {
        Node subRoot = rightNode.left;

        // R
        rightNode.left = subRoot.right;
        subRoot.right = rightNode;

        // L
        leftNode.right = subRoot.left;
        subRoot.left = leftNode;

        switch (subRoot.balanceFactor) {
            case 0:
                leftNode.balanceFactor = 0;
                rightNode.balanceFactor = 0;
                break;
            case 1:
                leftNode.balanceFactor = 0;
                rightNode.balanceFactor = -1;
                subRoot.balanceFactor = 0;
                break;
            case -1:
                leftNode.balanceFactor = 1;
                rightNode.balanceFactor = 0;
                subRoot.balanceFactor = 0;
                break;
        }

        return subRoot;
    }


    @Override
    public V put(K key, V value) {
        if (root == null) {  // The root node is empty, add the element directly and exit
            root = new Node(new Entry<>(key, value));
            size++;
            return null;
        } else {
            Node node = root;
            Stack<Node> stack = new LinkedList<>();
            Node insertNode = new Node(new Entry<>(key, value));

            while (true) {
                stack.push(node);
                int compareResult = key.compareTo(node.element.getKey());

                if (compareResult > 0) {  // The key is greater than the key of the current node then should be inserted into the right subtree of the current node
                    if (node.right == null) {
                        node.right = insertNode;
                        size++;
                        break;
                    }
                    node = node.right;
                } else if (compareResult < 0) {  // The key is less than the key of the current node then should be inserted into the left subtree of the current node
                    if (node.left == null) {
                        node.left = insertNode;
                        size++;
                        break;
                    }
                    node = node.left;
                } else {  // The key is the same, replace the corresponding value of the current key
                    V result = node.element.getValue();
                    node.element.setValue(value);
                    return result;
                }
            }

            // Check whether the AVL tree is still balanced after inserting elements
            Node subNode = insertNode;

            while (!stack.isEmpty()) {
                // Take out the top node of the stack, which must be the parent node of the current subNode
                node = stack.pop();
                // Adjust the balance factor
                if (node.right == subNode) {
                    node.balanceFactor -= 1;
                } else {
                    node.balanceFactor += 1;
                }
                // Change the value of the child node to the current node
                subNode = node;

                if (Math.abs(node.balanceFactor) == 0) {
                    // If the BF value of the parent node is 0 after adjustment, it means that the inserted node did not cause the depth of the current subtree to change, so there is no need to continue to adjust
                    break;
                } else if (Math.abs(node.balanceFactor) > 1) {   // If the absolute value of the balance factor is greater than 1, it means that the current node is the root node that needs to be adjusted
                    /**
                     * Make different adjustments according to different situations, a total of 4 rotation situations
                     */
                    if (node.balanceFactor < 0 &&  node.right.balanceFactor < 0) {  // L
                        node = leftRotate(node);
                    } else if (node.balanceFactor < 0) {  // RL
                        node = rightLeftRotate(node.right, node);
                    } else if (node.balanceFactor > 0 &&  node.left.balanceFactor >= 0) {  // R
                        node = rightRotate(node);
                    } else {  // LR
                        node = leftRightRotate(node.left, node);
                    }

                    // If the stack is empty after the adjustment, it means that the root node of the adjusted subtree is the root node of the original AVL tree.
                    if (stack.isEmpty()) {
                        root = node;
                    } else {
                        Node parent = stack.pop();
                        if (parent.right == subNode) {
                            parent.right = node;
                        } else {
                            parent.left = node;
                        }
                    }
                    break;
                }
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        Stack<Node> stack = new LinkedList<>();

        Node node = root;
        while (node != null) {
            if (node.element.getKey().equals(key)) {
                size--;

                // Keep the return value
                V result = node.element.getValue();

                // Determine whether the current node has left and right subtrees
                if (node.left == null && node.right == null) {
                    // The case where the deleted node is a leaf node
                    if (stack.isEmpty()) {
                        // The stack is empty, indicating that the node to be deleted is the root node
                        root = null;
                        return result;
                    } else {
                        Node parent = stack.peek();
                        if (parent.left == node) {
                            parent.left = null;
                        } else {
                            parent.right = null;
                        }
                    }
                } else if (node.left != null && node.right == null) {
                    // The case where the deleted node only has the left subtree
                    if (stack.isEmpty()) {
                        // The stack is empty, indicating that the node to be deleted is the root node
                        root = root.left;
                        return result;
                    } else {
                        Node parent = stack.peek();
                        if (parent.left == node) {
                            parent.left = node.left;
                        } else {
                            parent.right = node.left;
                        }
                    }
                } else if (node.left == null && node.right != null) {
                    // The case where the deleted node only has the right subtree
                    if (stack.isEmpty()) {
                        // The stack is empty, indicating that the node to be deleted is the root node
                        root = node.right;
                        return result;
                    } else {
                        Node parent = stack.peek();
                        if (parent.left == node) {
                            parent.left = node.right;
                        } else {
                            parent.right = node.right;
                        }
                    }
                } else {
                    // The deleted node has both a left subtree and a right subtree. First find the maximum value in the left subtree
                    Node removeNode = node;
                    stack.push(node);
                    node = node.left;

                    while (node.right != null) {
                        stack.push(node);
                        node = node.right;
                    }

                    // Replacement value
                    removeNode.element.setKey(node.element.getKey());
                    removeNode.element.setValue(node.element.getValue());
                    Node parent = stack.peek();

                    if (parent == removeNode) {
                        if (parent.right == node) {
                            parent.right = null;
                        } else {
                            parent.left = null;
                        }
                    } else {
                        parent.right = node.left;
                    }
                }

                Node subNode = node;

                // Adjust the balance factor
                while (!stack.isEmpty()) {
                    // Take out the top node of the stack, which must be the parent node of the current subNode
                    node = stack.pop();

                    // Adjust the balance factor
                    if (node.right == subNode) {
                        node.balanceFactor -= 1;
                    } else {
                        node.balanceFactor += 1;
                    }

                    // Change the value of the child node to the current node
                    subNode = node;

                    if (Math.abs(node.balanceFactor) == 0) {
                        // If the BF value of the parent node is 0 after adjustment, it means that the inserted node did not cause the depth of the current subtree to change, so there is no need to continue to adjust
                        break;
                    } else if (Math.abs(node.balanceFactor) > 1) {   // If the absolute value of the balance factor is greater than 1, it means that the current node is the root node that needs to be adjusted
                        /**
                         * Make different adjustments according to different situations, a total of 4 rotation situations
                         */
                        if (node.balanceFactor < 0 && node.right.balanceFactor < 0) {  // L
                            node = leftRotate(node);
                        } else if (node.balanceFactor < 0) {  // RL
                            node = rightLeftRotate(node.right, node);
                        } else if (node.balanceFactor > 0 && node.left.balanceFactor >= 0) {  // R
                            node = rightRotate(node);
                        } else {  // LR
                            node = leftRightRotate(node.left, node);
                        }

                        // If the stack is empty after the adjustment, it means that the root node of the adjusted subtree is the root node of the original AVL tree.
                        if (stack.isEmpty()) {
                            root = node;
                        } else {
                            Node parent = stack.pop();
                            if (parent.right == subNode) {
                                parent.right = node;
                            } else {
                                parent.left = node;
                            }
                        }
                        break;
                    }
                }

                return result;
            } else if (key.compareTo(node.element.getKey()) > 0) {
                stack.push(node);
                // If the KEY to be deleted is greater than the KEY of the current node, it means that if it exists, it must be in the right subtree of the node
                node = node.right;
            } else {
                stack.push(node);
                // The the KEY to be deleted is less than the KEY of the current node, it means that if it exists, it must be in the left subtree of the node
                node = node.left;
            }
        }
        return null;
    }

    public LinkedList<K> inorderTraversalKey() {
        LinkedList<K> result = new LinkedList<>();
        Stack<Node> stack = new LinkedList<>();

        if (root != null) {
            Node node = root;
            while(node != null || !stack.isEmpty()) {
                if(node != null) {
                    stack.push(node);
                    node = node.left;
                } else {
                    node = stack.pop();
                    result.add(node.element.getKey());
                    node = node.right;
                }
            }
        }
        return result;
    }

    public LinkedList<V> inorderTraversalValue() {
        LinkedList<V> result = new LinkedList<>();
        Stack<Node> stack = new LinkedList<>();

        if (root != null) {
            Node node = root;
            while(node != null || !stack.isEmpty()) {
                if(node != null) {
                    stack.push(node);
                    node = node.left;
                } else {
                    node = stack.pop();
                    result.add(node.element.getValue());
                    node = node.right;
                }
            }
        }
        return result;
    }

    public LinkedList<Entry<K,V>> inorderTraversalEntry() {
        LinkedList<Entry<K,V>> result = new LinkedList<>();
        Stack<Node> stack = new LinkedList<>();

        if (root != null) {
            Node node = root;
            while(node != null || !stack.isEmpty()) {
                if(node != null) {
                    stack.push(node);
                    node = node.left;
                } else {
                    node = stack.pop();
                    result.add(node.element);
                    node = node.right;
                }
            }
        }
        return result;
    }


    @Override
    public Iterable<K> keySet() {
        return inorderTraversalKey();
    }

    @Override
    public Iterable<V> values() {
        return inorderTraversalValue();
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        return inorderTraversalEntry();
    }

    private class Node {
        private Entry<K, V> element;
        private Node left;
        private Node right;
        private int balanceFactor;

        public Node() {
            element = null;
            left = null;
            right = null;
            balanceFactor = 0;
        }

        public Node(Entry<K, V> element) {
            this.element = element;
            left = null;
            right = null;
            balanceFactor = 0;
        }
    }
}
