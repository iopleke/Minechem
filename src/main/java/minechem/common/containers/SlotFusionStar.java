package minechem.common.containers;

import minechem.common.MinechemItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotFusionStar extends Slot
{

    public SlotFusionStar(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return itemStack.itemID == Item.netherStar.itemID || itemStack.itemID == MinechemItems.fusionStar.itemID;
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }

}
