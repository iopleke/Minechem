package pixlepix.minechem.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import pixlepix.minechem.common.tileentity.TileEntityLeadedChest;

public class ContainerLeadedChest extends Container {

    protected TileEntityLeadedChest leadedchest;

    public ContainerLeadedChest(InventoryPlayer inventoryPlayer, TileEntityLeadedChest leadedChest) {
        this.leadedchest = leadedChest;

        this.bindOutputSlots();
        this.bindPlayerInventory(inventoryPlayer);
    }

    private void bindOutputSlots() {
        int x = 8;
        int y = 18;
        int j = 0;
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(leadedchest, i, x + (j * 18), y));
            j++;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.leadedchest.isUseableByPlayer(entityplayer);
    }

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        int inventoryY = 50;
        int hotBarY = 108;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, inventoryY + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, hotBarY));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            //merges the item into player inventory since its in the tileEntity
            if (slot < 9) {
                if (!this.mergeItemStack(stackInSlot, 0, 35, true)) {
                    return null;
                }
            } //places it into the tileEntity is possible since its in the player inventory
            else if (!this.mergeItemStack(stackInSlot, 0, 9, false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;

    }
}
