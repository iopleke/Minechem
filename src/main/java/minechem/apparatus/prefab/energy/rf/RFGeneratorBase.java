package minechem.apparatus.prefab.energy.rf;

import cofh.api.energy.IEnergyProvider;
import cpw.mods.fml.common.Optional;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHAPI|energy")
public abstract class RFGeneratorBase extends RFBase implements IEnergyProvider
{
    public RFGeneratorBase(int inventorySize, int capacity, int maxInput, int maxOutput)
    {
        super(inventorySize, capacity, maxInput, maxOutput);
    }

    public RFGeneratorBase(int inventorySize, int capacity, int maxTransfer)
    {
        super(inventorySize, capacity, maxTransfer);
    }

    public RFGeneratorBase(int inventorySize, int capacity)
    {
        super(inventorySize, capacity);
    }

    public RFGeneratorBase(int inventorySize)
    {
        super(inventorySize);
    }

    @Optional.Method(modid = "CoFHAPI|energy")
    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection)
    {
        return true;
    }

    @Optional.Method(modid = "CoFHAPI|energy")
    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int amount, boolean doExtract)
    {
        return this.energy.extractEnergy(amount, doExtract);
    }

    @Optional.Method(modid = "CoFHAPI|energy")
    @Override
    public int getEnergyStored(ForgeDirection forgeDirection)
    {
        return this.energy.getStored();
    }

    @Optional.Method(modid = "CoFHAPI|energy")
    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection)
    {
        return this.energy.getCapacity();
    }
}
