package minechem.handler;

import java.util.Map;
import java.util.TreeMap;
import minechem.Compendium;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;

/**
 *
 *
 */
public class IconHandler
{
    private static Map<String, IIcon> icons = new TreeMap<String, IIcon>();

    public static void addIcon(String iconName, String iconPath, IIconRegister iconRegistry)
    {
        icons.put(iconName, iconRegistry.registerIcon(iconPath));
    }

    public static IIcon getIcon(String iconName)
    {
        if (icons.containsKey(iconName))
        {
            return icons.get(iconName);
        }
        return icons.get("default");
    }

    public static void registerIcons(TextureStitchEvent.Pre paramPre)
    {
        if (paramPre.map.getTextureType() != 0)
        {
            if (paramPre.map.getTextureType() == 1)
            {
                IconHandler.addIcon("default", Compendium.Naming.id + ":guitab/default", paramPre.map);
                IconHandler.addIcon("patreon", Compendium.Naming.id + ":guitab/patreon", paramPre.map);
            }
        }
    }
}
