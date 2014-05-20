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
        return null;
        /* Slot slotObject = (Slot) inventorySlots.get(slot); if (slotObject != null && slotObject.getHasStack()) { ItemStack stackInSlot = slotObject.getStack(); ItemStack stack = stackInSlot.copy(); if (slot == 0) { if (!mergeItemStack(stackInSlot, 1,
         * inventorySlots.size(), true)) return null; } else { if (!mergeItemStack(stackInSlot, 0, 1, false)) return null; }
         * 
         * if (stackInSlot.stackSize == 0) slotObject.putStack(null); else slotObject.onSlotChanged();
         * 
         * return stack; } return null; */
    }

}
