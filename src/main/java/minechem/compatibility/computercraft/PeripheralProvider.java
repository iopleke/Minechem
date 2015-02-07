package minechem.compatibility.computercraft;

import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import minechem.Compendium;
import minechem.apparatus.prefab.peripheral.TilePeripheralBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheralProvider", modid = Compendium.Naming.Mods.computerCraft)
public class PeripheralProvider implements IPeripheralProvider
{
    public static void register()
    {
        ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
    }

    @Override
    @Optional.Method(modid = Compendium.Naming.Mods.computerCraft)
    public IPeripheral getPeripheral(World world, int x, int y, int z, int side)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TilePeripheralBase)
        {
            return (IPeripheral) te;
        }
        return null;
    }
}
