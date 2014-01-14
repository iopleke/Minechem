package pixlepix.minechem.minechem.common.tileentity;

import pixlepix.minechem.minechem.common.MinechemBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityProxy extends TileEntity implements ISidedInventory {

    public TileEntity manager;
    int managerX;
    int managerY;
    int managerZ;
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        if (manager != null) {
            nbtTagCompound.setInteger("managerX", manager.xCoord);
            nbtTagCompound.setInteger("managerY", manager.yCoord);
            nbtTagCompound.setInteger("managerZ", manager.zCoord);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        managerX = nbtTagCompound.getInteger("managerX");
        managerY = nbtTagCompound.getInteger("managerY");
        managerZ = nbtTagCompound.getInteger("managerZ");
        if (worldObj != null)
            manager = worldObj.getBlockTileEntity(managerX, managerY, managerZ);
    }
    
    public void setManager(TileEntity managerTileEntity) {
    	
        this.manager = managerTileEntity;
	    if(managerTileEntity != null){
	        this.managerX=managerTileEntity.xCoord;
	        this.managerY=managerTileEntity.yCoord;
	        this.managerZ=managerTileEntity.zCoord;
        }
    }

    public TileEntity getManager() {
    	if(this.manager!=null&&!(this.manager instanceof TileEntityProxy)){
    		return this.manager;
    	}
    	else{
    		if(worldObj.getBlockTileEntity(managerX, managerY, managerZ)!=null&& !(worldObj.getBlockTileEntity(managerX, managerY, managerZ) instanceof TileEntityProxy)){
    			return worldObj.getBlockTileEntity(managerX, managerY, managerZ);
    		}
    		if(worldObj.getBlockId(managerX, managerY, managerZ)==MinechemBlocks.fusion.blockID){
    			this.manager=buildManagerBlock();
    			return this.manager;
    		}
    	}
    	return null;
    }
    private TileEntity buildManagerBlock() {
            
            if(this.worldObj.getBlockMetadata(managerX,managerY,managerZ)==2){
            	TileEntityFusion fusion=new TileEntityFusion();
            	fusion.worldObj=this.worldObj;
            	fusion.xCoord=this.managerZ;
            	fusion.yCoord=this.managerY;
            	fusion.zCoord=this.managerX;
            	fusion.blockType=MinechemBlocks.fusion;
            	worldObj.setBlockTileEntity(xCoord, yCoord, zCoord, fusion);
            }
            if(this.worldObj.getBlockMetadata(managerX,managerY,managerZ)==3){
            	TileEntityFission fusion=new TileEntityFission();
            	fusion.worldObj=this.worldObj;
            	fusion.xCoord=this.managerZ;
            	fusion.yCoord=this.managerY;
            	fusion.zCoord=this.managerX;
            	fusion.blockType=MinechemBlocks.fusion;
            	worldObj.setBlockTileEntity(xCoord, yCoord, zCoord, fusion);
            }
            return worldObj.getBlockTileEntity(managerX,managerY,managerZ);
       
    }

	@Override
	public int getSizeInventory() {
		if(this.getManager()!=null&&this.getManager() instanceof ISidedInventory){
			return ((ISidedInventory) this.getManager()).getSizeInventory();
		}
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if(this.getManager()!=null&&this.getManager() instanceof ISidedInventory){
			return ((ISidedInventory) this.getManager()).getStackInSlot(i);
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(this.getManager()!=null&&this.getManager() instanceof ISidedInventory){
			return ((ISidedInventory) this.getManager()).decrStackSize(i, j);
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(this.getManager()!=null&&this.getManager() instanceof ISidedInventory){
			return ((ISidedInventory) this.getManager()).getStackInSlotOnClosing(i);
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if(this.getManager()!=null&&this.getManager() instanceof ISidedInventory){
			((ISidedInventory) this.getManager()).setInventorySlotContents(i,itemstack);
		}
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return "Multiblock Minechem proxy";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		if(this.getManager()!=null&&this.getManager() instanceof ISidedInventory){
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
		if(this.getManager()!=null&&this.getManager() instanceof ISidedInventory){
			return ((ISidedInventory) this.getManager()).isItemValidForSlot(i,itemstack);
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		if(this.getManager()!=null&&this.getManager() instanceof ISidedInventory){
			return ((ISidedInventory) this.getManager()).getAccessibleSlotsFromSide(var1);
		}
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		if(this.getManager()!=null&&this.getManager() instanceof ISidedInventory){
			return ((ISidedInventory) this.getManager()).canInsertItem(i, itemstack, j);
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if(this.getManager()!=null&&this.getManager() instanceof ISidedInventory){
			return ((ISidedInventory) this.getManager()).canExtractItem(i, itemstack, j);
		}
		return false;
	}

}
