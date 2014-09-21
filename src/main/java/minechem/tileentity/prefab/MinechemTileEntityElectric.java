package minechem.tileentity.prefab;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import cpw.mods.fml.common.Optional;
import minechem.Settings;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({
    @Optional.Interface(iface = "cofh.api.energy.IEnergyStorage", modid = "CoFHCore"),
    @Optional.Interface(iface = "cofh.api.energy.IEnergyHandler", modid = "CoFHCore")
})
public abstract class MinechemTileEntityElectric extends MinechemTileEntity implements IEnergyStorage, IEnergyHandler
{
    /**
     * Determines amount of energy we are allowed to input into the machine with a given update.
     */
    private static final int MAX_ENERGY_RECIEVED = 20;

    /**
     * Determines total amount of energy that this machine can store.
     */
    private static final int MAX_ENERGY_STORED = 10000;

    /**
     * Amount of energy stored
     */
    private int energyStored;

    public MinechemTileEntityElectric()
    {
        super();
        energyStored = 0;
    }

    @Optional.Method(modid = "CoFHCore")
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        int received = (maxReceive <= MAX_ENERGY_RECIEVED ? maxReceive : MAX_ENERGY_RECIEVED);
        received = (energyStored + received > MAX_ENERGY_STORED ? MAX_ENERGY_STORED - energyStored : received);
        if(!simulate)
        {
            energyStored += received;
        }
        return received;
    }

    @Optional.Method(modid = "CoFHCore")
    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        return 0;
    }

    @Override
    public int getEnergyStored()
    {
        return energyStored;
    }

    @Override
    public int getMaxEnergyStored()
    {
        return MAX_ENERGY_STORED;
    }

    @Optional.Method(modid = "CoFHCore")
    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return true;
    }

    @Optional.Method(modid = "CoFHCore")
    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        return receiveEnergy(maxReceive, simulate);
    }

    @Optional.Method(modid = "CoFHCore")
    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        return extractEnergy(maxExtract, simulate);
    }

    @Optional.Method(modid = "CoFHCore")
    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return getEnergyStored();
    }

    @Optional.Method(modid = "CoFHCore")
    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return getMaxEnergyStored();
    }

    public boolean useEnergy(int energy)
    {
        if (!Settings.powerUsage)
        {
            return true;
        }
        else if (this.energyStored - energy < 0)
        {
            return false;
        }
        else if (this.energyStored - energy > MAX_ENERGY_STORED)
        {
            return false;
        }
        this.energyStored -= energy;
        return true;
    }

    public int getPowerRemainingScaled(int scale)
    {
        return (this.energyStored/MAX_ENERGY_STORED) * scale;
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
