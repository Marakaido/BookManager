package ui;

import data.entities.Book;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.InputMismatchException;

/**
 * @author marakaido
 * @since 31.03.17
 */
public class UI
{
    /**
     * Parses next command
     * @return Command token
     */
    public static String getCommand()
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
    public static Book getBook()
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
    public static String getBookName()
    {
        return parse(scanner.nextLine(), bookNameRegex);
    }

    /**
     * Parses next book name
     * @throws IllegalStateException When the name couldn't be parsed
     * @return Parsed name
     */
    public static String getBookAuthorRegex()
    {
        return parse(scanner.nextLine(), bookAuthorRegex);
    }

    /**
     * Parses next id
     * @throws InputMismatchException if next token couldn't be parsed as long
     * @return Id of a book
     */
    public static long getId()
    {
        return scanner.nextLong();
    }

    /**
     * Lets user pick one of the books in the lists
     * @param books Books from which user has to choose one
     * @return Book that was chosen by the user
     */
    public static Book specify(List<Book> books)
    {
        UI.print("Several books match your query: ");
        int i = 1;
        for(Book book : books) UI.print(i++ + ") " + book);
        UI.print("$id: ");
        long id = UI.getId();
        return books.get((int)id-1);
    }

    /**
     * Prints standard message
     * @param message Message that is to be printed
     */
    public static void print(String message)
    {
        System.out.println(message);
    }

    /**
     * Prints warning message
     * @param warning Warning that is to be printed
     */
    public static void printWarning(String warning)
    {
        System.out.println("[?] " + warning);
    }

    /**
     * Prints error message
     * @param error Error that is to be printed
     */
    public static void printError(String error)
    {
        System.out.println("[!] " + error);
    }

    private static String parse(String text, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if(matcher.find()) return matcher.group(1).trim();
        else throw new IllegalStateException();
    }

    private static Scanner scanner = new Scanner(System.in);
    private static final String bookNameRegex = "\\\"(.+)\\\"";
    private static final String bookAuthorRegex = "(.+)";
    private static final String bookRegex = bookAuthorRegex + "\\s" + bookNameRegex;
}
