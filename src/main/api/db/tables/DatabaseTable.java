package main.api.db.tables;

/**
 * Interface representing a single database table with an object value and an id key.
 */
public interface DatabaseTable<V> {

    /**
     * Adds a value mapped to a key.
     *
     * @param key the key
     * @param val the value
     */
    void add(String key, V val);

    /**
     * Finds a value based on the key and deletes the key-value entry.
     *
     * @param key the key
     */
    void remove(String key);

    /**
     * Gets the value associated with the key.
     *
     * @param key    the key
     * @return the value associated with the key
     */
    V get(String key);

}
