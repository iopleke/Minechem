package minechem.recipes;

import minechem.Compendium;
import minechem.item.augment.IAugmentedItem;
import minechem.registry.AugmentRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;

public class AugmentRecipe implements IRecipe
{
    public AugmentRecipe()
    {
        RecipeSorter.register(Compendium.Naming.id + ":augment", getClass(), RecipeSorter.Category.SHAPELESS, "after:" + Compendium.Naming.id + ":wrapper");
    }

    @Override
    public boolean matches(InventoryCrafting crafting, World world)
    {
        ItemStack augmented = getAugmentable(crafting);
        if (augmented == null)
        {
            return false;
        }
        ItemStack augmentItem = getItem(crafting);
        if (augmentItem == null)
        {
            return false;
        }
        return ((IAugmentedItem) augmented.getItem()).canHaveAugment(augmented, AugmentRegistry.getAugment(augmentItem));
    }

    private ItemStack getAugmentable(IInventory crafting)
    {
        ItemStack wrapper = null;
        for (int i = 0; i < crafting.getSizeInventory(); i++)
        {
            ItemStack itemStack = crafting.getStackInSlot(i);
            if (itemStack == null)
            {
                continue;
            }
            if (itemStack.getItem() instanceof IAugmentedItem)
            {
                if (wrapper == null)
                {
                    wrapper = itemStack;
                } else
                {
                    return null;
                }
            }
        }
        return wrapper;
    }

    private ItemStack getItem(IInventory crafting)
    {
        ItemStack item = null;
        for (int i = 0; i < crafting.getSizeInventory(); i++)
        {
            ItemStack itemStack = crafting.getStackInSlot(i);
            if (itemStack == null)
            {
                continue;
            }
            if (AugmentRegistry.getAugment(itemStack) != null)
            {
                if (item == null)
                {
                    item = itemStack;
                } else
                {
                    return null;
                }
            }
        }
        return item;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting crafting)
    {
        ItemStack augment = getAugmentable(crafting);
        if (augment == null)
        {
            return null;
        }
        ItemStack item = getItem(crafting);
        if (item == null)
        {
            return null;
        }
        ItemStack result = augment.copy();
        ((IAugmentedItem) result.getItem()).setAugment(result, item);
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
