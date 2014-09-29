package minechem.utils;

import minechem.tileentity.decomposer.DecomposerRecipe;
import net.minecraft.item.ItemStack;

public class Recipe {
	public ItemStack output;
	public ItemStack[] inStacks;
	
	public Recipe(ItemStack outStack, ItemStack[] components)
	{
		output=outStack;
		inStacks=components;
	}
	
	public int getOutStackSize()
	{
		return output.stackSize;
	}
	
	public String getKey()
	{
		return DecomposerRecipe.getKey(output);
	}
}
