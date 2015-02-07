package minechem.helper;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.StatCollector;

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
            String localString = StatCollector.translateToLocal(key);
            if (capitalize)
            {
                localString = localString.toUpperCase();
            }
            return localString;
        }
        return key;
    }
}
