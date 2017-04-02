import commands.ArrayCommandHandler;
import commands.CommandHandler;
import commands.HandlerFactory;
import commands.StandardHandlerFactory;
import data.PersistenceUnit;
import ui.ConsoleUI;
import ui.UI;

/**
 * @author marakaido
 * @since 31.03.17
 *
 * Starting point of the application.
 * Configures main objects with their dependencies.
 */
public class Main
{
    public static void main(String ... args)
    {
        Log.configure();

        try(PersistenceUnit persistenceUnit = new PersistenceUnit("hibernate_persistence_unit"))
        {
            //Dependency injection
            UI userInterface = new ConsoleUI();
            HandlerFactory handlerFactory = new StandardHandlerFactory(persistenceUnit, userInterface);
            CommandHandler commandHandler = new ArrayCommandHandler(handlerFactory);
            Application application = new Application(commandHandler, userInterface);

            application.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
