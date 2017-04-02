package data;

import data.entities.Book;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author marakaido
 * @since 31.03.17
 */
public interface CRUDRepository<T, ID>
{
    /**
     * Persists entity
     * @param entity Entity to be persisted
     * @throws EntityExistsException if entity already exists
     * @return Saved entity
     */
    T save(T entity);

    /**
     * Updates entity to the value of newValue
     * @param oldValue Detached entity that specifies which entity to update
     * @param newValue Entity that contains new value for entity that will be edited
     * @throws EntityNotFoundException if entity with same fields as oldValue doesn't exist
     * @throws EntityExistsException if new value of the entity already exists
     * @return Updated entity
     */
    T update(T oldValue, T newValue);

    /**
     * Deletes the entity
     * @param entity Entity that will be deleted
     * @throws EntityNotFoundException if entity doesn't exist
     */
    void delete(T entity);

    /**
     * Finds entity if it exists
     * @param id Id of the element to be found
     * @return Entity with the given id or null if such entity doesn't exist
     */
    T get(ID id);

    /**
     * Checks if entity exists
     * @param entity Entity to be checked
     * @return true if entity exists, false otherwise
     */
    boolean exists(Book entity);

    /**
     * Tries to find entities if they exist
     * @param ids Ids of the entities to be found
     * @return  Entities with for which there is corresponding id.
     *          Returns k instances for n ids, where k <= n, number of failed searches = n-k
     */
    default Iterable<T> get(Iterable<ID> ids)
    {
        List<T> result = new ArrayList<T>();
        for(ID id : ids)
        {
            T entity = get(id);
            if(entity != null) result.add(entity);
        }
        return result;
    }

    /**
     * Gets all existing entities
     * @return all existing entities
     */
    Iterable<T> getAll();
}