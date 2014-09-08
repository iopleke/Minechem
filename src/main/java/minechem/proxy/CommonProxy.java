package minechem.proxy;

import minechem.utils.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.server.MinecraftServer;

public class CommonProxy implements Reference
{
    public static int RENDER_ID;

    public void registerRenderers()
    {

    }

    public World getClientWorld()
    {
        return null;
    }

    public void registerHooks()
    {
    }

    public EntityPlayer findEntityPlayerByName(String name)
    {

        EntityPlayer player;
        player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);

        if (player != null)
        {
            return player;
        }

        return null;
    }

    public String getCurrentLanguage()
    {
        return null;
    }

    public void addName(Object obj, String s)
    {
    }

    public void addLocalization(String s1, String string)
    {
    }

    public String getItemDisplayName(ItemStack newStack)
    {
        return "";
    }
}
