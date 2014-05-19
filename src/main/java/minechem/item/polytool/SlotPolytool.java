package minechem.item.polytool;

import minechem.item.element.ItemElement;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPolytool extends Slot
{

    public SlotPolytool(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);

    }

    @Override
    public boolean isItemValid(ItemStack itemstack)
    {

        return itemstack == null || (itemstack.stackSize == 64 && (itemstack.getItem() instanceof ItemElement) && PolytoolHelper.getTypeFromElement(ItemElement.getElement(itemstack), 1) != null);

    }

}
