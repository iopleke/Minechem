package minechem.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import minechem.Minechem;
import minechem.tileentity.decomposer.DecomposerRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipe {
	public static Map<String,Recipe> recipes = new Hashtable<String, Recipe>();
	@SuppressWarnings("unchecked")
	public static Map<ItemStack,ItemStack> smelting = FurnaceRecipes.smelting().getSmeltingList();
	public static Map<String,String> oreDictionary;
	public ItemStack output;
	public ItemStack[] inStacks;
	
	public static void init()
	{
    	int wrongValue=Short.MAX_VALUE;
        for (Object recipe : CraftingManager.getInstance().getRecipeList())
        {
        	//Minechem.LOGGER.info(recipe);
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
                                inputs.add((ItemStack) o);
                            }
                            else if (o instanceof String)
                            {
                                inputs.add(OreDictionary.getOres((String) o).get(0));
                            }
                            else if (o instanceof ArrayList && !((ArrayList) o).isEmpty())
                            {
                            	//TODO: pick the most basic results out of oredict - I am not sure if vanilla is always listed first
                            	inputs.add((ItemStack)((ArrayList)o).get(0));
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
                    	for(int i=0;i<components.length;i++)
                    		if (components[i]!=null&&components[i].getItemDamage()==wrongValue)
                    			components[i].setItemDamage(0);
                    	Recipe currRecipe = recipes.get(input);
                    	if (currRecipe==null||input.stackSize<currRecipe.getOutStackSize()){
                    		recipes.put(DecomposerRecipe.getKey(input), new Recipe(input, components));
                    	}                        
                    }
                }
            }
        }
        for (ItemStack input:smelting.keySet())
        {
        	Recipe currRecipe = recipes.get(input);
        	if (currRecipe==null||input.stackSize<currRecipe.getOutStackSize()){
        		recipes.put(DecomposerRecipe.getKey(input), new Recipe(input, new ItemStack[]{smelting.get(input)}));
        	}
        }
        for (String name:OreDictionary.getOreNames())
        {
        	ArrayList<ItemStack> oreDictStacks = OreDictionary.getOres(name);
    		String toKey=null;
    		for (ItemStack thisStack:oreDictStacks)
    		{
    			String key = getKey(thisStack);
    			if (DecomposerRecipe.get(key)==null)
    			{
	    			if (toKey==null) toKey = key;
	    			else
	    			{
		    			String fromKey = key;
		    			if (fromKey!=null) oreDictionary.put(fromKey, toKey);
	    			}
    			}
    		}
        }
	}
	
	public Recipe(ItemStack outStack, ItemStack[] components)
	{
		output=outStack;
		inStacks=components;
	}
	
	public int getOutStackSize()
	{
		return output.stackSize;
	}
	
	public static String getKey(ItemStack output)
	{
		if (output!=null){
			ItemStack result=output.copy();
			result.stackSize=1;
			return result.toString();
		}
		return null;
	}
	
	public static Recipe get(ItemStack output)
	{
		if (output!=null)
		{
			String key = getKey(output);
			if (key!=null) return get(key);
		}
		return null;
	}
	
	public static Recipe get(String string)
	{
		return recipes.get(string);
	}
	
	public String getKey()
	{
		ItemStack result=output.copy();
		result.stackSize=1;
		return result.toString();
	}
	
}
