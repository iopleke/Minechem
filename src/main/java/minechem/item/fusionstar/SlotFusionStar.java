package minechem.item.fusionstar;

<<<<<<< HEAD
import minechem.MinechemItemGeneration;
=======
import minechem.MinechemItemsGeneration;
>>>>>>> MaxwolfRewrite
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
<<<<<<< HEAD
        return itemStack.itemID == Item.netherStar.itemID || itemStack.itemID == MinechemItemGeneration.fusionStar.itemID;
=======
        return itemStack.itemID == Item.netherStar.itemID || itemStack.itemID == MinechemItemsGeneration.fusionStar.itemID;
>>>>>>> MaxwolfRewrite
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }

}
