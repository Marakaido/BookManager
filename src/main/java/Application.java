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
    public Application(CommandHandler commandHandler)
    {
        this.commandHandler = commandHandler;
    }

    /**
     * Starts application loop
     */
    public void start()
    {
        UI.print("Welcome to Book Manager!");
        String command = UI.getCommand();
        while(!command.equals("exit"))
        {
            commandHandler.resolve(command);
            command = UI.getCommand();
        }
    }
    private CommandHandler commandHandler;
}
