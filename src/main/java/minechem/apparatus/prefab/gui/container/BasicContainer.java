package minechem.apparatus.prefab.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class BasicContainer extends Container
{
    /**
     * Add the player's inventory slots to the GUI
     *
     * @param inventoryPlayer the player's inventory
     */
    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    /**
     * Determine if the player can interact with the container
     *
     * @param entityPlayer the player entity
     * @return boolean
     */
    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
    {
        Slot slot=(Slot) inventorySlots.get(slotNumber);
        ItemStack stack=slot.getStack();
        
        if (stack!=null&&slot.getHasStack()){
            if (slotNumber<player.inventory.mainInventory.length){
                if (!mergeItemStack(stack, player.inventory.mainInventory.length, inventorySlots.size(), true)){
                    return null;
                }
            }else{
                if (!mergeItemStack(stack, 0, player.inventory.mainInventory.length, true)){
                    return null;
                }
            }
            
            if (stack.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return stack;
    }
}
