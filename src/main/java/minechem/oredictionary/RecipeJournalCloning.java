package minechem.oredictionary;

import minechem.MinechemItems;
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
        return (itemstack1 != null && itemstack1.itemID == MinechemItems.journal.itemID) && (itemstack2 != null && itemstack2.itemID == Item.book.itemID);
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
