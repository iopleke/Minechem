package minechem.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import minechem.Minechem;
import minechem.fluid.MinechemFluid;
import minechem.fluid.MinechemFluidBlock;
import minechem.fluid.reaction.ChemicalFluidReactionHandler;
import minechem.tick.ScheduledTickHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommonProxy
{
	public static int RENDER_ID;

	public void registerRenderers()
	{

	}

	public void registerTickHandlers()
	{
		FMLCommonHandler.instance().bus().register(new ScheduledTickHandler());
		FMLCommonHandler.instance().bus().register(new ChemicalFluidReactionHandler());
		FMLCommonHandler.instance().bus().register(Minechem.INSTANCE);
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

    public EntityPlayer getPlayer(MessageContext context)
    {
        return context.getServerHandler().playerEntity;
    }
    
    public void onAddFluid(MinechemFluid fluid,MinechemFluidBlock block)
    {
    	
    }

}
