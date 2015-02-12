package minechem.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import minechem.Minechem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class CommonProxy
{
    public static int RENDER_ID;

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

    /**
     *Get the current lang code
     * @return eg. 'en_US'
     */
    public String getCurrentLanguage()
    {
        return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
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

    public void registerTickHandlers()
    {
        FMLCommonHandler.instance().bus().register(Minechem.INSTANCE);
    }

    public String getCurrentSaveDir()
    {
        return DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath().replace(".\\", "") + "\\";
    }
}
