package minechem.tileentity.blueprintprojector;

import minechem.item.blueprint.SlotBlueprint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BlueprintProjectorContainer extends Container
{

    BlueprintProjectorTileEntity projector;
    InventoryPlayer inventoryPlayer;

    public BlueprintProjectorContainer(InventoryPlayer inventoryPlayer, BlueprintProjectorTileEntity projector)
    {
        this.inventoryPlayer = inventoryPlayer;
        this.projector = projector;
        addSlotToContainer(new SlotBlueprint(projector, 0, 25, 47));
        bindPlayerInventory(inventoryPlayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        return true;
    }

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        int inventoryY = 84;
        int hotBarY = 142;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, inventoryY + i * 18));
            }
        }

        for (int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, hotBarY));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot)
    {
         Slot slotObject = (Slot) inventorySlots.get(slot); 
         ItemStack stack =  null;
         if (slotObject != null && slotObject.getHasStack()) 
         { 
        	 ItemStack stackInSlot = slotObject.getStack(); 
        	 stack = stackInSlot.copy(); 
        	 if (slot == 0) 
        	 { 
        		 if (!mergeItemStack(stackInSlot, 1, inventorySlots.size(), true)) 
        			 return null; 
        	 } 
        	 else 
        	 { 
        		 if(projector.isItemValidForSlot(slot, stackInSlot) && !getSlot(0).getHasStack())
        		 {
                     ItemStack copy = slotObject.decrStackSize(1);
                     getSlot(0).putStack(copy);
                     return null;
        		 }
             	if(slot<28 && stackInSlot.stackSize == stack.stackSize)
             	{
             		if (!this.mergeItemStack(stackInSlot, 28, 37, false))
             		{
             			return null;
             		}
             	}
             	if(slot>27 && stackInSlot.stackSize == stack.stackSize)
             	{
             		if (!this.mergeItemStack(stackInSlot, 1, 28, false))
             		{
             			return null;
             		}
             	}
        	 }
         
	         if (stackInSlot.stackSize == 0)
	         {
	        	 if(slot == 0)
	        	 {
	        		 projector.setBlueprint(null);
	        	 }
	        	 slotObject.putStack(null); 
	         }
	         else
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
