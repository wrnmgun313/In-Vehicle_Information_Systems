/**
 * map interface
 * @param <K> type of key
 * @param <V> type of value
 */
public interface Map<K, V> {
    /**
     * Get the number of elements in the map
     * @return the number of elements in the map
     */
    int size();

    /**
     * Determine whether the map is empty
     * @return If there is no element in the map, return true, otherwise return false
     */
    boolean isEmpty();

    /**
     * Get the VALUE corresponding to the specified KEY
     * @param key key
     * @return If the specified key exists in the map, return the corresponding value, otherwise return null
     */
    V get(K key);

    /**
     * Add a bunch of key-value pairs to the map
     * @param key Key to be added
     * @param value The value to add
     * @return If the specified key already exists, return the value before the key, otherwise return null
     */
    V put(K key, V value);

    /**
     * Delete the specified key-vallue pair
     * @param key The Key to delete data
     * @return If the deletion is successful, return the value corresponding to the specified key. Otherwise, return null
     */
    V remove(K key);

    /**
     * Returns an iterable object of all keys in the map
     * @return An iterable object containing all keys
     */
    Iterable<K> keySet();

    /**
     * Returns an iterable object of all values in the map
     * @return Contains an iterable object that has a value
     */
    Iterable<V> values();

    /**
     * Return all key-value pairs in the map
     * @return Contains iterable objects with key-value pairs
     */
    Iterable<Entry<K, V>> entrySet();
}
