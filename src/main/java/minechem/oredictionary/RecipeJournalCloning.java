package minechem.oredictionary;

<<<<<<< HEAD
<<<<<<< HEAD
import minechem.MinechemItemGeneration;
=======
import minechem.MinechemItemsGeneration;
>>>>>>> MaxwolfRewrite
=======
import minechem.MinechemItemsGeneration;
>>>>>>> MaxwolfRewrite
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeJournalCloning implements IRecipe
{

    @Override
    public boolean matches(InventoryCrafting crafting, World world)
    {
        ItemStack itemstack1 = crafting.getStackInSlot(0);
        ItemStack itemstack2 = crafting.getStackInSlot(1);
<<<<<<< HEAD
<<<<<<< HEAD
        return (itemstack1 != null && itemstack1.itemID == MinechemItemGeneration.journal.itemID) && (itemstack2 != null && itemstack2.itemID == Item.book.itemID);
=======
        return (itemstack1 != null && itemstack1.itemID == MinechemItemsGeneration.journal.itemID) && (itemstack2 != null && itemstack2.itemID == Item.book.itemID);
>>>>>>> MaxwolfRewrite
=======
        return (itemstack1 != null && itemstack1.itemID == MinechemItemsGeneration.journal.itemID) && (itemstack2 != null && itemstack2.itemID == Item.book.itemID);
>>>>>>> MaxwolfRewrite
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting crafting)
    {
        ItemStack journal = crafting.getStackInSlot(0);
        ItemStack newJournal = journal.copy();
        newJournal.stackSize = 2;
        return newJournal;
    }

    @Override
    public int getRecipeSize()
    {
        return 4;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return null;
    }

}
