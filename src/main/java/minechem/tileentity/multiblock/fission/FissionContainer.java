package minechem.tileentity.multiblock.fission;

import minechem.MinechemItemsRegistration;
import minechem.api.IRadiationShield;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FissionContainer extends Container implements IRadiationShield
{

    protected FissionTileEntity fission;
    protected final int kPlayerInventorySlotStart;
    protected final int kPlayerInventorySlotEnd;
    protected final int kDecomposerInventoryEnd;

    public FissionContainer(InventoryPlayer inventoryPlayer, FissionTileEntity fission)
    {
        this.fission = fission;
        kPlayerInventorySlotStart = fission.getSizeInventory();
        kPlayerInventorySlotEnd = kPlayerInventorySlotStart + (9 * 4);
        kDecomposerInventoryEnd = fission.getSizeInventory();

        addSlotToContainer(new Slot(fission, FissionTileEntity.kInput[0], 80, 16));
        bindOutputSlot();
        bindPlayerInventory(inventoryPlayer);
    }

    private void bindOutputSlot()
    {
        int x = 8;
        int y = 62;
        int j = 0;
        addSlotToContainer(new Slot(fission, 2, x + (4 * 18), y));
    }

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
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

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return fission.isUseableByPlayer(entityPlayer);
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
            if (slot < 2)
            {
                if (!mergeItemStack(stackInSlot, kPlayerInventorySlotStart, inventorySlots.size(), true))
                {
                    return null;
                }
            } else
            {
                if (stackInSlot.getItem() == MinechemItemsRegistration.element && stackInSlot.getItemDamage() > 0)
                {
                    if (!mergeItemStack(stackInSlot, 0, 1, false))
                    {
                        return null;
                    }
                }
                if (slot < 29 && stackInSlot.stackSize == stack.stackSize)
                {
                    if (!this.mergeItemStack(stackInSlot, 29, 38, false))
                    {
                        return null;
                    }
                }
                if (slot > 28 && stackInSlot.stackSize == stack.stackSize)
                {
                    if (!this.mergeItemStack(stackInSlot, 2, 29, false))
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

    @Override
    public float getRadiationReductionFactor(int baseDamage, ItemStack itemstack, EntityPlayer player)
    {
        return 0.4F;
    }

}
