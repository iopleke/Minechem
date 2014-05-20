package minechem.slots;

import minechem.MinechemItemGeneration;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotFakeChemical extends SlotFake
{

    public SlotFakeChemical(IInventory iInventory, int id, int x, int y)
    {
        super(iInventory, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return itemStack.itemID == MinechemItemGeneration.element.itemID || itemStack.itemID == MinechemItemGeneration.molecule.itemID;
    }

}
