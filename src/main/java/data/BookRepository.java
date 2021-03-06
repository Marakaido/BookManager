package data;

import data.entities.Book;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import java.util.List;

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
     * Returns book from database that equals to given book
     * @param value Book value, based on which book from database will be returned
     * @return Book with same fields as in value or null, if no such book exists
     */
    public Book getBookEqualsTo(Book value)
    {
        TypedQuery<Book> query = entityManager.createQuery("SELECT book FROM Book book WHERE book.name = '"+value.getName()+"' AND book.author = '"+value.getAuthor()+"'", Book.class);
        List<Book> result = query.getResultList();
        if(result.size() == 1) return result.get(0);
        else return null;
    }

    /**
     * Finds books by specified name
     * @param name Name of the books to be found
     * @return Books with specified name
     */
    public List<Book> getBooksByName(String name)
    {
        return entityManager
                .createQuery(
                    "SELECT book FROM Book book WHERE book.name = '"+name+"'",
                    Book.class)
                .getResultList();
    }

    /**
     * Finds books by author
     * @param author Author of the books to be found
     * @return Books with specified author
     */
    public List<Book> getBooksByAuthor(String author)
    {
        return entityManager
                .createQuery(
                        "SELECT book FROM Book book WHERE book.author = '"+author+"'",
                        Book.class)
                .getResultList();
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
    public Book update(Book oldValue, Book newValue)
    {
        Book book = getBookEqualsTo(oldValue);
        if(book == null) throw new EntityNotFoundException();
        else if(exists(newValue)) throw new EntityExistsException();

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
        Book book = getBookEqualsTo(entity);
        if(book == null) throw new EntityNotFoundException();

        entityManager.getTransaction().begin();
        entityManager.remove(book);
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
        return getBookEqualsTo(entity) != null;
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
