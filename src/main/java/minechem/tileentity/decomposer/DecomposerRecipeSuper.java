package minechem.tileentity.decomposer;

import minechem.Settings;
import minechem.potion.PotionChemical;
import minechem.utils.LogHelper;
import minechem.utils.MinechemHelper;
import minechem.utils.Recipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class DecomposerRecipeSuper extends DecomposerRecipe
{
	static Random random = new Random();
	public Map<DecomposerRecipeSelect, Double> selectRecipes = new Hashtable<DecomposerRecipeSelect, Double>();

	public DecomposerRecipeSuper(ItemStack input, ItemStack[] components, int level)
	{
		this.input = input;
		this.input.stackSize=Math.max(1, this.input.stackSize);

        LogHelper.debug(input.toString());
		for (ItemStack component : components)
		{
			if (component != null && component.getItem() != null)
			{
				DecomposerRecipe decompRecipe = DecomposerRecipe.get(component);
				if (decompRecipe != null)
				{
					// TODO:Fix scale
					//decompRecipe.scaleOutput(input.stackSize);
					if (decompRecipe instanceof DecomposerRecipeSelect)
					{
						addSelectRecipe((DecomposerRecipeSelect) decompRecipe, 1.0/this.input.stackSize);
					} else if (decompRecipe instanceof DecomposerRecipeSuper)
					{
						addDecompRecipeSuper((DecomposerRecipeSuper) decompRecipe, 1.0/this.input.stackSize);
					} else if (decompRecipe instanceof DecomposerRecipeChance)
					{
						addSelectRecipe(new DecomposerRecipeSelect(decompRecipe.getInput(), ((DecomposerRecipeChance) decompRecipe).getChance(), new DecomposerRecipe(decompRecipe.getInput(), decompRecipe.getOutputRaw())), 1.0/this.input.stackSize);
					} else
					{
						addPotionChemical(decompRecipe.getOutput(),1.0/this.input.stackSize);
					}
				} else if (!component.isItemEqual(input) || !(component.getItemDamage() == input.getItemDamage()))
				{
					//Recursively generate recipe
					Recipe recipe = Recipe.get(component);
					if (recipe != null && level < Settings.recursiveDepth)
					{
						DecomposerRecipeSuper newSuper;
						DecomposerRecipe.add(newSuper = new DecomposerRecipeSuper(recipe.output, recipe.inStacks, level + 1));
						addDecompRecipeSuper(newSuper, 1.0/this.input.stackSize);
					}
				}
			}
		}
	}

	public DecomposerRecipeSuper(ItemStack input, ItemStack[] components)
	{
		this(input, components, 0);
	}
	
	public DecomposerRecipeSuper(ItemStack input, ItemStack[] components, ArrayList<PotionChemical> chemicals)
	{
		this(input, components, 0);
		addPotionChemical(chemicals,1);
	}

	private void addDecompRecipeSuper(DecomposerRecipeSuper recipeSuper, double amount)
	{
		addPotionChemical(recipeSuper.getGuaranteedOutput(),amount);
		Map<DecomposerRecipeSelect, Double> newSelectRecipes = recipeSuper.getSelectRecipes();
		for (DecomposerRecipeSelect recipeSelect : newSelectRecipes.keySet())
		{
			addSelectRecipe(recipeSelect, newSelectRecipes.get(recipeSelect) * amount);
		}
	}

	private void addPotionChemical(ArrayList<PotionChemical> out, double amount)
	{
		if (out != null)
		{
			for (PotionChemical add : out)
			{
				PotionChemical addChem = add.copy();
				addChem.amount*=amount;
				super.addChemicals(add);
			}
		}
	}

	private void addSelectRecipe(DecomposerRecipeSelect recipe, double number)
	{
		Double current = this.selectRecipes.put(recipe, number);
		if (current != null)
		{
			this.selectRecipes.put(recipe, current + number);
		}
	}

	@Override
	public ArrayList<PotionChemical> getOutput()
	{
		ArrayList<PotionChemical> result = super.getOutput();
		for (DecomposerRecipeSelect current : this.selectRecipes.keySet())
		{
			int i;
			for (i=0; i < this.selectRecipes.get(current); i++)
			{
				ArrayList<PotionChemical> partialResult = current.getOutput();
				if (partialResult != null) result.addAll(partialResult);
			}
			double chance = this.selectRecipes.get(current)-i;
			if (random.nextDouble()<chance)
			{
				ArrayList<PotionChemical> partialResult = current.getOutput();
				if (partialResult != null) result.addAll(partialResult);
			}
		}
		return result;
	}

	@Override
	public ArrayList<PotionChemical> getOutputRaw()
	{
		ArrayList<PotionChemical> result = super.getOutputRaw();
		for (DecomposerRecipeSelect current : this.selectRecipes.keySet())
		{
			for (int i = 0; i < this.selectRecipes.get(current); i++)
			{
                ArrayList<PotionChemical> partialResult = current.getOutputRaw();
                partialResult = MinechemHelper.pushTogetherChemicals(partialResult);
                if (partialResult != null) result.addAll(partialResult);
			}
		}
		return MinechemHelper.pushTogetherChemicals(result);
	}

	public ArrayList<PotionChemical> getGuaranteedOutput()
	{
		return super.getOutput();
	}

	public Map<DecomposerRecipeSelect, Double> getSelectRecipes()
	{
		return this.selectRecipes;
	}

	@Override
	public boolean isNull()
	{
		if (super.getOutput() == null && this.selectRecipes == null)
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
		return !this.selectRecipes.values().isEmpty() || !this.output.isEmpty();
	}

	@Override
	public boolean outputContains(PotionChemical potionChemical)
	{
		boolean contains;
		contains = super.outputContains(potionChemical);
		if (!contains)
		{
			for (DecomposerRecipeSelect dr : selectRecipes.keySet())
			{
				contains = dr.outputContains(potionChemical);
				if (contains)
				{
					break;
				}
			}
		}
		return contains;
	}

	public float getChance()
	{
		double count = 0;
		float chances = 0;
		for (Map.Entry<DecomposerRecipeSelect, Double> entry : selectRecipes.entrySet())
		{
			chances += entry.getKey().getChance();
			count += entry.getValue();
		}
		return chances / (float)count;
	}

	@Override
	public void scaleOutput(float scale)
	{
		super.scaleOutput(scale);
		for (DecomposerRecipe recipe : selectRecipes.keySet())
		{
			recipe.scaleOutput(scale);
		}
	}
}
