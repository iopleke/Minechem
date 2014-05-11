package pixlepix.minechem.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import pixlepix.minechem.common.ModMinechem;
import pixlepix.minechem.common.tileentity.TileEntityLeadedChest;

public class ContainerLeadedChest extends Container {

    protected TileEntityLeadedChest te;

    public ContainerLeadedChest(InventoryPlayer inventoryPlayer, TileEntityLeadedChest tileEntity) {
        te = tileEntity;
        int chestSize = ModMinechem.leadedChestSize;
        int rows = 1;
        int lastRow = 9;
        if (chestSize >= 9) {
            rows = chestSize / 9 + 1;
            lastRow = chestSize % 9;
            if (lastRow == 0) {
                rows--;
                lastRow = 9;
            }
        }

        for (int i = 0; i < rows; i++) {
            if (i != rows) {
                for (int j = 0; j < 9; j++) {
                    this.addSlotToContainer(new Slot(te, j + i * rows, 62 + j * 18, 17 + i * 18));
                }
            } else {
                for (int j = 0; j < lastRow; j++) {
                    this.addSlotToContainer(new Slot(te, j + i * rows, 62 + j * 18, 17 + i * 18));
                }
            }

        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.te.isUseableByPlayer(entityplayer);
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        ItemStack stack = null;
        Slot slotObject = (Slot) this.inventorySlots.get(slot);
        
        if(slotObject != null && slotObject.getHasStack()){
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();
            
            if(slot < 9){
                if(!this.mergeItemStack(stackInSlot, 0, 39, false)){
                    return null;
                }
            } else if(!this.mergeItemStack(stackInSlot, 0, 9, false)){
                return null;
            }
            
            if(stackInSlot.stackSize == 0){
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }
            if(stackInSlot.stackSize == stack.stackSize){
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }

}
