package commands;

import java.util.List;

/**
 * @author marakaido
 * @since 01.04.17
 *
 * Handles all registered commands.
 * The process of finding the handler is of linear complexity.
 */
public class ArrayCommandHandler implements CommandHandler
{
    public ArrayCommandHandler(HandlerFactory handlerFactory)
    {
        this.handlers = handlerFactory.getHandlers();
    }

    /**
     * Tries to apply available handlers to given command
     * @param command The command which is to be handled
     */
    @Override
    public void resolve(String command)
    {
        for(Handler handler : handlers)
        {
            if(handler.handle(command)) break;
        }
    }

    List<Handler> handlers;
}
