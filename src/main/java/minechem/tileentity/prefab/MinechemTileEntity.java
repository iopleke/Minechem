package minechem.tileentity.prefab;

import java.util.EnumSet;
import minechem.utils.Vector3;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.api.UniversalClass;
import universalelectricity.api.core.grid.INodeProvider;
import universalelectricity.api.core.grid.electric.EnergyStorage;
import universalelectricity.api.core.grid.electric.IEnergyContainer;
import universalelectricity.compatibility.Compatibility.CompatibilityModule;

@UniversalClass
public abstract class MinechemTileEntity extends MinechemTileEntityBase implements IInventory, INodeProvider, IEnergyContainer
{
    private EnergyStorage energy;
    public ItemStack[] inventory;

    public MinechemTileEntity()
    {
        this(0);
    }

    public MinechemTileEntity(long capacity)
    {
        this(capacity, capacity, capacity);
    }

    public MinechemTileEntity(long energyCapacity, long transferRate)
    {
        energy = new EnergyStorage(energyCapacity, transferRate);
    }

    public MinechemTileEntity(long capacity, long maxReceive, long maxExtract)
    {
        energy = new EnergyStorage(capacity, maxReceive, maxExtract);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, tagCompound);
    }

    public void consume()
    {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            if (this.energy.getEnergy() < this.energy.getEnergyCapacity())
            {
                TileEntity tileEntity = new Vector3(this).translate(direction).getTileEntity(this.worldObj);

                if (tileEntity != null)
                {
                    double maxRecieve = this.energy.getMaxReceive();
                    long used = CompatibilityModule.extractEnergy(tileEntity, direction.getOpposite(), this.energy.receiveEnergy(maxRecieve, false), true);
                    this.energy.receiveEnergy(used, true);
                }
            }
        }
    }

    public void consumeEnergy(long amount)
    {
        if (!this.energy.isEmpty())
        {
            this.energy.setEnergy(Math.max((this.energy.getEnergy() - amount), 0));
        }
    }

    @Override
    public double getEnergy(ForgeDirection from)
    {
        return this.energy.getEnergy();
    }

    @Override
    public double getEnergyCapacity(ForgeDirection from)
    {
        return this.energy.getEnergyCapacity();
    }

    /** The electrical input direction.
     * 
     * @return The direction that electricity is entered into the tile. Return null for no input. By default you can accept power from all sides. */
    public EnumSet<ForgeDirection> getInputDirections()
    {
        return EnumSet.allOf(ForgeDirection.class);
    }

    /** The electrical output direction.
     * 
     * @return The direction that electricity is output from the tile. Return null for no output. By default it will return an empty EnumSet. */
    public EnumSet<ForgeDirection> getOutputDirections()
    {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    public int getPowerRemainingScaled(int prgPixels)
    {
        Double result = Double.valueOf(this.getEnergy(ForgeDirection.UNKNOWN)).doubleValue() * Long.valueOf(prgPixels).doubleValue() / Long.valueOf(this.getEnergyCapacity(ForgeDirection.UNKNOWN)).doubleValue();
        return result.intValue();
    }

    public boolean isPowered()
    {
        return this.energy.getEnergy() > 0;
    }

    @Override
    public long onExtractEnergy(ForgeDirection from, long extract, boolean doExtract)
    {
        return this.energy.extractEnergy(extract, doExtract);
    }

    @Override
    public void onInventoryChanged()
    {
        super.onInventoryChanged();
    }

    @Override
    public long onReceiveEnergy(ForgeDirection from, long receive, boolean doReceive)
    {
        return this.energy.receiveEnergy(receive, doReceive);
    }

    public void produce()
    {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            if (this.energy.getEnergy() > 0)
            {
                TileEntity tileEntity = new Vector3(this).translate(direction).getTileEntity(this.worldObj);

                if (tileEntity != null)
                {
                    long used = CompatibilityModule.receiveEnergy(tileEntity, direction.getOpposite(), this.energy.extractEnergy(this.energy.getEnergy(), false), true);
                    this.energy.extractEnergy(used, true);
                }
            }
        }
    }

    public void produceEnergy(long amount)
    {
        if (!this.energy.isFull())
        {
            this.energy.setEnergy(this.energy.getEnergy() + amount);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.energy.readFromNBT(nbt);
    }

	@Override
    public void setEnergy(ForgeDirection from, double energy)
    {
        this.energy.setEnergy(energy);
    }

    public void setEnergyCapacity(long energy)
    {
        this.energy.setCapacity(energy);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        // Accept energy if we are allowed to do so.
        if (this.energy.checkReceive())
        {
            this.consume();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        this.energy.writeToNBT(nbt);
    }

    @Override
    public ItemStack getStackInSlot(int var1)
    {
        return this.inventory[var1];
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (this.inventory[slot] != null)
        {
            ItemStack itemstack;
            if (this.inventory[slot].stackSize <= amount)
            {

                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.inventory[slot].splitStack(amount);
                if (this.inventory[slot].stackSize == 0)
                    this.inventory[slot] = null;
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.inventory[slot] != null)
        {
            ItemStack itemstack = this.inventory[slot];
            this.inventory[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
            itemstack.stackSize = this.getInventoryStackLimit();
        this.inventory[slot] = itemstack;
    }
}
