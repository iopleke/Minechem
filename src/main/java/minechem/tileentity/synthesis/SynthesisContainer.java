package minechem.tileentity.synthesis;

import java.util.ArrayList;
import java.util.List;
import minechem.MinechemItemsGeneration;
import minechem.container.ContainerWithFakeSlots;
import minechem.item.chemistjournal.ChemistJournalSlot;
import minechem.radiation.IRadiationShield;
import minechem.slot.SlotChemical;
import minechem.slot.SlotFake;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SynthesisContainer extends ContainerWithFakeSlots implements IRadiationShield
{

    private SynthesisTileEntity synthesis;

    public SynthesisContainer(InventoryPlayer inventoryPlayer, SynthesisTileEntity synthesis)
    {
        this.synthesis = synthesis;
        addSlotToContainer(new SynthesisSlotOutput(synthesis, SynthesisTileEntity.kStartOutput, 134, 18));
        bindRecipeMatrixSlots();
        bindStorageSlots();
        addSlotToContainer(new ChemistJournalSlot(synthesis, SynthesisTileEntity.kStartJournal, 26, 36));
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

    private void bindRecipeMatrixSlots()
    {
        int slot = 0;
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                addSlotToContainer(new SlotFake(synthesis, SynthesisTileEntity.kStartRecipe + slot, 62 + (col * 18), 18 + (row * 18)));
                slot++;
            }
        }
    }

    private void bindStorageSlots()
    {
        int slot = 0;
        for (int col = 0; col < 9; col++)
        {
            addSlotToContainer(new SlotChemical(synthesis, SynthesisTileEntity.kStartStorage + slot, 8 + (col * 18), 84));
            slot++;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        return synthesis.isUseableByPlayer(var1);
    }

    public boolean craftMaxmimum()
    {
        List<ItemStack> outputs = synthesis.getMaximumOutput();
        if (outputs == null)
        {
            return false;
        }

        for (ItemStack output : outputs)
        {
            if (!mergeItemStack(output, synthesis.getSizeInventory(), inventorySlots.size(), true))
            {
                return false;
            }
        }

        return true;
    }

    public List<ItemStack> getPlayerInventory()
    {
        List<ItemStack> playerInventory = new ArrayList<ItemStack>();
        for (int slot = 27; slot < this.inventorySlots.size(); slot++)
        {
            ItemStack stack = getSlot(slot).getStack();
            if (stack != null)
            {
                playerInventory.add(stack);
            }
        }
        return playerInventory;
    }

    @Override
    public float getRadiationReductionFactor(int baseDamage, ItemStack itemstack, EntityPlayer player)
    {
        return 0.4F;
    }

    public List<ItemStack> getStorageInventory()
    {
        List<ItemStack> storageInventory = new ArrayList<ItemStack>();
        for (int slot = 0; slot < 27; slot++)
        {
            ItemStack stack = getSlot(slot).getStack();
            if (stack != null)
            {
                storageInventory.add(stack);
            }
        }
        return storageInventory;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot)
    {
        Slot slotObject = (Slot) inventorySlots.get(slot);

        if (slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            ItemStack stack = stackInSlot.copy();
            if (slot != SynthesisTileEntity.kStartJournal && stack.itemID == MinechemItemsGeneration.journal.itemID && !getSlot(SynthesisTileEntity.kStartJournal).getHasStack())
            {
                ItemStack copystack = slotObject.decrStackSize(1);
                getSlot(SynthesisTileEntity.kStartJournal).putStack(copystack);
                return null;
            } else if (slot == SynthesisTileEntity.kStartOutput)
            {
                if (entityPlayer.inventory.getFirstEmptyStack() == -1)
                {
                    return null;
                }
                if (!craftMaxmimum())
                {
                    return null;
                }
            } else if (slot >= synthesis.getSizeInventory() && slot < inventorySlots.size() && (stackInSlot.itemID == MinechemItemsGeneration.element.itemID || stackInSlot.itemID == MinechemItemsGeneration.molecule.itemID))
            {
                if (!mergeItemStack(stackInSlot, SynthesisTileEntity.kStartStorage, SynthesisTileEntity.kStartStorage + SynthesisTileEntity.kSizeStorage, false))
                {
                    return null;
                }
            } else if (slot >= SynthesisTileEntity.kStartStorage && slot < SynthesisTileEntity.kStartStorage + SynthesisTileEntity.kSizeStorage)
            {
                if (!mergeItemStack(stackInSlot, synthesis.getSizeInventory(), inventorySlots.size(), true))
                {
                    return null;
                }
            } else if (slot == SynthesisTileEntity.kStartJournal)
            {
                if (!mergeItemStack(stackInSlot, synthesis.getSizeInventory(), inventorySlots.size(), true))
                {
                    return null;
                }
            } else
            {
                return null;
            }

            if (stackInSlot.stackSize == 0)
            {
                slotObject.putStack(null);
            } else
            {
                slotObject.onSlotChanged();
            }

            return stack;
        }
        return null;
    }
}
