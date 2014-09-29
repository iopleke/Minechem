package minechem.tileentity.decomposer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import minechem.potion.PotionChemical;
import minechem.utils.MinechemHelper;
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
    public static Map<String,ItemStack[]> recipes = new Hashtable<String, ItemStack[]>();
    public static Map<String,ItemStack> outputs = new Hashtable<String, ItemStack>();
    
    private DecomposerRecipeHandler()
    {

    }
    
    public static void recursiveRecipes()
    {
        for (Object recipe : CraftingManager.getInstance().getRecipeList())
        {
            if (recipe instanceof IRecipe)
            {
                if (((IRecipe) recipe).getRecipeOutput() != null)
                {

                    ItemStack input = ((IRecipe) recipe).getRecipeOutput();
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
                    else if (recipe instanceof ShapedOreRecipe)
                    {
                        ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
                        for (Object o : ((ShapedOreRecipe) recipe).getInput())
                        {
                        	
                            if (o instanceof ItemStack)
                            {
                            	if (((ItemStack) o).getItemDamage()>500)((ItemStack) o).setItemDamage(0);
                                inputs.add((ItemStack) o);
                            }
                            else if (o instanceof String)
                            {
                                inputs.add(OreDictionary.getOres((String) o).get(0));
                            }
                            else if (o instanceof ArrayList && !((ArrayList) o).isEmpty())
                            {
                            	ItemStack q =((ItemStack) ((ArrayList) o).get(0)).copy();
                            	if (q.getItemDamage()>500)q.setItemDamage(0);
                                inputs.add(q);
                            }
                        }
                        components = inputs.toArray(new ItemStack[inputs.size()]);
                    }
                    else if (recipe instanceof ShapelessRecipes && ((ShapelessRecipes) recipe).recipeItems.toArray() instanceof ItemStack[])
                    {
                        components = (ItemStack[]) ((ShapelessRecipes) recipe).recipeItems.toArray();
                    }
                    else if (recipe instanceof ShapedRecipes && ((ShapedRecipes) recipe).recipeItems instanceof ItemStack[])
                    {
                        components = ((ShapedRecipes) recipe).recipeItems;
                    }

                    if (components != null)
                    {
                    	outputs.put(DecomposerRecipe.getKey(input), input);
                        recipes.put(DecomposerRecipe.getKey(input), components);
                    }
                }
            }
        }
        for (Object key:recipes.keySet()){
        	if (!DecomposerRecipe.recipes.containsKey(key)){
        		DecomposerRecipe result = getRecursiveRecipe((String)key,0);
        		if (result!=null){
        			DecomposerRecipe.recipes.put((String)key, result);
        		}
        	}
        }
    }
    
    public static DecomposerRecipe getRecursiveRecipe(String input, int level)
    {
        DecomposerRecipe decomprecipe = DecomposerRecipe.recipes.get(input);
        if (decomprecipe!=null){ 
        	return decomprecipe;
        }
        if (level < 20)
        {
        	ArrayList<PotionChemical> output = new ArrayList<PotionChemical>();
        	ItemStack[] components = recipes.get(input);
        	if (components != null)
            {
                ArrayList<PotionChemical> sum = new ArrayList<PotionChemical>();
                for (ItemStack item : components)
                {
                    if (item != null)
                    {
                        DecomposerRecipe decompRecipe = getRecursiveRecipe(DecomposerRecipe.getKey(item), level + 1);
                        if (decompRecipe != null && !(decompRecipe instanceof DecomposerRecipeSelect))
                        {
                            sum.addAll(decompRecipe.getPartialOutputRaw(outputs.get(input).stackSize));
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
        	 if (!output.isEmpty())
             {
                 return new DecomposerRecipe(outputs.get(input), output.toArray(new PotionChemical[output.size()]));
             }
        }
        return null;
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
