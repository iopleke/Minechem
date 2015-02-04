package minechem.helper;

import cpw.mods.fml.common.FMLLog;
import minechem.Config;
import minechem.reference.Compendium;
import org.apache.logging.log4j.Level;

/**
 * Helper class for logging
 *
 * @author way2muchnoise
 */
public class LogHelper
{
    /**
     * Used for logging when debug is turned on in the config
     *
     * @param obj object to log
     * @param args parameters to the message
     */
    public static void debug(Object obj, Object... args)
    {
        if (Config.debugMode)
        {
            log(Level.INFO, obj, args);
        }
    }

    /**
     * Used for logging an exception
     *
     * @param obj       object to log
     * @param exception exception to log
     * @param level     level of the log
     * @param args parameters to the message
     */
    public static void exception(Object obj, Throwable exception, Level level, Object... args)
    {
        FMLLog.log(Compendium.Naming.id, level, exception, String.valueOf(obj), args);
    }

    /**
     * Used for logging an exception
     *
     * @param exception exception to log
     * @param level     level of the log
     */
    public static void exception(Throwable exception, Level level)
    {
        FMLLog.log(Compendium.Naming.id, level, exception, exception.toString());
    }

    /**
     * Used for logging in any case
     *
     * @param obj object to log
     * @param args parameters to the message
     */
    public static void info(Object obj, Object... args)
    {
        log(Level.INFO, obj, args);
    }

    /**
     * General logging method
     *
     * @param level Level of the log
     * @param obj   object to log
     * @param args parameters to the message
     */
    public static void log(Level level, Object obj, Object... args)
    {
        FMLLog.log(Compendium.Naming.id, level, String.valueOf(obj), args);
    }
}
