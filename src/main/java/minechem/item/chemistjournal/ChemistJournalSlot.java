package minechem.item.chemistjournal;

import minechem.MinechemItemsGeneration;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ChemistJournalSlot extends Slot
{

    public ChemistJournalSlot(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        return itemstack.itemID == MinechemItemsGeneration.journal.itemID;
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }

}
