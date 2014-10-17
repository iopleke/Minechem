package minechem.tileentity.prefab;

import minechem.MinechemBlocksGeneration;
import minechem.Settings;
import minechem.tileentity.multiblock.fission.FissionTileEntity;
import minechem.tileentity.multiblock.fusion.FusionTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityProxy extends MinechemTileEntityElectric implements ISidedInventory
{

	public TileEntityProxy() 
	{
		super(Settings.energyPacketSize);
	}

	public TileEntity manager;
	int managerXOffset;
	int managerYOffset;
	int managerZOffset;

@Override
public void updateEntity()
{
	if(this.manager != null)
	{
		int ammountReceived = ((MinechemTileEntityElectric) manager).receiveEnergy(this.getEnergyStored(), true);
		if(ammountReceived>0)
		{
			((MinechemTileEntityElectric) manager).receiveEnergy(ammountReceived, false);			
			this.useEnergy(ammountReceived);
		}
	}
}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);
		if (manager != null)
		{
			nbtTagCompound.setInteger("managerXOffset", manager.xCoord);
			nbtTagCompound.setInteger("managerYOffset", manager.yCoord);
			nbtTagCompound.setInteger("managerZOffset", manager.zCoord);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);
		managerXOffset = nbtTagCompound.getInteger("managerXOffset");
		managerYOffset = nbtTagCompound.getInteger("managerYOffset");
		managerZOffset = nbtTagCompound.getInteger("managerZOffset");
		if (worldObj != null)
		{
			manager = worldObj.getTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset);
		}

	}

	public void setManager(TileEntity managerTileEntity)
	{

		this.manager = managerTileEntity;
		if (managerTileEntity != null)
		{
			this.managerXOffset = managerTileEntity.xCoord - xCoord;
			this.managerYOffset = managerTileEntity.yCoord - yCoord;
			this.managerZOffset = managerTileEntity.zCoord - zCoord;
		}
	}

	public TileEntity getManager()
	{
		// Return the next block in sequence but never the TileEntityProxy.
		if (worldObj.getTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset) != null
				&& !(worldObj.getTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset) instanceof TileEntityProxy))
		{
			return worldObj.getTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset);
		}

		// Return the entire fusion generator as a whole (indicating the structure is complete).
		if (worldObj.getBlock(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset) == MinechemBlocksGeneration.fusion)
		{
			this.manager = buildManagerBlock();
			return this.manager;
		}

		return null;

	}

	private TileEntity buildManagerBlock()
	{

		if (this.worldObj.getBlockMetadata(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset) == 2)
		{
			FusionTileEntity fusion = new FusionTileEntity();
			fusion.setWorldObj(this.worldObj);
			fusion.zCoord = this.managerZOffset + zCoord;
			fusion.yCoord = this.managerYOffset + yCoord;
			fusion.xCoord = this.managerXOffset + xCoord;
			fusion.blockType = MinechemBlocksGeneration.fusion;
			worldObj.setTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset, fusion);
		}
		if (this.worldObj.getBlockMetadata(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset) == 3)
		{
			FissionTileEntity fission = new FissionTileEntity();
			fission.setWorldObj(this.worldObj);
			fission.zCoord = this.managerZOffset + zCoord;
			fission.yCoord = this.managerYOffset + yCoord;
			fission.xCoord = this.managerXOffset + xCoord;
			fission.blockType = MinechemBlocksGeneration.fusion;
			worldObj.setTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset, fission);
		}
		return worldObj.getTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset);

	}

	@Override
	public int getSizeInventory()
	{
		if (this.manager != null && this.manager != this)
		{
			return ((ISidedInventory) this.manager).getSizeInventory();
		}
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory)
		{
			return ((ISidedInventory) this.getManager()).getStackInSlot(i);
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory)
		{
			return ((ISidedInventory) this.getManager()).decrStackSize(i, j);
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory)
		{
			return ((ISidedInventory) this.getManager()).getStackInSlotOnClosing(i);
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory)
		{
			((ISidedInventory) this.getManager()).setInventorySlotContents(i, itemstack);
		}
	}

	@Override
	public String getInventoryName()
	{
		return "Multiblock Minechem proxy";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		if (this.manager != null && this.manager != this)
		{
			return ((ISidedInventory) this.getManager()).getInventoryStackLimit();
		}
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer)
	{
		return false;
	}

	@Override
	public void openInventory()
	{

	}

	@Override
	public void closeInventory()
	{

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		if (this.manager != null && this.manager != this)
		{
			return ((ISidedInventory) this.getManager()).isItemValidForSlot(i, itemstack);
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1)
	{
		if (this.manager != null && this.manager != this)
		{
			return ((ISidedInventory) this.getManager()).getAccessibleSlotsFromSide(var1);
		}
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		// Cannot insert items into reactor with automation disabled.
		if (Settings.AllowAutomation && isItemValidForSlot(slot, itemstack))
		{
			return true;
		} else
		{
			return false;
		}
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side)
	{
        // Cannot extract items from reactor with automation disabled.
		// Can only extract from the bottom.
		if (Settings.AllowAutomation && side == 0 && slot == 2)
		{
			return true;
		}
		return false;
	}

	@Override
	public int getEnergyNeeded() 
	{
		return 0;
	}
}
