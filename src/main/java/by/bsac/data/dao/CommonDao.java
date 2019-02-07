package by.bsac.data.dao;

import java.util.List;

/**
 * Common Data access object (DAO) interface.
 * All other DAO interfaces extends this.
 * Interface have a basic CRUD method.
 */
public interface CommonDao<T> {

    /**
     * Find all <T> data rows and retrieve it from table in database.
     * @return - List<T> founded objects.
     */
    List<T> findAll();

    /**
     * Create new <T> object data row in database table.
     * @param t - new T object.
     * @return - generated ID value.
     */
    int create(T t);

    /**
     * Update <T> object data row with new values in database table.
     * @param t - Updated T object.
     */
    void update(T t);

    /**
     * Delete <T> object data row from table in database.
     * @param t - T object for removing.
     */
    void delete(T t);
}
