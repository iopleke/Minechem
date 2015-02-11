package minechem.tileentity.prefab;

import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional;
import minechem.Settings;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "cofh.api.energy.IEnergyReceiver", modid = "CoFHAPI|energy")
public abstract class MinechemTileEntityElectric extends MinechemTileEntity implements IEnergyReceiver
{
    /**
     * Determines amount of energy we are allowed to input into the machine with a given update.
     */
    private static int MAX_ENERGY_RECEIVED = Settings.energyPacketSize;

    /**
     * Determines total amount of energy that this machine can store.
     */
    private int MAX_ENERGY_STORED;

    /**
     * Amount of energy stored
     */
    private int energyStored;
    protected int oldEnergyStored;

    public MinechemTileEntityElectric(int maxEnergy)
    {
        super();
        this.MAX_ENERGY_STORED = maxEnergy;
        energyStored = 0;
    }

    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        int received = (maxReceive <= MAX_ENERGY_RECEIVED ? maxReceive : MAX_ENERGY_RECEIVED);
        received = (energyStored + received > MAX_ENERGY_STORED ? MAX_ENERGY_STORED - energyStored : received);
        if (!simulate)
        {
            energyStored += received;
        }
        return received;
    }

    public void syncEnergyValue(int syncAt)
    {
        if (this.getEnergyStored() > syncAt)
        {
            this.useEnergy(this.getEnergyStored() - syncAt);
        } else if (this.getEnergyStored() < syncAt)
        {
            this.receiveEnergy(syncAt - this.getEnergyStored(), false);
        }
    }

    public int getEnergyStored()
    {
        return energyStored;
    }

    public int getMaxEnergyStored()
    {
        return MAX_ENERGY_STORED;
    }

    @Optional.Method(modid = "CoFHAPI|energy")
    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return true;
    }

    @Optional.Method(modid = "CoFHAPI|energy")
    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        return receiveEnergy(maxReceive, simulate);
    }

    @Optional.Method(modid = "CoFHAPI|energy")
    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return getEnergyStored();
    }

    @Optional.Method(modid = "CoFHAPI|energy")
    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return getMaxEnergyStored();
    }

    public boolean useEnergy(int energy)
    {
        if (!Settings.powerUseEnabled)
        {
            return true;
        } else if (this.energyStored - energy < 0)
        {
            return false;
        } else if (this.energyStored - energy > MAX_ENERGY_STORED)
        {
            return false;
        }
        this.energyStored -= energy;
        return true;
    }

    public int getPowerRemainingScaled(double scale)
    {
        return (int) (this.energyStored * (scale / MAX_ENERGY_STORED));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("energy", this.energyStored);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.energyStored = nbt.getInteger("energy");
    }

    public abstract int getEnergyNeeded();
}
