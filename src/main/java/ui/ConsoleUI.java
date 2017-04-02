package ui;

import data.entities.Book;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author marakaido
 * @since 02.04.17
 *
 * Handles user interaction through console
 */
public class ConsoleUI implements UI
{
    /**
     * Parses next command
     * @return Command token
     */
    @Override
    public String getCommand()
    {
        print("$ ");
        scanner = new Scanner(System.in);
        return scanner.next();
    }

    /**
     * Parses next book
     * @throws IllegalStateException When the book couldn't be parsed
     * @return Parsed book
     */
    @Override 
    public Book getBook()
    {
        String line = scanner.nextLine();
        if(line.equals("") && scanner.hasNext()) line = scanner.nextLine();
        Pattern pattern = Pattern.compile(bookRegex);
        Matcher matcher = pattern.matcher(line);
        if(matcher.find())
            return new Book(matcher.group(2).trim(), matcher.group(1).trim());
        else throw new IllegalStateException();
    }

    /**
     * Parses next book name
     * @throws IllegalStateException When the name couldn't be parsed
     * @return Parsed name
     */
    @Override 
    public String getBookName()
    {
        return parse(scanner.nextLine(), bookNameRegex);
    }

    /**
     * Parses next book name
     * @throws IllegalStateException When the name couldn't be parsed
     * @return Parsed name
     */
    @Override 
    public String getBookAuthorRegex()
    {
        return parse(scanner.nextLine(), bookAuthorRegex);
    }

    /**
     * Parses next id
     * @throws InputMismatchException if next token couldn't be parsed as long
     * @return Id of a book
     */
    @Override 
    public long getId()
    {
        return scanner.nextLong();
    }

    /**
     * Lets user pick one of the books in the lists
     * @param books Books from which user has to choose one
     * @return Book that was chosen by the user
     */
    @Override 
    public Book specify(List<Book> books)
    {
        print("Several books match your query: ");
        int i = 1;
        for(Book book : books) print(i++ + ") " + book);
        print("$id: ");
        long id = getId();
        return books.get((int)id-1);
    }

    /**
     * Prints standard message
     * @param message Message that is to be printed
     */
    @Override 
    public void print(String message)
    {
        System.out.println(message);
    }

    /**
     * Prints warning message
     * @param warning Warning that is to be printed
     */
    @Override 
    public void printWarning(String warning)
    {
        System.out.println("[?] " + warning);
    }

    /**
     * Prints error message
     * @param error Error that is to be printed
     */
    @Override 
    public void printError(String error)
    {
        System.out.println("[!] " + error);
    }

    private String parse(String text, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if(matcher.find()) return matcher.group(1).trim();
        else throw new IllegalStateException();
    }

    private Scanner scanner = new Scanner(System.in);
    private final String bookNameRegex = "\\\"(.+)\\\"";
    private final String bookAuthorRegex = "(.+)";
    private final String bookRegex = bookAuthorRegex + "\\s" + bookNameRegex;
}
