package basiccomponents.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import basiccomponents.client.gui.GuiBatteryBox;
import basiccomponents.client.gui.GuiCoalGenerator;
import basiccomponents.client.gui.GuiElectricFurnace;
import basiccomponents.common.container.ContainerBatteryBox;
import basiccomponents.common.container.ContainerCoalGenerator;
import basiccomponents.common.container.ContainerElectricFurnace;
import basiccomponents.common.tileentity.TileEntityBatteryBox;
import basiccomponents.common.tileentity.TileEntityCoalGenerator;
import basiccomponents.common.tileentity.TileEntityElectricFurnace;
import cpw.mods.fml.common.network.IGuiHandler;

public class BCGuiHandler implements IGuiHandler
{
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity != null)
		{
			if (tileEntity instanceof TileEntityBatteryBox)
			{
				return new GuiBatteryBox(player.inventory, ((TileEntityBatteryBox) tileEntity));
			}
			else if (tileEntity instanceof TileEntityCoalGenerator)
			{
				return new GuiCoalGenerator(player.inventory, ((TileEntityCoalGenerator) tileEntity));
			}
			else if (tileEntity instanceof TileEntityElectricFurnace)
			{
				return new GuiElectricFurnace(player.inventory, ((TileEntityElectricFurnace) tileEntity));
			}
		}

		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity != null)
		{
			if (tileEntity instanceof TileEntityBatteryBox)
			{
				return new ContainerBatteryBox(player.inventory, ((TileEntityBatteryBox) tileEntity));
			}
			else if (tileEntity instanceof TileEntityCoalGenerator)
			{
				return new ContainerCoalGenerator(player.inventory, ((TileEntityCoalGenerator) tileEntity));
			}
			else if (tileEntity instanceof TileEntityElectricFurnace)
			{
				return new ContainerElectricFurnace(player.inventory, ((TileEntityElectricFurnace) tileEntity));
			}
		}

		return null;
	}
}
