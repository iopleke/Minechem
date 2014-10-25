package minechem.oredictionary;

import minechem.MinechemRecipes;
import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.synthesis.SynthesisRecipe;
import minechem.utils.LogHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OreDictionaryDefaultHandler implements OreDictionaryHandler
{

	private enum EnumOrePrefix
	{
		block, oreNether, ore, ingot, nugget, dustDirty, dustSmall, dust, plate, gem, crystal
	}

	private String[] supportedOres;

	private Map<OreDictionaryBaseOreEnum, ArrayList<EnumOrePrefix>> seenOres = new HashMap<OreDictionaryBaseOreEnum, ArrayList<EnumOrePrefix>>();

	private Map<OreDictionaryBaseOreEnum, String> registeredIngots = new HashMap<OreDictionaryBaseOreEnum, String>();

	public OreDictionaryDefaultHandler()
	{
		ArrayList<String> ores = new ArrayList<String>();
		for (OreDictionaryBaseOreEnum ore : OreDictionaryBaseOreEnum.values())
		{
			ores.add(ore.name());
		}
		supportedOres = ores.toArray(new String[ores.size()]);
	}

	public String[] parseOreName(String oreName)
	{
		for (EnumOrePrefix prefix : EnumOrePrefix.values())
		{
			if (oreName.startsWith(prefix.name()))
			{
				String remainder = oreName.substring(prefix.name().length()).toLowerCase();
				if (Arrays.asList(supportedOres).contains(remainder))
				{
					return new String[]
					{
						prefix.name(), remainder
					};
				}
			}
		}

		return null;
	}

	@Override
	public boolean canHandle(String oreName)
	{
		if (this.parseOreName(oreName) != null)
		{
			return true;
		}
		return false;
	}

	@Override
	public void handle(String oreName)
	{
        LogHelper.debug(OreDictionaryDefaultHandler.class.getSimpleName() + " registered : " + oreName);

		String[] tokens = this.parseOreName(oreName);
		EnumOrePrefix prefix = EnumOrePrefix.valueOf(tokens[0]);
		OreDictionaryBaseOreEnum ore = OreDictionaryBaseOreEnum.valueOf(tokens[1]);

		switch (prefix)
		{
			case oreNether:
				DecomposerRecipe.createAndAddRecipeSafely(oreName, scaleFloor(ore.getComposition(), 6d));
				break;
			case ore:
				DecomposerRecipe.createAndAddRecipeSafely(oreName, scaleFloor(ore.getComposition(), 3d));
				break;
			case ingot:
				DecomposerRecipe.createAndAddRecipeSafely(oreName, ore.getComposition());
				if (!haveSeen(ore, EnumOrePrefix.dust) && !haveSeen(ore, EnumOrePrefix.dustSmall))
				{
					SynthesisRecipe.createAndAddRecipeSafely(oreName, false, MinechemRecipes.COST_INGOT, ore.getComposition());
					registeredIngots.put(ore, oreName);
				}
				break;

			case nugget:
				DecomposerRecipe.createAndAddRecipeSafely(oreName, scaleFloor(ore.getComposition(), 1d / 9d));
				break;
			case dust:
				DecomposerRecipe.createAndAddRecipeSafely(oreName, ore.getComposition());
				unregisterIngot(ore);
				SynthesisRecipe.createAndAddRecipeSafely(oreName, false, MinechemRecipes.COST_INGOT, ore.getComposition());
				break;
			case dustDirty:
				DecomposerRecipe.createAndAddRecipeSafely(oreName, scaleFloor(ore.getComposition(), 0.75d));
				break;
			case plate:
				DecomposerRecipe.createAndAddRecipeSafely(oreName, ore.getComposition());
				break;
			case dustSmall:
				DecomposerRecipe.createAndAddRecipeSafely(oreName, scaleFloor(ore.getComposition(), 0.25d));
				unregisterIngot(ore);
				SynthesisRecipe.createAndAddRecipeSafely(oreName, false, MinechemRecipes.COST_INGOT / 4, scaleCeil(ore.getComposition(), 0.25d));
				break;
            case crystal:
			case gem:
				DecomposerRecipe.createAndAddRecipeSafely(oreName, ore.getComposition());
				SynthesisRecipe.createAndAddRecipeSafely(oreName, false, MinechemRecipes.COST_GEM, ore.getComposition());
				break;
			default:
                LogHelper.debug(OreDictionaryDefaultHandler.class.getSimpleName() + " : Invalid ore dictionary type.");
				break;
		}

		seen(ore, prefix);
	}

	private void unregisterIngot(OreDictionaryBaseOreEnum ore)
	{
		if (registeredIngots.containsKey(ore))
		{
			SynthesisRecipe.remove(registeredIngots.get(ore));
			registeredIngots.remove(ore);
		}
	}

	private PotionChemical[] scaleCeil(PotionChemical[] composition, double factor)
	{
		ArrayList<PotionChemical> newComposition = new ArrayList<PotionChemical>();

		for (PotionChemical chem : composition)
		{
			PotionChemical newChem = chem.copy();
			newChem.amount = (int) Math.ceil(chem.amount * factor);
			newComposition.add(newChem);
		}

		return newComposition.toArray(new PotionChemical[newComposition.size()]);
	}

	private PotionChemical[] scaleFloor(PotionChemical[] composition, double factor)
	{
		ArrayList<PotionChemical> newComposition = new ArrayList<PotionChemical>();

		for (PotionChemical chem : composition)
		{
			PotionChemical newChem = chem.copy();
			newChem.amount = (int) Math.floor(chem.amount * factor);
			newComposition.add(newChem);
		}

		return newComposition.toArray(new PotionChemical[newComposition.size()]);
	}

	private boolean haveSeen(OreDictionaryBaseOreEnum ore, EnumOrePrefix prefix)
	{
		if (this.seenOres.containsKey(ore) && this.seenOres.get(ore).contains(prefix))
		{
			return true;
		}
		return false;
	}

	private void seen(OreDictionaryBaseOreEnum ore, EnumOrePrefix prefix)
	{
		if (!this.seenOres.containsKey(ore))
		{
			this.seenOres.put(ore, new ArrayList<EnumOrePrefix>());
		}
		if (!this.seenOres.get(ore).contains(prefix))
		{
			this.seenOres.get(ore).add(prefix);
		}
	}
}
