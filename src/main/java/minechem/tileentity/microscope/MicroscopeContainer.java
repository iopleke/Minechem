package minechem.tileentity.microscope;

import minechem.MinechemItemsRegistration;
import minechem.item.chemistjournal.ChemistJournalSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MicroscopeContainer extends Container
{

    MicroscopeTileEntity microscope;
    InventoryPlayer inventoryPlayer;

    public MicroscopeContainer(InventoryPlayer inventoryPlayer, MicroscopeTileEntity microscope)
    {
        this.microscope = microscope;
        this.inventoryPlayer = inventoryPlayer;
        addSlotToContainer(new Slot(microscope, 0, 44, 45));
        addSlotToContainer(new ChemistJournalSlot(microscope, 1, 80, 95));
        int slot = 0;
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                addSlotToContainer(new MicroscopeSlotOutput(microscope, 2 + slot, 98 + (col * 18), 27 + (row * 18)));
                slot++;
            }
        }
        bindPlayerInventory(inventoryPlayer);
    }

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        int inventoryY = 135;
        int hotBarY = 193;
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
    public boolean canInteractWith(EntityPlayer var1)
    {
        return microscope.isUseableByPlayer(var1);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot)
    {
        Slot slotObject = (Slot) inventorySlots.get(slot);
        ItemStack stack = null;
        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();
            if (slot <= 1)
            {
                if (!mergeItemStack(stackInSlot, 2, inventorySlots.size(), true))
                {
                    return null;
                }
            }
            if (slot != 1 && stack.getItem() == MinechemItemsRegistration.journal && !getSlot(1).getHasStack())
            {
                ItemStack copy = slotObject.decrStackSize(1);
                getSlot(1).putStack(copy);
                return null;
            }
            if (slot > 1 && stack.getItem() != MinechemItemsRegistration.journal && !getSlot(0).getHasStack())
            {
                ItemStack copy = slotObject.decrStackSize(1);
                getSlot(0).putStack(copy);
                return null;
            }
            if (slot > 37 && stackInSlot.stackSize == stack.stackSize)
            {
                if (!this.mergeItemStack(stackInSlot, 11, 38, false))
                {
                    return null;
                }
            }
            if (slot < 38 && stackInSlot.stackSize == stack.stackSize)
            {
                if (!this.mergeItemStack(stackInSlot, 38, 47, false))
                {
                    return null;
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
