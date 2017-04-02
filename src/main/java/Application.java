import commands.CommandHandler;
import ui.UI;

/**
 * @author marakaido
 * @since 31.03.17
 *
 * Handles application loop
 */
public class Application
{
    /**
     * User interface of the application
     */
    public final UI ui;

    public Application(CommandHandler commandHandler, UI ui)
    {
        this.commandHandler = commandHandler;
        this.ui = ui;
    }

    /**
     * Starts application loop
     */
    public void start()
    {
        ui.print("Welcome to Book Manager!");
        String command = ui.getCommand();
        while(!command.equals("exit"))
        {
            commandHandler.resolve(command);
            command = ui.getCommand();
        }
    }

    private CommandHandler commandHandler;
}
