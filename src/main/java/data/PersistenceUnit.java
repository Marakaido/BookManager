package data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author marakaido
 * @since 31.03.17
 *
 * Manages all repositories and database connection
 */
public class PersistenceUnit implements AutoCloseable
{
    public PersistenceUnit(String persistenceUnitName)
    {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        this.entityManager = this.entityManagerFactory.createEntityManager();

        this.bookRepository = new BookRepository(this.entityManager);
    }

    public BookRepository getBookRepository() { return bookRepository; }

    @Override
    public void close() throws Exception
    {
        entityManager.close();
        entityManagerFactory.close();
    }

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private BookRepository bookRepository;
}
