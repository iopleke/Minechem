package minechem.oredictionary;

import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerRecipe;

import java.util.Arrays;
import java.util.List;

public class OreDictionaryMekanismHandler implements OreDictionaryHandler
{

	private static final int OSNIUM = 0;
	private static final int COPPER = 1;
	private static final int TIN = 2;
	private static final int BRONZE = 3;
	private static final int STEEL = 4;

	private String[] blockTypes = new String[]
	{
		"OsniumOre", "CopperOre", "TinOre", "Bronze", "Steel"
	};

	private List<String> blockList = Arrays.asList(blockTypes);

	@Override
	public boolean canHandle(String oreName)
	{
		return blockList.contains(oreName);
	}

	@Override
	public void handle(String oreName)
	{
		switch (blockList.indexOf(oreName))
		{
			case OSNIUM
				DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
				{
					new Element(ElementEnum.Os, 16)
				});
			case COPPER
				DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
				{
					new Element(ElementEnum.Cu, 16)
				});
			case TIN
				DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
				{
					new Element(ElementEnum.Sn, 16)
				});
			case BRONZE
				DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
				{
					new Element(ElementEnum.Cu, 12), new Element(ElementEnum.Sn, 4)
				});
			case STEEL
				DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[]
				{
					new Element(ElementEnum.Fe, 14), new Element(ElementEnum.C, 2)
				});
		}
	}

}
