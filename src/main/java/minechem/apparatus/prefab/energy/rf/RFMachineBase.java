package minechem.apparatus.prefab.energy.rf;

import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "cofh.api.energy.IEnergyReceiver", modid = "CoFHAPI|energy")
public class RFMachineBase extends RFBase implements IEnergyReceiver
{
    @Optional.Method(modid = "CoFHAPI|energy")
    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int amount, boolean doInsert)
    {
        return this.energy.inputEnergy(amount, doInsert);
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

    @Optional.Method(modid = "CoFHAPI|energy")
    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection)
    {
        return true;
    }
}
