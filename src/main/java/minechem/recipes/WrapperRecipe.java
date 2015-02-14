package minechem.recipes;

import minechem.item.prefab.WrapperItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class WrapperRecipe implements IRecipe
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
        ItemStack wrapper = getWrapper(crafting);
        if (wrapper == null) return false;
        ItemStack item = getItem(crafting);
        if (item == null) return false;
        return ((WrapperItem)wrapper.getItem()).isWrappable(item);
    }

    private ItemStack getWrapper(IInventory crafting)
    {
        ItemStack wrapper = null;
        for (int i = 0; i< crafting.getSizeInventory(); i++)
        {
            ItemStack itemStack = crafting.getStackInSlot(i);
            if (itemStack == null) continue;
            if (itemStack.getItem() instanceof WrapperItem)
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
            if (!(itemStack.getItem() instanceof WrapperItem))
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
        ItemStack wrapper = getWrapper(crafting);
        if (wrapper == null) return null;
        ItemStack item = getItem(crafting);
        if (item == null) return null;
        ItemStack result = wrapper.copy();
        ((WrapperItem)result.getItem()).setWrappedItemStack(result,item);
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
