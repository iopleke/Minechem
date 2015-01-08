package minechem.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
	/**
	 * Get the GUI container object for the server
	 *
	 * @param ID     GUI element ID, unused
	 * @param player Player entity
	 * @param world  World object
	 * @param x      World x coordinate
	 * @param y      World y coordinate
	 * @param z      World z coordinate
	 * @return Container object for the TileEntity
	 */
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null)
		{
			// use instanceof to return the correct container object
		}
		return null;
	}

	/**
	 * Get the GUI container object for the server
	 *
	 * @param ID     GUI element ID, unused
	 * @param player Player entity
	 * @param world  World object
	 * @param x      World x coordinate
	 * @param y      World y coordinate
	 * @param z      World z coordinate
	 * @return GUI object for the TileEntity
	 */
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null)
		{
			// use instanceof to return the correct GUI object
		}
		return null;
	}
}
