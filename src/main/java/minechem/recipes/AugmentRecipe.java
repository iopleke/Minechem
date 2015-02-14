package minechem.recipes;

import minechem.item.augment.IAugmentedItem;
import minechem.registry.AugmentRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class AugmentRecipe implements IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param crafting
     * @param world
     */
    @Override
    public boolean matches(InventoryCrafting crafting, World world)
    {
        ItemStack augmented = getAugmentable(crafting);
        if (augmented == null) return false;
        ItemStack augmentItem = getItem(crafting);
        if (augmentItem == null) return false;
        return ((IAugmentedItem)augmented.getItem()).canHaveAugment(augmented, AugmentRegistry.getAugment(augmentItem));
    }

    private ItemStack getAugmentable(IInventory crafting)
    {
        ItemStack wrapper = null;
        for (int i = 0; i< crafting.getSizeInventory(); i++)
        {
            ItemStack itemStack = crafting.getStackInSlot(i);
            if (itemStack == null) continue;
            if (itemStack.getItem() instanceof IAugmentedItem)
            {
                if (wrapper == null) wrapper = itemStack;
                else return null;
            }
        }
        return wrapper;
    }

    private ItemStack getItem(IInventory crafting)
    {
        ItemStack item = null;
        for (int i = 0; i< crafting.getSizeInventory(); i++)
        {
            ItemStack itemStack = crafting.getStackInSlot(i);
            if (itemStack == null) continue;
            if (AugmentRegistry.getAugment(itemStack)!=null)
            {
                if (item == null) item = itemStack;
                else return null;
            }
        }
        return item;
    }

    /**
     * Returns an Item that is the result of this recipe
     *
     * @param crafting
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting crafting)
    {
        ItemStack augment = getAugmentable(crafting);
        if (augment == null) return null;
        ItemStack item = getItem(crafting);
        if (item == null) return null;
        ItemStack result = augment.copy();
        ((IAugmentedItem)result.getItem()).setAugment(result,item);
        return result;
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize()
    {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return null;
    }
}
