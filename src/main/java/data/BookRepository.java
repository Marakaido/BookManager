package data;

import data.entities.Book;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

/**
 * @author marakaido
 * @since 31.03.17
 *
 * Repository for book entities
 */
public class BookRepository implements CRUDRepository<Book, Long>
{
    public BookRepository(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    /**
     * @see CRUDRepository
     */
    @Override
    public Book save(Book entity)
    {
        if(exists(entity)) throw new EntityExistsException();

        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    /**
     * @see CRUDRepository
     */
    @Override
    public Book update(Long id, Book newValue)
    {
        Book book = entityManager.find(Book.class, id);
        if(book == null) throw new EntityNotFoundException();

        entityManager.getTransaction().begin();
        book.setName(newValue.getName());
        book.setAuthor(newValue.getAuthor());
        entityManager.getTransaction().commit();

        return book;
    }

    /**
     * @see CRUDRepository
     */
    @Override
    public void delete(Book entity)
    {
        if(!exists(entity)) throw new EntityNotFoundException();

        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    /**
     * @see CRUDRepository
     */
    @Override
    public Book get(Long key)
    {
        return entityManager.find(Book.class, key);
    }

    /**
     * @see CRUDRepository
     */
    @Override
    public boolean exists(Book entity)
    {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT book FROM Book book WHERE book.name = '" + entity.getName() + "' AND book.author = '" + entity.getAuthor() + "'",
                Book.class);
        return query.getResultList().size() == 1;
    }

    /**
     * @see CRUDRepository
     */
    @Override
    public Iterable<Book> getAll()
    {
        TypedQuery<Book> query = entityManager.createQuery("SELECT book FROM Book book", Book.class);
        return query.getResultList();
    }

    private EntityManager entityManager;
}
