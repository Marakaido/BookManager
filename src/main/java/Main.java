import commands.CommandHandler;
import data.PersistenceUnit;

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
            CommandHandler commandHandler = new CommandHandler(persistenceUnit);
            Application application = new Application(commandHandler);

            application.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
