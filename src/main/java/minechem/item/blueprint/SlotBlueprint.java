package minechem.item.blueprint;

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

public class SlotBlueprint extends Slot
{

    public SlotBlueprint(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
<<<<<<< HEAD
<<<<<<< HEAD
        return itemstack.itemID == MinechemItemGeneration.blueprint.itemID;
=======
        return itemstack.itemID == MinechemItemsGeneration.blueprint.itemID;
>>>>>>> MaxwolfRewrite
=======
        return itemstack.itemID == MinechemItemsGeneration.blueprint.itemID;
>>>>>>> MaxwolfRewrite
    }

}
