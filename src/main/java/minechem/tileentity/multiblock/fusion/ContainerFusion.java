package minechem.tileentity.multiblock.fusion;

import minechem.radiation.IRadiationShield;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFusion extends Container implements IRadiationShield
{

    TileEntityFusion fusion;
    InventoryPlayer inventoryPlayer;

    public ContainerFusion(InventoryPlayer inventoryPlayer, TileEntityFusion fusion)
    {
        this.inventoryPlayer = inventoryPlayer;
        this.fusion = fusion;

        addSlotToContainer(new Slot(fusion, TileEntityFusion.kStartFusionStar, 80, 18));
        addSlotToContainer(new Slot(fusion, TileEntityFusion.kStartInput1, 22, 62));
        addSlotToContainer(new Slot(fusion, TileEntityFusion.kStartInput2, 138, 62));
        addSlotToContainer(new Slot(fusion, TileEntityFusion.kStartOutput, 80, 62));

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
        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            ItemStack stack = stackInSlot.copy();
            if (slot >= 0 && slot < fusion.getSizeInventory())
            {
                if (!mergeItemStack(stackInSlot, fusion.getSizeInventory(), inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (slot >= fusion.getSizeInventory())
            {
                if (!mergeItemStack(stackInSlot, TileEntityFusion.kStartInput1, TileEntityFusion.kStartInput1 + 1, false))
                {
                    return null;
                }
            }

            if (stackInSlot.stackSize == 0)
            {
                slotObject.putStack(null);
            }
            else
            {
                slotObject.onSlotChanged();
            }
            return stack;
        }
        return null;
    }

    @Override
    public float getRadiationReductionFactor(int baseDamage, ItemStack itemstack, EntityPlayer player)
    {
        return 1.0F;
    }

}
