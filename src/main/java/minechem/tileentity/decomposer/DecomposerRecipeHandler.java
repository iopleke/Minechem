package minechem.tileentity.decomposer;

import java.util.ArrayList;

import minechem.potion.PotionChemical;
import minechem.utils.MinechemHelper;
import minechem.utils.Compare;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class DecomposerRecipeHandler
{
    public static DecomposerRecipeHandler instance = new DecomposerRecipeHandler();

    private DecomposerRecipeHandler()
    {

    }

    public DecomposerRecipe getRecipe(ItemStack input)
    {
        return this.getRecipe(input, 0);
    }

    public DecomposerRecipe getRecipe(ItemStack input, int level)
    {
        DecomposerRecipe decomprecipe = DecomposerRecipe.get(input);
        if (decomprecipe!=null){ 
        	return decomprecipe;
        }
        
        if (level < 5)
        {
            ArrayList<PotionChemical> output = new ArrayList<PotionChemical>();
            int foundRecipies = 0;
            for (Object recipe : CraftingManager.getInstance().getRecipeList())
            {

                if (recipe instanceof IRecipe)
                {

                    if (((IRecipe) recipe).getRecipeOutput() != null && ((IRecipe) recipe).getRecipeOutput().isItemEqual(input))
                    {

                        ItemStack[] components = null;

                        if (recipe instanceof ShapelessOreRecipe && ((ShapelessOreRecipe) recipe).getInput().size() > 0)
                        {
                            ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
                            for (Object o : ((ShapelessOreRecipe) recipe).getInput())
                            {
                                if (o instanceof ItemStack)
                                {
                                    inputs.add((ItemStack) o);
                                }
                            }
                            components = inputs.toArray(new ItemStack[inputs.size()]);
                        }
                        if (recipe instanceof ShapedOreRecipe)
                        {
                            ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
                            for (Object o : ((ShapedOreRecipe) recipe).getInput())
                            {
                                if (o instanceof ItemStack)
                                {
                                    inputs.add((ItemStack) o);
                                }
                                else if (o instanceof String)
                                {
                                    inputs.add(OreDictionary.getOres((String) o).get(0));
                                }
                                else if (o instanceof ArrayList && !((ArrayList) o).isEmpty())
                                {
                                    inputs.add((ItemStack) ((ArrayList) o).get(0));
                                }
                            }
                            components = inputs.toArray(new ItemStack[inputs.size()]);
                        }
                        if (recipe instanceof ShapelessRecipes && ((ShapelessRecipes) recipe).recipeItems.toArray() instanceof ItemStack[])
                        {
                            components = (ItemStack[]) ((ShapelessRecipes) recipe).recipeItems.toArray();
                        }
                        if (recipe instanceof ShapedRecipes && ((ShapedRecipes) recipe).recipeItems instanceof ItemStack[])
                        {
                            components = ((ShapedRecipes) recipe).recipeItems;
                        }

                        if (components != null)
                        {
                            ArrayList<PotionChemical> sum = new ArrayList<PotionChemical>();
                            for (ItemStack item : components)
                            {
                                if (item != null)
                                {
                                    DecomposerRecipe decompRecipe = this.getRecipe(item, level + 1);
                                    if (decompRecipe != null && !(decompRecipe instanceof DecomposerRecipeSelect))
                                    {
                                        sum.addAll(decompRecipe.getPartialOutputRaw(((IRecipe) recipe).getRecipeOutput().stackSize));
                                    }
                                }

                            }
                            if (!sum.isEmpty())
                            {
                                if (sum.containsAll(output))
                                {
                                    output = sum;
                                }
                            }
                        }

                    }
                }
            }

            if (!output.isEmpty())
            {
                return new DecomposerRecipe(input, output.toArray(new PotionChemical[output.size()]));
            }
        }

        return null;
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
//        DecomposerFluidRecipe fluidRecipe = null;
//        for (DecomposerRecipe recipe : DecomposerRecipe.hashRecipes.values())
//        {
//            if (recipe instanceof DecomposerFluidRecipe && input.isFluidEqual(((DecomposerFluidRecipe) recipe).inputFluid))
//            {
//                fluidRecipe = (DecomposerFluidRecipe) recipe;
//            }
//        }
    	DecomposerFluidRecipe fluidRecipe = (DecomposerFluidRecipe)DecomposerRecipe.get(input);
        if (fluidRecipe != null)
        {
            ArrayList<ItemStack> stacks = MinechemHelper.convertChemicalsIntoItemStacks(fluidRecipe.getOutput());
            return stacks;
        }
        return null;
    }

}
