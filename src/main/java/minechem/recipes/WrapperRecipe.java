package minechem.recipes;

import minechem.Compendium;
import minechem.item.prefab.WrapperItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;

public class WrapperRecipe implements IRecipe
{
    public WrapperRecipe()
    {
        RecipeSorter.register(Compendium.Naming.id + ":wrapper", getClass(), RecipeSorter.Category.SHAPELESS, "after:forge:shapelessore");
    }

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
