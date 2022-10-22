package main.db;

/**
 * Interface representing a single database table with an object value and an id key.
 *
 * @param <K> the id key for locating an object
 * @param <V> the type of object value to be stored
 */
public interface DatabaseTable<K, V> {

    /**
     * Adds a value mapped to a key.
     *
     * @param key the key
     * @param val the value
     */
    void add(K key, V val);

    /**
     * Finds a value based on the key and deletes the key-value entry.
     *
     * @param key the key
     */
    void remove(K key);

    /**
     * Gets the value associated with the key.
     *
     * @param key the key
     * @return the value associated with the key
     */
    V get(K key);

}
