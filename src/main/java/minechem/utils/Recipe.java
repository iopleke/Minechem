package minechem.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
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

public class Recipe
{
	public static Map<String, Recipe> recipes;

	public static Map<ItemStack, ItemStack> smelting;
	public static Map<String, String> oreDictionary;
	public ItemStack output;
	public ItemStack[] inStacks;

	@SuppressWarnings("unchecked")
	public static void init()
	{
		recipes = new Hashtable<String, Recipe>();
		smelting = FurnaceRecipes.smelting().getSmeltingList();
		oreDictionary = new Hashtable<String, String>();

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
					} else if (recipe instanceof ShapedOreRecipe)
					{
						ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
						for (Object o : ((ShapedOreRecipe) recipe).getInput())
						{

							if (o instanceof ItemStack)
							{
								inputs.add((ItemStack) o);
							} else if (o instanceof String)
							{
								inputs.add(OreDictionary.getOres((String) o).get(0));
							} else if (o instanceof ArrayList && !((ArrayList) o).isEmpty())
							{
								//TODO: pick the most basic results out of oredict - I am not sure if vanilla is always listed first
								inputs.add((ItemStack) ((ArrayList) o).get(0));
							}
						}
						components = inputs.toArray(new ItemStack[inputs.size()]);

					} else if (recipe instanceof ShapelessRecipes && ((ShapelessRecipes) recipe).recipeItems.toArray() instanceof ItemStack[])
					{
						components = (ItemStack[]) ((ShapelessRecipes) recipe).recipeItems.toArray();
					} else if (recipe instanceof ShapedRecipes && ((ShapedRecipes) recipe).recipeItems instanceof ItemStack[])
					{
						components = ((ShapedRecipes) recipe).recipeItems;
					}

					String key = DecomposerRecipe.getKey(input);
					if (components != null && key != null)
					{
						boolean badRecipe = false;
						for (ItemStack component : components)
						{
							if (component != null && component.getItem() != null)
							{
								if (component.isItemEqual(input) && component.getItemDamage() == input.getItemDamage())
								{
									badRecipe = true;
								}
							}
						}
						Recipe currRecipe = recipes.get(key);
						if ((currRecipe == null || input.stackSize < currRecipe.getOutStackSize()) && !badRecipe)
						{
							recipes.put(key, new Recipe(input, components));
						}
					}
				}
			}
		}
		for (ItemStack input : smelting.keySet())
		{
			String key = DecomposerRecipe.getKey(input);
			if (key != null)
			{
				Recipe currRecipe = recipes.get(key);
				if ((currRecipe == null || input.stackSize < currRecipe.getOutStackSize()))
				{
					recipes.put(key, new Recipe(input, new ItemStack[]
					{
						smelting.get(DecomposerRecipe.getKey(input))
					}));
				}
			}
		}
		for (String name : OreDictionary.getOreNames())
		{
			ArrayList<ItemStack> oreDictStacks = OreDictionary.getOres(name);
			for (ItemStack thisStack : oreDictStacks)
			{
				String key = getKey(thisStack);
				if (key != null && DecomposerRecipe.get(key) != null)
				{
					for (ItemStack dictStack : oreDictStacks)
					{
						if (!dictStack.equals(thisStack))
						{
							String fromKey = getKey(dictStack);
							if (fromKey != null)
							{
								oreDictionary.put(fromKey, key);
							}
						}
					}
				}
			}
		}
	}

	public Recipe(ItemStack outStack, ItemStack[] componentsParam)
	{
		output = outStack;
		ItemStack[] components = new ItemStack[componentsParam.length];
		int i = 0;
		for (ItemStack itemStack : componentsParam)
		{
			if (itemStack != null && itemStack.getItem() != null)
			{
				if (itemStack.getItemDamage() == Short.MAX_VALUE)
				{
					components[i] = new ItemStack(itemStack.getItem(), itemStack.stackSize, 0);
				} else
				{
					components[i] = new ItemStack(itemStack.getItem(), itemStack.stackSize, itemStack.getItemDamage());
				}
			} else
			{
				components[i] = null;
			}
			i++;
		}
		inStacks = components;
	}

	public int getOutStackSize()
	{
		return output.stackSize;
	}

	public static String getKey(ItemStack output)
	{
		if (output != null)
		{
			ItemStack result = output.copy();
			result.stackSize = 1;
			if (result.getItemDamage() == Short.MAX_VALUE)
			{
				result.setItemDamage(0);
			}
			if (result.toString().contains("null"))
			{
				return result.stackSize + "x" + result.getItem().getUnlocalizedName(result) + "@" + result.getItemDamage();
			}
			return result.toString();
		}
		return null;
	}

	public static Recipe get(ItemStack output)
	{
		if (output != null)
		{
			if (output.getItem() != null)
			{
				String key = getKey(output);
				if (key != null)
				{
					return get(key);
				}
			}
		}
		return null;
	}

	public static Recipe get(String string)
	{
		return recipes.get(string);
	}

	public String getKey()
	{
		ItemStack result = output.copy();
		result.stackSize = 1;
		if (result.getItemDamage() == Short.MAX_VALUE)
		{
			result.setItemDamage(0);
		}
		if (result.toString().contains("null"))
		{
			return result.stackSize + "x" + result.getItem().getUnlocalizedName(result) + "@" + result.getItemDamage();
		}
		return result.toString();
	}

}
