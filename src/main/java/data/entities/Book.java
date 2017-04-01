package data.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author marakaido
 * @since 31.03.17
 *
 * Represents single book
 */
@Entity
@Table (name = "books",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "author" }) })
public class Book implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "author", nullable = false)
    private String author;

    public Book(String name, String author)
    {
        this.name = name;
        this.author = author;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    @Override
    public String toString()
    {
        return  this.author + " \"" + this.name + "\"";
    }

    protected Book() {}
}
