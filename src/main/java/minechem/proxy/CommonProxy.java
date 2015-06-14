package minechem.proxy;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import minechem.handler.EventHandler;
import net.minecraft.entity.player.EntityPlayer;
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
        return MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
    }

    public World getClientWorld()
    {
        return null;
    }

    public EntityPlayer getPlayer(MessageContext context)
    {
        return context.getServerHandler().playerEntity;
    }

    public World getWorld(MessageContext context)
    {
        return context.getServerHandler().playerEntity.worldObj;
    }

    public void registerRenderers()
    {
        // NOOP
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
        // NOOP
    }

    public String getCurrentLanguage()
    {
        return "en_US";
    }

    public void registerFonts()
    {
        // NOOP
    }
}
