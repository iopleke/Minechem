package minechem.handler;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import minechem.reference.Compendium;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;

/**
 *
 * @author jakimfett
 */
public class IconHandler
{
    private static TMap<String, IIcon> icons = new THashMap();

    public static void addIcon(String iconName, String iconPath, IIconRegister iconRegistry)
    {
        icons.put(iconName, iconRegistry.registerIcon(iconPath));
    }

    public static IIcon getIcon(String iconName)
    {
        if (icons.containsKey(iconName))
        {
            return (IIcon) icons.get(iconName);
        }
        return (IIcon) icons.get("default");
    }

    public static void registerIcons(TextureStitchEvent.Pre paramPre)
    {
        if (paramPre.map.getTextureType() != 0)
        {
            if (paramPre.map.getTextureType() == 1)
            {
                IconHandler.addIcon("default", Compendium.Naming.id + ":default", paramPre.map);
                IconHandler.addIcon("IconInformation", Compendium.Naming.id + ":patreon", paramPre.map);
            }
        }
    }
}
