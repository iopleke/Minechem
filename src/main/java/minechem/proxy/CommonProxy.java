package minechem.proxy;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import minechem.handler.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
    public static int RENDER_ID;
    public static int ISBRH_ID;

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

    public World getClientWorld()
    {
        return null;
    }

    public String getItemDisplayName(ItemStack newStack)
    {
        return "";
    }

    public EntityPlayer getPlayer(MessageContext context)
    {
        return context.getServerHandler().playerEntity;
    }

    public void registerRenderers()
    {

    }

    public void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public String getCurrentSaveDir()
    {
        return DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath();
    }

    public void registerResourcesListener()
    {

    }

    public void registerJournalPages()
    {

    }
    
    public String getCurrentLanguage()
    {
        return null;
    }
}
