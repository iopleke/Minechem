package ljdp.minechem.common.tileentity;

import ljdp.minechem.common.MinechemBlocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityProxy extends TileEntity {

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

}
