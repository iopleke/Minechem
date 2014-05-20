package minechem.item.element;

<<<<<<< HEAD
<<<<<<< HEAD
import minechem.MinechemItemGeneration;
=======
import minechem.MinechemItemsGeneration;
>>>>>>> MaxwolfRewrite
=======
import minechem.MinechemItemsGeneration;
>>>>>>> MaxwolfRewrite
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotElement extends Slot
{

    public SlotElement(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
<<<<<<< HEAD
<<<<<<< HEAD
        return itemStack.itemID == MinechemItemGeneration.element.itemID;
=======
        return itemStack.itemID == MinechemItemsGeneration.element.itemID;
>>>>>>> MaxwolfRewrite
=======
        return itemStack.itemID == MinechemItemsGeneration.element.itemID;
>>>>>>> MaxwolfRewrite
    }
}
