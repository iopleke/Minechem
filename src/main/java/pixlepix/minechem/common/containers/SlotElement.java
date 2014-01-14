package pixlepix.minechem.common.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import pixlepix.minechem.common.MinechemItems;

public class SlotElement extends Slot {

    public SlotElement(IInventory par1iInventory, int par2, int par3, int par4) {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return itemStack.itemID == MinechemItems.element.itemID;
    }
}
