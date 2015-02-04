package minechem.apparatus.prefab.energy.rf;

import cofh.api.energy.IEnergyProvider;
import cpw.mods.fml.common.Optional;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHAPI|energy")
public abstract class RFHandlerBase extends RFMachineBase implements IEnergyProvider
{
    public RFHandlerBase(int inventorySize, int capacity, int maxInput, int maxOutput)
    {
        super(inventorySize, capacity, maxInput, maxOutput);
    }

    public RFHandlerBase(int inventorySize, int capacity, int maxTransfer)
    {
        super(inventorySize, capacity, maxTransfer);
    }

    public RFHandlerBase(int inventorySize, int capacity)
    {
        super(inventorySize, capacity);
    }

    public RFHandlerBase(int inventorySize)
    {
        super(inventorySize);
    }

    @Optional.Method(modid = "CoFHAPI|energy")
    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int amount, boolean doExtract)
    {
        return this.energy.extractEnergy(amount, doExtract);
    }
}
