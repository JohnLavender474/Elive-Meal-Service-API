package main.api.db;

import main.api.db.tables.DatabaseTable;

/**
 * Interface for database with tables.
 */
public interface Database {

    /**
     * Gets the database table associated with the key.
     *
     * @param key the key
     * @return the database table
     */
    DatabaseTable<?> getDatabaseTable(String key);

}
