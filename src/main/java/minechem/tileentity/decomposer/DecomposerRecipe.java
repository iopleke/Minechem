package minechem.tileentity.decomposer;

import java.util.ArrayList;
import java.util.Random;
import minechem.Minechem;

import minechem.Settings;
import minechem.potion.PotionChemical;
import minechem.utils.Compare;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DecomposerRecipe
{

	public static ArrayList<DecomposerRecipe> recipes = new ArrayList<DecomposerRecipe>();

	ItemStack input;
	public ArrayList<PotionChemical> output = new ArrayList<PotionChemical>();

	//TODO:Add blacklist support for fluids
	public static DecomposerRecipe add(DecomposerRecipe recipe)
	{
		if (recipe.input != null)
		{
			for (int i = 0; i < Settings.DecomposerBlacklist.length; i++)
			{
                if (recipe.input.hasDisplayName())
                {
                    if (Compare.stringSieve(recipe.input.getDisplayName()).compareTo(Compare.stringSieve(Settings.DecomposerBlacklist[i])) == 0)
                    {
                        if (Settings.DebugMode)
                        {
                            Minechem.LOGGER.info("Decomposer recipe for '" + Settings.DecomposerBlacklist[i] + "' has been blacklisted");
                        }
                        return null;
                    }
                }
			}
		}
		recipes.add(recipe);
		return recipe;
	}

	public static void createAndAddRecipeSafely(String item, int amount, PotionChemical... chemicals)
	{
		for (ItemStack i : OreDictionary.getOres(item))
		{
			DecomposerRecipe.add(new DecomposerRecipe(new ItemStack(i.getItem(), amount, i.getItemDamage()), chemicals));
		}
	}

	public DecomposerRecipe(ItemStack input, PotionChemical... chemicals)
	{
		this(chemicals);
		this.input = input;
	}

	public DecomposerRecipe(PotionChemical... chemicals)
	{
		for (PotionChemical potionChemical : chemicals)
		{
			this.output.add(potionChemical);
		}
	}

	public ItemStack getInput()
	{
		return this.input;
	}

	public ArrayList<PotionChemical> getOutput()
	{
		return this.output;
	}

	public ArrayList<PotionChemical> getOutputRaw()
	{
		return this.output;
	}

	public ArrayList<PotionChemical> getPartialOutputRaw(int f)
	{
		ArrayList<PotionChemical> raw = getOutput();
		ArrayList<PotionChemical> result = new ArrayList<PotionChemical>();
		if (raw != null)
		{
			for (PotionChemical chem : raw)
			{
				try
				{
					if (chem != null)
					{
						PotionChemical reduced = chem.copy();
						if (reduced != null)
						{
							reduced.amount = (int) Math.floor(chem.amount / f);
							Random rand = new Random();
							if (reduced.amount == 0 && rand.nextFloat() > (chem.amount / f))
							{
								reduced.amount = 1;
							}
							result.add(reduced);
						}
					}

				} catch (Exception e)
				{
					// something has gone wrong
					// but we do not know quite why
					// debug code goes here
				}

			}
		}

		return result;
	}
}
