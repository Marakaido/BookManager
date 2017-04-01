package commands;

import data.BookRepository;
import data.PersistenceUnit;
import data.entities.Book;
import ui.UI;

import javax.persistence.EntityExistsException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author marakaido
 * @since 01.04.17
 *
 * This object handles all registered commands.
 */
public class CommandHandler
{
    public CommandHandler(PersistenceUnit persistenceUnit)
    {
        this.bookRepository = persistenceUnit.getBookRepository();
    }

    /**
     * Tries to apply available handlers to given command
     * @param command The command which is to be handled
     */
    public void resolve(String command)
    {
        for(Handler handler : handlers)
        {
            if(handler.handle(command)) break;
        }
    }

    private BookRepository getBookRepository() {return bookRepository;}
    private BookRepository bookRepository;

    //All command handlers.
    //If new command handler is needed, insert it here. Order is important.
    //Command handler must return false if it wasn't able to handle request, true otherwise
    private Handler[] handlers = new Handler[] {
            (addCommand) -> {
                if(!addCommand.equals("add")) return false;

                Book book = UI.getBook();
                if(book != null)
                {
                    try
                    {
                        getBookRepository().save(book);
                        UI.print("Book " + book + " was successfully added");
                    }
                    catch(EntityExistsException e)
                    {
                        UI.printError("Failed to add book: book already exists");
                    }
                }
                else
                {
                    UI.printError("Error: cannot parse book");
                }

                return true;
            },

            (removeCommand) -> {
                if(!removeCommand.equals("remove")) return false;

                try
                {
                    long id = UI.getId();
                    Book book = getBookRepository().get(id);
                    if(book != null)
                    {
                        getBookRepository().delete(book);
                        UI.print("Book" + book + " was successfully removed");
                    }
                }
                catch(InputMismatchException e) { UI.printError("Wrong id format"); }

                return true;
            },

            (updateCommand) -> {
                if(!updateCommand.equals("update")) return false;

                UI.print("$id: ");
                try
                {
                    long id = UI.getId();
                    Book book = getBookRepository().get(id);

                    if(book != null)
                    {
                        UI.print("$new data: ");
                        Book editedBook = UI.getBook();
                        if(editedBook != null)
                        {
                            editedBook = getBookRepository().update(id, editedBook);
                            UI.print("Book " + editedBook + " was successfully edited");
                        }
                        else UI.printError("Error: cannot parse book");
                    }
                    else UI.printError("Book with id [" + id + "] doesn't exist");
                }
                catch(InputMismatchException e) { UI.printError("Wrong id format"); }

                return true;
            },

            (listCommand) -> {
                if(!listCommand.equals("list")) return false;

                List<Book> books = new LinkedList<>();
                for(Book book : getBookRepository().getAll()) books.add(book);

                books.sort((a, b) -> a.getName().compareTo(b.getName()));
                for(Book book : books) UI.print("\t" + book);
                return true;
            },

            (unknownCommand) -> {
                UI.printWarning("Unrecognised command, please check your spelling or type 'help' to see the list of available commands");
                return true;
            }
    };
}
