package minechem.common.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class TileEntityChemicalStorage extends TileEntityChest
{

    public void addStackToInventory(ItemStack newStack)
    {
        for (int i = 0; i < this.getSizeInventory(); i++)
        {
            ItemStack stack = this.getStackInSlot(i);
            if (stack == null)
            {
                this.setInventorySlotContents(i, newStack);
                return;
            }
            if (stack.stackSize < 64 && stack.getItem() == newStack.getItem() && stack.getItemDamage() == newStack.getItemDamage())
            {
                stack.stackSize++;
                return;
            }
        }
        worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord + 2, zCoord, newStack));
    }

}
