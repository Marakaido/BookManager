package ui;

import data.entities.Book;

import java.util.List;
import java.util.InputMismatchException;

/**
 * @author marakaido
 * @since 31.03.17
 *
 * Handles user interaction
 */
public interface UI
{
    /**
     * Gets next command
     * @return Command token
     */
    String getCommand();

    /**
     * Gets next book
     * @throws IllegalStateException If book had wrong format
     * @return Parsed book
     */
    Book getBook();

    /**
     * Gets next book name
     * @throws IllegalStateException If name had wrong format
     * @return Parsed name
     */
    String getBookName();

    /**
     * Gets next book author
     * @throws IllegalStateException If author had wrong format
     * @return Parsed author
     */
    String getBookAuthorRegex();

    /**
     * Gets next id
     * @throws InputMismatchException if input wasn't a digit
     * @return Id of a book
     */
    long getId();
    /**
     * Lets user pick one of the books in the lists
     * @param books Books from which user has to choose one
     * @return Book that was chosen by the user
     */
    Book specify(List<Book> books);

    /**
     * Shows standard message
     * @param message Message that is to be shown
     */
    void print(String message);

    /**
     * Shows warning message
     * @param warning Warning that is to be shown
     */
    void printWarning(String warning);

    /**
     * Shows error message
     * @param error Error that is to be shown
     */
    void printError(String error);
}
