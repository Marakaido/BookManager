package ui;

import data.entities.Book;

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
     * @return Parsed book or null, if it couldn't be parsed
     */
    public static Book getBook()
    {
        String line = scanner.nextLine();
        Pattern pattern = Pattern.compile("(.+)\\s\\\"(.+)\\\"");
        Matcher matcher = pattern.matcher(line);
        if(matcher.find())
            return new Book(matcher.group(2).trim(), matcher.group(1).trim());
        else return null;
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

    private static Scanner scanner = new Scanner(System.in);
}
