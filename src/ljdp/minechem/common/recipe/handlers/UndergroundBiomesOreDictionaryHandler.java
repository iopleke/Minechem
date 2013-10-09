package ljdp.minechem.common.recipe.handlers;

import java.util.Arrays;

import cpw.mods.fml.common.Loader;

import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;
import ljdp.minechem.api.core.Chemical;
import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.api.core.Molecule;
import ljdp.minechem.api.recipe.DecomposerRecipe;
import ljdp.minechem.api.recipe.SynthesisRecipe;
import ljdp.minechem.common.recipe.OreDictionaryHandler;

public class UndergroundBiomesOreDictionaryHandler implements
		OreDictionaryHandler {

	private enum EnumOre {
		stoneGraniteBlack, 
		stoneGraniteRed, 
		stoneRhyolite, 
		stoneAndesite, 
		stoneGabbro, 
		stoneBasalt, 
		stoneKomatiite, 
		stoneDacite, 
		stoneGneiss, 
		stoneEclogite, 
		stoneMarble, 
		stoneQuartzite, 
		stoneBlueschist, 
		stoneGreenschist, 
		stoneSoapstone, 
		stoneMigmatite, 
		stoneLimestone, 
		stoneChalk, 
		stoneShale, 
		stoneSiltstone, 
		stoneLignite, 
		stoneDolomite, 
		stoneGreywacke, 
		stoneChert
	}

	@Override
	public boolean canHandle(OreRegisterEvent event) {
		if (Arrays.asList(EnumOre.values()).contains(event.Name))
			return true;
		return false;
	}

	@Override
	public void handle(OreRegisterEvent event) {
		EnumOre ore = EnumOre.valueOf(event.Name);
		switch (ore) {
		case stoneGraniteBlack:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] {
							new Molecule(EnumMolecule.siliconDioxide, 24),
							new Molecule(EnumMolecule.aluminiumOxide, 6),
							new Molecule(EnumMolecule.potassiumOxide, 1),
							new Molecule(EnumMolecule.sodiumOxide, 1) }));
			break;
		case stoneGraniteRed:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] {
							new Molecule(EnumMolecule.siliconDioxide, 20),
							new Molecule(EnumMolecule.aluminiumOxide, 6),
							new Molecule(EnumMolecule.potassiumOxide, 2),
							new Molecule(EnumMolecule.sodiumOxide, 1),
							new Molecule(EnumMolecule.whitePigment, 1) }));
			break;
		default:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] {
							new Molecule(EnumMolecule.siliconDioxide, 24),
							new Molecule(EnumMolecule.aluminiumOxide, 8) }));
			break;
		}
	}

}
