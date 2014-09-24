package minechem.item.polytool;

import java.util.ArrayList;

import minechem.item.element.ElementItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class PolytoolContainer extends Container
{
    public InventoryPlayer player;
    public ArrayList infusionsToUpdate = new ArrayList();

    public PolytoolContainer(EntityPlayer invPlayer)
    {
        this.player = invPlayer.inventory;

        addSlotToContainer(new PolytoolSlot(new PolytoolInventory(player.getCurrentItem(), invPlayer), 0, 8, 17));

        for (int x = 0; x < 9; x++)
        {
            addSlotToContainer(new Slot(player, x, 8 + 18 * x, 64 + 130));
        }

        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + 18 * x, 64 + 72 + y * 18));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {

        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot)
    {
    	System.out.println(slot);
        Slot slotObject = (Slot) inventorySlots.get(slot);
        ItemStack stack = null;
        
        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();
            if (slot ==0)
            {
            	return null;
            } 
            else 
            {	
            	if(stackInSlot.stackSize == 64 && (stackInSlot.getItem() instanceof ElementItem) && PolytoolHelper.getTypeFromElement(ElementItem.getElement(stackInSlot), 1) != null)
            	{
                    if (!mergeItemStack(stackInSlot, 0, 1, false))
                    {
                        return null;
                    }
            	}
            	else if(slot < 10)
             	{
             		if (!this.mergeItemStack(stackInSlot, 10, 37, true))
             		{
             			return null;
             		}
             	}
            	else if(slot > 9)
             	{
             		if (!this.mergeItemStack(stackInSlot, 1, 10, true))
             		{
             			return null;
             		}
             	}
            }
            if (stackInSlot.stackSize == 0)
            {
                slotObject.putStack(null);
            } else
            {
                slotObject.onSlotChanged();
            }
	         if (stackInSlot.stackSize == stack.stackSize)
	         {
	         	return null;
	         }
	         slotObject.onPickupFromSlot(entityPlayer, stackInSlot);
        }
        return stack;
    }
}
