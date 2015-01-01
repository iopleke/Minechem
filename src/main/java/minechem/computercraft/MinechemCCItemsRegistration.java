package minechem.computercraft;

import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraftforge.common.MinecraftForge;

public class MinechemCCItemsRegistration
{
    public static ChemicalTurtleUpgrade chemicalUpgrade = null;

    public static void init()
    {
        ComputerCraftAPI.registerTurtleUpgrade(chemicalUpgrade = new ChemicalTurtleUpgrade(342));
        MinecraftForge.EVENT_BUS.register(chemicalUpgrade);

    }
}
