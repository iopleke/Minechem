package minechem.helper;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.StatCollector;

import java.util.IllegalFormatException;

/**
 *
 *
 */
public class LocalizationHelper
{
    public static String getLocalString(String key)
    {
        return getLocalString(key, false);
    }

    public static String getLocalString(String key, boolean capitalize)
    {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            String localString;
            if (StatCollector.canTranslate(key))
            {
                localString = StatCollector.translateToLocal(key);
            } else
            {
                localString = StatCollector.translateToFallback(key);
            }
            if (capitalize)
            {
                localString = localString.toUpperCase();
            }
            return localString;
        }
        return key;
    }

    public static String getFormattedString(String key, Object... objects)
    {
        return getFormattedString(key, false, objects);
    }

    public static String getFormattedString(String key, boolean capitalize, Object... objects)
    {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            String localString;
            if (StatCollector.canTranslate(key))
            {
                localString = StatCollector.translateToLocalFormatted(key, objects);
            } else
            {
                localString = StatCollector.translateToFallback(key);
                try
                {
                    localString = String.format(localString, objects);
                } catch (IllegalFormatException illegalformatexception)
                {
                    return "Format error: " + key;
                }
            }
            if (capitalize)
            {
                localString = localString.toUpperCase();
            }
            return localString;
        }
        return key;
    }
}
