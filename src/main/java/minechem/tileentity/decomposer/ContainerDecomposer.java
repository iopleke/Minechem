package minechem.tileentity.decomposer;

import minechem.containers.SlotOutput;
import minechem.item.IRadiationShield;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDecomposer extends Container implements IRadiationShield
{

    protected TileEntityDecomposer decomposer;
    protected final int kPlayerInventorySlotStart;
    protected final int kPlayerInventorySlotEnd;
    protected final int kDecomposerInventoryEnd;

    public ContainerDecomposer(InventoryPlayer inventoryPlayer, TileEntityDecomposer decomposer)
    {
        this.decomposer = decomposer;
        kPlayerInventorySlotStart = decomposer.getSizeInventory();
        kPlayerInventorySlotEnd = kPlayerInventorySlotStart + (9 * 4);
        kDecomposerInventoryEnd = decomposer.getSizeInventory();

        addSlotToContainer(new Slot(decomposer, decomposer.kInputSlot, 80, 16));
        bindOutputSlots();
        bindPlayerInventory(inventoryPlayer);
    }

    private void bindOutputSlots()
    {
        int x = 8;
        int y = 62;
        int j = 0;
        for (int i = 1; i < 10; i++)
        {
            addSlotToContainer(new SlotOutput(decomposer, i, x + (j * 18), y));
            j++;
        }
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
        return decomposer.isUseableByPlayer(entityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot)
    {
        Slot slotObject = (Slot) inventorySlots.get(slot);
        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            ItemStack stack = stackInSlot.copy();
            if (slot >= 0 && slot < kDecomposerInventoryEnd)
            {
                if (!mergeItemStack(stackInSlot, kPlayerInventorySlotStart, inventorySlots.size(), true))
                    return null;
            }
            else if (slot >= kPlayerInventorySlotStart)
            {
                if (!mergeItemStack(stackInSlot, decomposer.kInputSlot, decomposer.kInputSlot + 1, false))
                    return null;
            }
            else if (!mergeItemStack(stackInSlot, kPlayerInventorySlotStart, inventorySlots.size(), true))
                return null;

            if (stackInSlot.stackSize == 0)
                slotObject.putStack(null);
            else
                slotObject.onSlotChanged();

            return stack;
        }
        return null;
    }

    @Override
    public float getRadiationReductionFactor(int baseDamage, ItemStack itemstack, EntityPlayer player)
    {
        return 0.4F;
    }

}
