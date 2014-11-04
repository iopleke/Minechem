package minechem.tileentity.decomposer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import minechem.Settings;
import minechem.potion.PotionChemical;
import minechem.utils.LogHelper;
import minechem.utils.MinechemHelper;
import minechem.utils.Recipe;
import net.minecraft.item.ItemStack;

public class DecomposerRecipeSuper extends DecomposerRecipe
{

	static Random random = new Random();
	public Map<String, Double> recipes = new Hashtable<String, Double>();

	public DecomposerRecipeSuper(ItemStack input, ItemStack[] components, int level)
	{
		this.input = input.copy();
		this.input.stackSize = 1;

		LogHelper.debug(input.toString());
		for (ItemStack component : components)
		{
			if (component != null && component.getItem() != null)
			{
				DecomposerRecipe decompRecipe = DecomposerRecipe.get(component);
				if (decompRecipe != null)
				{
					addDecompRecipe(decompRecipe, 1.0 / input.stackSize);
				} else if (!component.isItemEqual(input) || !(component.getItemDamage() == input.getItemDamage()))
				{
					//Recursively generate recipe
					Recipe recipe = Recipe.get(component);
					if (recipe != null && level < Settings.recursiveDepth)
					{
						DecomposerRecipeSuper newSuper;
						DecomposerRecipe.add(newSuper = new DecomposerRecipeSuper(recipe.output, recipe.inStacks, level + 1));
						addDecompRecipe(newSuper, 1.0 / recipe.getOutStackSize());
					}
				}
			}
		}
	}

	private void addDecompRecipe(DecomposerRecipe decompRecipe, double d)
	{
		String key = DecomposerRecipe.getKey(decompRecipe.input);
		Double current = recipes.put(key, d);
		if (current != null)
		{
			recipes.put(key, d + current);
		}
	}

	public DecomposerRecipeSuper(ItemStack input, ItemStack[] components)
	{
		this(input, components, 0);
	}

	public DecomposerRecipeSuper(ItemStack input, ItemStack[] components, ArrayList<PotionChemical> chemicals)
	{
		this(input, components, 0);
		addPotionChemical(chemicals);
	}

	private void addPotionChemical(ArrayList<PotionChemical> out)
	{
		if (out != null)
		{
			for (PotionChemical add : out)
			{
				super.addChemicals(add);
			}
		}
	}

	@Override
	public ArrayList<PotionChemical> getOutput()
	{
		ArrayList<PotionChemical> result = super.getOutput();
		for (String currentKey : this.recipes.keySet())
		{
			DecomposerRecipe current = DecomposerRecipe.get(currentKey);
			if (current != null)
			{
				Double i = this.recipes.get(currentKey);
                LogHelper.debug("getOutput :" + currentKey + " chance: " + i);
				while (i >= 1)
				{
					ArrayList<PotionChemical> partialResult = current.getOutput();
					if (partialResult != null)
					{
						result.addAll(partialResult);
					}
					i--;
				}
				if (random.nextDouble() < i)
				{
					ArrayList<PotionChemical> partialResult = current.getOutput();
					if (partialResult != null)
					{
						result.addAll(partialResult);
					}
				}
			}
		}
		return result;
	}

	@Override
	public ArrayList<PotionChemical> getOutputRaw()
	{
		ArrayList<PotionChemical> result = super.getOutputRaw();
		for (String currentKey : this.recipes.keySet())
		{
			DecomposerRecipe current = DecomposerRecipe.get(currentKey);
			LogHelper.debug("getOutputRaw: " + currentKey);
			if (current != null)
			{
				for (int i = 0; i < this.recipes.get(currentKey); i++)
				{
					ArrayList<PotionChemical> partialResult = current.getOutputRaw();
					partialResult = MinechemHelper.pushTogetherChemicals(partialResult);
					if (partialResult != null)
					{
						result.addAll(partialResult);
					}
				}
			}
		}
		return MinechemHelper.pushTogetherChemicals(result);
	}

	public ArrayList<PotionChemical> getGuaranteedOutput()
	{
		return super.getOutput();
	}

	@Override
	public boolean isNull()
	{
		if (super.getOutput() == null && this.recipes == null)
		{
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<PotionChemical> getPartialOutputRaw(int f)
	{
		return super.getPartialOutputRaw(f);
	}

	@Override
	public boolean hasOutput()
	{
		return !this.recipes.values().isEmpty() || !this.output.isEmpty();
	}

	@Override
	public boolean outputContains(PotionChemical potionChemical)
	{
		boolean contains;
		contains = super.outputContains(potionChemical);
		if (!contains)
		{
			for (String key : recipes.keySet())
			{
				DecomposerRecipe dr = DecomposerRecipe.get(key);
                LogHelper.debug("outputContains: " + key);
				if (dr == null)
				{
					continue;
				}
				contains = dr.outputContains(potionChemical);
				if (contains)
				{
					break;
				}
			}
		}
		return contains;
	}

	@Override
	public float getChance()
	{
		float chances = 1;
		for (Map.Entry<String, Double> entry : recipes.entrySet())
		{
			DecomposerRecipe dr = DecomposerRecipe.get(entry.getKey());
			if (dr != null)
			{
				chances *= dr.getChance() / entry.getValue();
			}
		}
		return chances;
	}
}
