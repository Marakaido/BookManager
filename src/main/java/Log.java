import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author marakaido
 * @since 31.03.17
 *
 * Handles logging configuration
 */
public class Log
{
    /**
     * Performs standard configuration
     */
    public static void configure()
    {
        for(String loggerName : Log.standardLoggerNames)
        {
            Log.map(loggerName, loggerName + ".log");
        }
    }

    /**
     * Maps logger output to file
     * @param loggerName name of the logger to be configured
     * @param fileName name of the output file
     */
    public static void map(String loggerName, String fileName)
    {
        FileHandler fh;
        Logger logger = java.util.logging.Logger.getLogger(loggerName);
        try
        {
            logger.setUseParentHandlers(false);

            fh = new FileHandler(fileName);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    //Defines logger names to be created by standard configuration
    private static List<String> standardLoggerNames = new ArrayList<String>(Arrays.asList("org.hibernate"));
}
