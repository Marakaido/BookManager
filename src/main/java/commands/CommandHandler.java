package commands;

/**
 * @author marakaido
 * @since 02.04.17
 *
 * Handles all commands
 */
public interface CommandHandler
{
    void resolve(String command);
}