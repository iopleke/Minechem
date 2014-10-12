package minechem.tileentity.decomposer;

import java.util.ArrayList;

import minechem.Minechem;
import minechem.utils.MinechemHelper;
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
        for (String key:Recipe.recipes.keySet()){
        	if (!DecomposerRecipe.recipes.containsKey(key)&&(!(key.contains("compressed_cobblestone")))){
        		Recipe recipe = Recipe.get(key);
        		DecomposerRecipe.add(new DecomposerRecipeSuper(recipe.output,recipe.inStacks));
        	}
        }
        //Culls null recipes (used for recursion but breaks stuff if left in)
        for (String key: DecomposerRecipe.recipes.keySet())
        {
        	if (DecomposerRecipe.get(key).isNull()) DecomposerRecipe.remove(key);
        }
    }
    
    public DecomposerRecipe getRecipe(ItemStack input)
    {
        return DecomposerRecipe.get(input);
    }
    
	public DecomposerRecipe getRecipe(FluidStack fluidStack) {
		return DecomposerRecipe.get(fluidStack);
	}


    public ArrayList<ItemStack> getRecipeOutputForInput(ItemStack input)
    {
        DecomposerRecipe recipe = getRecipe(input);
        if (recipe != null)
        {
            ArrayList<ItemStack> stacks = MinechemHelper.convertChemicalsIntoItemStacks(recipe.getOutput());
            return stacks;
        }
        return null;
    }

    public ArrayList<ItemStack> getRecipeOutputForFluidInput(FluidStack input)
    {
    	DecomposerFluidRecipe fluidRecipe = (DecomposerFluidRecipe)DecomposerRecipe.get(input);
        if (fluidRecipe != null)
        {
        	
            ArrayList<ItemStack> stacks = MinechemHelper.convertChemicalsIntoItemStacks(fluidRecipe.getOutput());
            return stacks;
        }
        return null;
    }


}
