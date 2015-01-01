package minechem.slot;

import minechem.MinechemItemsRegistration;
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
        return itemStack.getItem() == MinechemItemsRegistration.element || itemStack.getItem() == MinechemItemsRegistration.molecule;
    }

}
