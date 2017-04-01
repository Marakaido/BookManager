package commands;

/**
 * @author marakaido
 * @since 01.04.17
 *
 * Responsible for handling a particular command
 */
public interface Handler
{
    /**
     * Tries to handle command
     * @param command Command to be handled
     * @return true if command was handled, false otherwise
     */
    boolean handle(String command);
}
