package commands;

import java.util.List;

/**
 * @author marakaido
 * @since 02.04.17
 */
public interface HandlerFactory
{
    List<Handler> getHandlers();
}
