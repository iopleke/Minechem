package minechem.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface IrwNBT
{
    public void readFromNBT(NBTTagCompound tag);
    public void writeToNBT(NBTTagCompound tag);
}
