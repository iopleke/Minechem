package minechem.tileentity.decomposer;

import minechem.potion.PotionChemical;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class DecomposerRecipeChance extends DecomposerRecipe
{

	static Random random = new Random();
	float chance;

	public DecomposerRecipeChance(ItemStack input, float chance, PotionChemical... output)
	{
		super(input, output);
		this.chance = chance;
	}

	@Override
	public ArrayList<PotionChemical> getOutput()
	{
		if (random.nextFloat() < this.chance)
		{
			return super.getOutput();
		} else
		{
			return null;
		}
	}

    @Override
	public float getChance()
	{
		return chance;
	}

}
