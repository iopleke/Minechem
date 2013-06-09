package basiccomponents.client;

import basiccomponents.common.CommonProxy;
import basiccomponents.common.tileentity.TileEntityCopperWire;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	@Override
	public void registerCopperWireTileEntity()
	{
		super.registerCopperWireTileEntity();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCopperWire.class, new RenderCopperWire());
	}
}
