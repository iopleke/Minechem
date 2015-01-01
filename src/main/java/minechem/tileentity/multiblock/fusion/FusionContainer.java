package minechem.tileentity.multiblock.fusion;

import minechem.api.IRadiationShield;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FusionContainer extends Container implements IRadiationShield
{

    FusionTileEntity fusion;
    InventoryPlayer inventoryPlayer;

    public FusionContainer(InventoryPlayer inventoryPlayer, FusionTileEntity fusion)
    {
        this.inventoryPlayer = inventoryPlayer;
        this.fusion = fusion;

        addSlotToContainer(new Slot(fusion, FusionTileEntity.inputLeft, 22, 62));
        addSlotToContainer(new Slot(fusion, FusionTileEntity.inputRight, 138, 62));
        addSlotToContainer(new Slot(fusion, FusionTileEntity.output, 80, 62));

        bindPlayerInventory(inventoryPlayer);
    }

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 105 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 163));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        return fusion.isUseableByPlayer(var1);
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

            if (slot < fusion.getSizeInventory())
            {
                if (!mergeItemStack(stackInSlot, fusion.getSizeInventory(), inventorySlots.size(), true))
                {
                    return null;
                }
            } else
            {
                if (fusion.isItemValidForSlot(1, stackInSlot))
                {
                    if (!mergeItemStack(stackInSlot, FusionTileEntity.inputLeft, FusionTileEntity.inputRight + 1, false))
                    {
                        return null;
                    }
                }
                if (slot < 30 && stackInSlot.stackSize == stack.stackSize)
                {
                    if (!this.mergeItemStack(stackInSlot, 30, 39, false))
                    {
                        return null;
                    }
                }
                if (slot > 29 && stackInSlot.stackSize == stack.stackSize)
                {
                    if (!this.mergeItemStack(stackInSlot, 3, 30, false))
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
        return 1.0F;
    }

}
