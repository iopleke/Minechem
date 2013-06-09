package basiccomponents.common;

import basiccomponents.common.tileentity.TileEntityCopperWire;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void registerCopperWireTileEntity()
	{
		GameRegistry.registerTileEntity(TileEntityCopperWire.class, "copperWire");
	}
}
