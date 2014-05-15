package pixlepix.minechem.common.tileentity;

import java.util.EnumSet;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.CompatibilityModule;
import universalelectricity.api.UniversalClass;
import universalelectricity.api.energy.EnergyStorageHandler;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.energy.IEnergyInterface;
import universalelectricity.api.vector.Vector3;

@UniversalClass
public abstract class MinechemTileEntity extends MinechemTileEntityRedstone implements IInventory, IEnergyInterface, IEnergyContainer
{
    protected EnergyStorageHandler energy;
    public ItemStack[] inventory;
    public float lastEnergyUsed;

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
        energy = new EnergyStorageHandler(energyCapacity, transferRate);
    }

    public MinechemTileEntity(long capacity, long maxReceive, long maxExtract)
    {
        energy = new EnergyStorageHandler(capacity, maxReceive, maxExtract);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tagCompound);
    }

    @Override
    public boolean canConnect(ForgeDirection direction, Object obj)
    {
        return true;
    }

    protected void consume()
    {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            if (this.energy.getEnergy() < this.energy.getEnergyCapacity())
            {
                TileEntity tileEntity = new Vector3(this).translate(direction).getTileEntity(this.worldObj);

                if (tileEntity != null)
                {
                    long maxRecieve = this.energy.getMaxReceive();
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
    public long getEnergy(ForgeDirection from)
    {
        return this.energy.getEnergy();
    }

    @Override
    public long getEnergyCapacity(ForgeDirection from)
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
        Double result = Long.valueOf(this.getEnergy(ForgeDirection.UNKNOWN)).doubleValue() * Long.valueOf(prgPixels).doubleValue() / Long.valueOf(this.getEnergyCapacity(ForgeDirection.UNKNOWN)).doubleValue();
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

    protected void produce()
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
        this.lastEnergyUsed = nbt.getFloat("lastEnergyUsed");
    }

    public void setEnergyUsage(float energyUsage)
    {
        this.lastEnergyUsed = energyUsage;
    }

    public float getRequest()
    {
        return this.lastEnergyUsed;
    }

    @Override
    public void setEnergy(ForgeDirection from, long energy)
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
        nbt.setFloat("lastEnergyUsed", this.lastEnergyUsed);
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
    public void openChest()
    {
    }

    @Override
    public void closeChest()
    {
    }

    abstract void sendUpdatePacket();

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
        this.readFromNBT(pkt.data);
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
