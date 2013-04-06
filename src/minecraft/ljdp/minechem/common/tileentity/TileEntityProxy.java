package ljdp.minechem.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityProxy extends TileEntity {

    public TileEntity manager;

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
        int managerX = nbtTagCompound.getInteger("managerX");
        int managerY = nbtTagCompound.getInteger("managerY");
        int managerZ = nbtTagCompound.getInteger("managerZ");
        if (worldObj != null)
            manager = worldObj.getBlockTileEntity(managerX, managerY, managerZ);
    }

    public void setManager(TileEntity managerTileEntity) {
        this.manager = managerTileEntity;
    }

    public TileEntity getManager() {
        return this.manager;
    }

}
