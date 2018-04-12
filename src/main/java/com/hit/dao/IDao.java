package com.hit.dao;

/**
 *
 * @param <ID>
 * @param <T>
 */
public interface IDao<ID extends java.io.Serializable, T> {
    /**
     * Deletes a given entity.
     * @param entity - given entity.
     * @throws java.lang.IllegalArgumentException - in case given entity is null.
     */
    void delete(T entity) throws java.lang.IllegalArgumentException;

    /**
     * Retrieves an entity by its id.
     * @param id - can't be null.
     * @return - the entity with the given id or null if not found.
     * @throws java.lang.IllegalArgumentException - if the given id is null.
     */
    T find(ID id) throws java.lang.IllegalArgumentException;

    /**
     * Saves a given entity.
     * @param entity - given entity.
     */
    void save(T entity);
}
