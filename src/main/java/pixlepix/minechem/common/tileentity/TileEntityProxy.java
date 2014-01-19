package pixlepix.minechem.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import pixlepix.minechem.common.MinechemBlocks;

public class TileEntityProxy extends TileEntity implements ISidedInventory {

	public TileEntity manager;
	int managerXOffset;
	int managerYOffset;
	int managerZOffset;

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		if (manager != null) {
			nbtTagCompound.setInteger("managerXOffset", manager.xCoord);
			nbtTagCompound.setInteger("managerYOffset", manager.yCoord);
			nbtTagCompound.setInteger("managerZOffset", manager.zCoord);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		managerXOffset = nbtTagCompound.getInteger("managerXOffset");
		managerYOffset = nbtTagCompound.getInteger("managerYOffset");
		managerZOffset = nbtTagCompound.getInteger("managerZOffset");
		if (worldObj != null)
			manager = worldObj.getBlockTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset);
	}

	public void setManager(TileEntity managerTileEntity) {

		this.manager = managerTileEntity;
		if (managerTileEntity != null) {
			this.managerXOffset = managerTileEntity.xCoord - xCoord;
			this.managerYOffset = managerTileEntity.yCoord - yCoord;
			this.managerZOffset = managerTileEntity.zCoord - zCoord;
		}
	}

	public TileEntity getManager() {
		if (worldObj.getBlockTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset) != null && !(worldObj.getBlockTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset) instanceof TileEntityProxy)) {

			return worldObj.getBlockTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset);
		}
		if (worldObj.getBlockId(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset) == MinechemBlocks.fusion.blockID) {
			this.manager = buildManagerBlock();
			return this.manager;
		}

		return null;

	}

	private TileEntity buildManagerBlock() {

		if (this.worldObj.getBlockMetadata(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset) == 2) {
			TileEntityFusion fusion = new TileEntityFusion();
			fusion.worldObj = this.worldObj;
			fusion.zCoord = this.managerZOffset + zCoord;
			fusion.yCoord = this.managerYOffset + yCoord;
			fusion.xCoord = this.managerXOffset + xCoord;
			fusion.blockType = MinechemBlocks.fusion;
			worldObj.setBlockTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset, fusion);
		}
		if (this.worldObj.getBlockMetadata(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset) == 3) {
			TileEntityFission fission = new TileEntityFission();
			fission.worldObj = this.worldObj;
			fission.zCoord = this.managerZOffset + zCoord;
			fission.yCoord = this.managerYOffset + yCoord;
			fission.xCoord = this.managerXOffset + xCoord;
			fission.blockType = MinechemBlocks.fusion;
			worldObj.setBlockTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset, fission);
		}
		return worldObj.getBlockTileEntity(xCoord + managerXOffset, yCoord + managerYOffset, zCoord + managerZOffset);

	}

	@Override
	public int getSizeInventory() {
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory) {
			return ((ISidedInventory) this.getManager()).getSizeInventory();
		}
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory) {
			return ((ISidedInventory) this.getManager()).getStackInSlot(i);
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory) {
			return ((ISidedInventory) this.getManager()).decrStackSize(i, j);
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory) {
			return ((ISidedInventory) this.getManager()).getStackInSlotOnClosing(i);
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory) {
			((ISidedInventory) this.getManager()).setInventorySlotContents(i, itemstack);
		}
	}

	@Override
	public String getInvName() {
		return "Multiblock Minechem proxy";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory) {
			return ((ISidedInventory) this.getManager()).getInventoryStackLimit();
		}
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory) {
			return ((ISidedInventory) this.getManager()).isItemValidForSlot(i, itemstack);
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory) {
			return ((ISidedInventory) this.getManager()).getAccessibleSlotsFromSide(var1);

		}
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory) {
			return ((ISidedInventory) this.getManager()).canInsertItem(i, itemstack, j);
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if (this.getManager() != null && this.getManager() instanceof ISidedInventory) {
			return ((ISidedInventory) this.getManager()).canExtractItem(i, itemstack, j);
		}
		return false;
	}

}
