package commands;

import data.BookRepository;
import data.PersistenceUnit;
import data.entities.Book;
import ui.UI;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author marakaido
 * @since 02.04.17
 */
public class StandardHandlerFactory implements HandlerFactory
{
    public StandardHandlerFactory(PersistenceUnit persistenceUnit, UI ui)
    {
        this.bookRepository = persistenceUnit.getBookRepository();
        this.ui = ui;
    }

    @Override
    public List<Handler> getHandlers()
    {
        return Arrays.asList(this.handlers);
    }

    private BookRepository getBookRepository() {return bookRepository;}
    private BookRepository bookRepository;

    //All command handlers.
    //If new command handler is needed, insert it here. Order is important.
    //Command handler must return false if it wasn't able to handle request, true otherwise
    private Handler[] handlers = new Handler[] {
            (addCommand) -> {
                if(!addCommand.equals("add")) return false;

                try
                {
                    Book book = getUI().getBook();
                    getBookRepository().save(book);
                    getUI().print("Book " + book + " was successfully added");
                }
                catch(EntityExistsException e) { getUI().printError("Failed to add book: book already exists"); }
                catch(IllegalStateException e) { getUI().printError("Wrong book format"); }

                return true;
            },

            (removeCommand) -> {
                if(!removeCommand.equals("remove")) return false;

                Book book = null;
                try
                {
                    String name = getUI().getBookName();
                    List<Book> books = getBookRepository().getBooksByName(name);
                    if(books.size() == 0) throw new EntityNotFoundException();
                    else if(books.size() == 1) book = books.get(0);
                    else book = getUI().specify(books);
                    getBookRepository().delete(book);
                    getUI().print("Book" + book + " was successfully removed");
                }
                catch (EntityNotFoundException e) { getUI().printError("Book " + book + " doesn't exist"); }
                catch(IllegalStateException e) { getUI().printError("Wrong book format"); }
                catch(InputMismatchException e) { getUI().printError("Wrong id");}

                return true;
            },

            (updateCommand) -> {
                if(!updateCommand.equals("update")) return false;

                Book book = null;
                try
                {
                    String name = getUI().getBookName();
                    List<Book> books = getBookRepository().getBooksByName(name);
                    if(books.size() == 0) throw new EntityNotFoundException();
                    else if(books.size() == 1) book = books.get(0);
                    else book = getUI().specify(books);

                    getUI().print("$new data: ");
                    Book editedBook = getUI().getBook();
                    editedBook = getBookRepository().update(book, editedBook);
                    getUI().print("Book " + editedBook + " was successfully edited");
                }
                catch (EntityNotFoundException e) { getUI().printError("Book " + book + " doesn't exist"); }
                catch(IllegalStateException e) { getUI().printError("Wrong book format"); }
                catch(InputMismatchException e) { getUI().printError("Wrong id");}
                catch(EntityExistsException e) { getUI().printError("Couldn't complete operation, book with such value already exists");}

                return true;
            },

            (listCommand) -> {
                if(!listCommand.equals("list")) return false;

                List<Book> books = new LinkedList<>();
                for(Book book : getBookRepository().getAll()) books.add(book);

                books.sort((a, b) -> a.getName().compareTo(b.getName()));
                for(Book book : books) getUI().print("\t" + book);

                return true;
            },

            (unknownCommand) -> {
                getUI().printWarning("Unrecognised command, please check your spelling or type 'help' to see the list of available commands");
                return true;
            }
    };

    private UI getUI(){return ui;}
    private final UI ui;
}
