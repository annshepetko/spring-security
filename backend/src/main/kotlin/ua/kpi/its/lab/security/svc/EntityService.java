package ua.kpi.its.lab.security.svc;

import java.util.List;

/**
 * Interface for generic entity service operations.
 */
public interface EntityService {

    /**
     * Finds an entity by its id.
     *
     * @param id the id of the entity to find
     * @return the found entity, or null if no entity with the given id exists
     */
    <T> T findById(Integer id);

    /**
     * Retrieves all entities.
     *
     * @return a list containing all entities
     */
    <T> List<T> findAll();

    /**
     * Creates a new entity.
     *
     * @param entity the entity to create
     */
    <T> void create(T entity);

    /**
     * Updates an existing entity.
     *
     * @param entity the entity to update
     */
    <T> void update(T entity);

    /**
     * Deletes an entity by its id.
     *
     * @param id the id of the entity to delete
     */
    void delete(Integer id);
}
