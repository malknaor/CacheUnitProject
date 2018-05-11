package com.hit.dao;

/**
 *
 * @param <ID>
 * @param <T>
 */
public interface IDao<ID extends java.io.Serializable, T> {
    /**
     * Saves a given entity.
     *
     * @param entity - given entity.
     */
    void save(T entity);

    /**
     * Deletes a given entity.
     *
     * @param entity - given entity.
     * @throws java.lang.IllegalArgumentException - in case given entity is null.
     */
    void delete(T entity) throws IllegalArgumentException;

    /**
     * Retrieves an entity by its id.
     *
     * @param id - must not be null.
     * @return - the entity with the given id or null if not found.
     * @throws java.lang.IllegalArgumentException - if the given id is null.
     */
    T find(ID id) throws IllegalArgumentException;
}