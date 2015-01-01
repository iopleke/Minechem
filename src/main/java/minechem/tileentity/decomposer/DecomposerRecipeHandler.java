package minechem.tileentity.decomposer;

import java.util.ArrayList;
import minechem.utils.MapKey;
import minechem.utils.MinechemUtil;
import minechem.utils.Recipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class DecomposerRecipeHandler
{
    public static DecomposerRecipeHandler instance = new DecomposerRecipeHandler();

    private DecomposerRecipeHandler()
    {

    }

    public static void recursiveRecipes()
    {
        for (MapKey key : Recipe.recipes.keySet())
        {
            if (!DecomposerRecipe.recipes.containsKey(key))
            {
                Recipe recipe = Recipe.get(key);
                DecomposerRecipe.add(new DecomposerRecipeSuper(recipe.output, recipe.inStacks));
            }
        }
        //Culls null recipes (used for recursion but breaks stuff if left in)
        for (MapKey key : DecomposerRecipe.recipes.keySet())
        {
            if (DecomposerRecipe.get(key).isNull())
            {
                DecomposerRecipe.remove(key);
            }
        }
    }

    public static void resetRecursiveRecipes()
    {
        for (MapKey key : DecomposerRecipe.recipes.keySet())
        {
            if (DecomposerRecipe.get(key) instanceof DecomposerRecipeSuper)
            {
                DecomposerRecipe.remove(key);
            }
        }
        Recipe.init();
        recursiveRecipes();
    }

    public DecomposerRecipe getRecipe(ItemStack input)
    {
        return DecomposerRecipe.get(input);
    }

    public DecomposerRecipe getRecipe(FluidStack fluidStack)
    {
        return DecomposerRecipe.get(fluidStack);
    }

    public ArrayList<ItemStack> getRecipeOutputForInput(ItemStack input)
    {
        DecomposerRecipe recipe = getRecipe(input);
        if (recipe != null)
        {
            return MinechemUtil.convertChemicalsIntoItemStacks(recipe.getOutput());
        }
        return null;
    }

    public ArrayList<ItemStack> getRecipeOutputForFluidInput(FluidStack input)
    {
        DecomposerFluidRecipe fluidRecipe = (DecomposerFluidRecipe) DecomposerRecipe.get(input);
        if (fluidRecipe != null)
        {
            return MinechemUtil.convertChemicalsIntoItemStacks(fluidRecipe.getOutput());
        }
        return null;
    }

}
