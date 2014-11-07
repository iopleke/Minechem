package minechem.computercraft;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import dan200.computercraft.api.ComputerCraftAPI;

public class MinechemCCItemsRegistration {
	public static ChemicalTurtleUpgrade chemicalUpgrade = null;
	
	public static void init()
	{
		ComputerCraftAPI.registerTurtleUpgrade(chemicalUpgrade=new ChemicalTurtleUpgrade(342));
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) MinecraftForge.EVENT_BUS.register(chemicalUpgrade);
		GameRegistry.registerTileEntity(ChemicalTurtlePeripheral.class, "chemicalTurtlePeripheral");
	}
}
