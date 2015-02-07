package minechem.interfaces;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Can be applied to objects to define that they can be read and write to NBT
 */
public interface IrwNBT
{
    public void readFromNBT(NBTTagCompound tag);
    public void writeToNBT(NBTTagCompound tag);
}
