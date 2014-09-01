package minechem.item.fusionstar;

import minechem.MinechemItemsRegistration;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FusionStarSlot extends Slot
{

    public FusionStarSlot(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return itemStack.getItem()== Items.nether_star || itemStack.getItem() == MinechemItemsRegistration.fusionStar;
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }

}
