import java.util.Iterator;

public class HashMap<K, V> implements Map<K, V> {
    private Object[] bucket;
    private int size;
    private int capacity;

    /**
     * No-parameter constructor
     */
    public HashMap() {
        capacity = 500;
        size = 0;
        bucket = new Object[capacity];
    }

    /**
     * Constructor
     * @param capacity Bucket capacity
     */
    public HashMap(int capacity) {
        this.capacity = capacity;
        size = 0;
        bucket = new Object[capacity];
    }

    /**
     * Adding data
     * @param key
     * @param value
     */
    public V put(K key, V value) {
        if (size / (double)capacity >= 0.6) {
            resize();
        }

        int index = key.hashCode() % capacity;

        if (bucket[index] == null) {
            bucket[index] = new LinkedList<Entry<K, V>>();
            ((LinkedList<Entry<K, V>>)bucket[index]).add(new Entry<>(key, value));
        } else {
            for (int i = 0; i < ((LinkedList<Entry<K, V>>)bucket[index]).size(); i++) {
                Entry<K, V> entry = ((LinkedList<Entry<K, V>>)bucket[index]).get(i);
                if (entry.getKey().equals(key)) {
                    V result = entry.getValue();
                    entry.setValue(value);
                    return result;
                }
            }
            ((LinkedList<Entry<K, V>>)bucket[index]).add(new Entry<>(key, value));
        }
        size++;

        return null;
    }

    /**
     * Expand the number of buckets
     */
    private void resize() {
        Object[] tmp = new Object[capacity * 2];

        for (int i = 0; i < capacity; i++) {
            if (bucket[i] != null) {
                LinkedList<Entry<K, V>> list = (LinkedList<Entry<K, V>>)bucket[i];
                Iterator<Entry<K, V>> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Entry<K, V> entry = iterator.next();

                    int index = entry.getKey().hashCode() % (capacity * 2);
                    if (tmp[index] == null) {
                        tmp[index] = new LinkedList<Entry<K, V>>();
                    }
                    ((LinkedList<Entry<K, V>>)bucket[index]).add(entry);
                }
            }
        }

        capacity *= 2;
        bucket = tmp;
    }

    /**
     * Delete element
     * @param key The KEY of the element to be deleted
     * @return Delete the value corresponding to the key, if the specified KEY does not exist, return null;
     */
    public V remove(K key) {
        int index = key.hashCode() % capacity;

        if (bucket[index] == null) {
            return null;
        }

        for (int i = 0; i < ((LinkedList<Entry<K, V>>)bucket[index]).size(); i++) {
            Entry<K, V> node = ((LinkedList<Entry<K, V>>)bucket[index]).get(i);
            if (node.getKey().equals(key)) {
                V value = node.getValue();
                ((LinkedList<Entry<K, V>>)bucket[index]).remove(i);
                size--;
                return value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get the value corresponding to the specified key
     * @param key Specified key
     * @return If the key exists, return the corresponding value, otherwise return null
     */
    public V get(K key) {
        int index = key.hashCode() % capacity;

        if (bucket[index] == null) {
            return null;
        }

        for (int i = 0; i <((LinkedList<Entry<K, V>>)bucket[index]).size(); i++) {
            Entry<K, V> node = ((LinkedList<Entry<K, V>>)bucket[index]).get(i);
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
        }
        return null;
    }

    /**
     * Get iterator of all keys
     * @return Instantiation of an iterator containing the key
     */
    public Iterable<K> keySet() {
        LinkedList<K> list = new LinkedList<>();

        for (int i = 0; i < capacity; i++) {
            if (bucket[i] != null) {
                for (int j = 0; j < ((LinkedList<Entry<K, V>>)bucket[i]).size(); j++) {
                    Entry<K, V> entry = ((LinkedList<Entry<K, V>>)bucket[i]).get(j);
                    list.add(entry.getKey());
                }
            }
        }

        return list;
    }

    /**
     * An iterator that returns all values
     * @return Instantiation of an iterator containing value
     */
    public Iterable<V> values() {
        LinkedList<V> list = new LinkedList<>();

        for (int i = 0; i < capacity; i++) {
            if (bucket[i] != null) {
                for (int j = 0; j < ((LinkedList<Entry<K, V>>)bucket[i]).size(); j++) {
                    Entry<K, V> entry = ((LinkedList<Entry<K, V>>)bucket[i]).get(j);
                    list.add(entry.getValue());
                }
            }
        }

        return list;
    }

    /**
     * Get an iterator containing Entry
     * @return Instantiation of an iterator containing Entry
     */
    public Iterable<Entry<K, V>> entrySet() {
        LinkedList<Entry<K, V>> list = new LinkedList<>();

        for (int i = 0; i < capacity; i++) {
            if (bucket[i] != null) {
                for (int j = 0; j < ((LinkedList<Entry<K, V>>)bucket[i]).size(); j++) {
                    Entry<K, V> entry = ((LinkedList<Entry<K, V>>)bucket[i]).get(j);
                    list.add(entry);
                }
            }
        }

        return list;
    }
}
