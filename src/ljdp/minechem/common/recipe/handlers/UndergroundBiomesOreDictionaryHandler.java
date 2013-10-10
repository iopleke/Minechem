package ljdp.minechem.common.recipe.handlers;

import java.util.Arrays;
import java.util.logging.Level;

import ljdp.minechem.api.core.Chemical;
import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.api.core.Molecule;
import ljdp.minechem.api.recipe.DecomposerRecipe;
import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.recipe.OreDictionaryHandler;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

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
		for (EnumOre ore : EnumOre.values()) {
	        if (ore.name().equals(event.Name)) {
	            return true;
	        }
	    }
		return false;
	}

	@Override
	public void handle(OreRegisterEvent event) {
		EnumOre ore = EnumOre.valueOf(event.Name);
		switch (ore) {
		case stoneGraniteBlack:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] { new Molecule(EnumMolecule.quartz, 20),
							new Molecule(EnumMolecule.plagioclaseAlbite, 4),
							new Molecule(EnumMolecule.plagioclaseAnorthite, 4),
							new Molecule(EnumMolecule.orthoclase, 3),
							new Molecule(EnumMolecule.biotite, 1) }));
			break;
		case stoneGraniteRed:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] { new Molecule(EnumMolecule.quartz, 20),
							new Molecule(EnumMolecule.plagioclaseAlbite, 3),
							new Molecule(EnumMolecule.plagioclaseAnorthite, 3),
							new Molecule(EnumMolecule.orthoclase, 4),
							new Molecule(EnumMolecule.biotite, 1),
							new Molecule(EnumMolecule.whitePigment, 1) }));
			break;
		case stoneRhyolite:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] { new Molecule(EnumMolecule.quartz, 18),
							new Molecule(EnumMolecule.plagioclaseAlbite, 5),
							new Molecule(EnumMolecule.plagioclaseAnorthite, 5),
							new Molecule(EnumMolecule.orthoclase, 2),
							new Molecule(EnumMolecule.biotite, 1),
							new Molecule(EnumMolecule.augite, 1) }));
			break;
		case stoneAndesite:
			DecomposerRecipe
					.add(new DecomposerRecipe(
							event.Ore,
							new Chemical[] {
									new Molecule(
											EnumMolecule.plagioclaseAlbite, 12),
									new Molecule(
											EnumMolecule.plagioclaseAnorthite,
											12),
									new Molecule(EnumMolecule.augite, 8) }));
			break;
		case stoneGabbro:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] {
							new Molecule(EnumMolecule.plagioclaseAlbite, 16),
							new Molecule(EnumMolecule.plagioclaseAnorthite, 8),
							new Molecule(EnumMolecule.augite, 7),
							new Molecule(EnumMolecule.olivine, 1) }));
			break;
		case stoneBasalt:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] {
							new Molecule(EnumMolecule.plagioclaseAlbite, 8),
							new Molecule(EnumMolecule.plagioclaseAnorthite, 8),
							new Molecule(EnumMolecule.augite, 12),
							new Molecule(EnumMolecule.olivine, 4) }));
			break;
		case stoneKomatiite:
			// TODO: find actual components (this is c/p from basalt)
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] {
							new Molecule(EnumMolecule.plagioclaseAlbite, 8),
							new Molecule(EnumMolecule.plagioclaseAnorthite, 8),
							new Molecule(EnumMolecule.augite, 12),
							new Molecule(EnumMolecule.olivine, 4) }));
			break;
		case stoneDacite:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] {
							new Molecule(EnumMolecule.plagioclaseAlbite, 6),
							new Molecule(EnumMolecule.plagioclaseAnorthite, 6),
							new Molecule(EnumMolecule.biotite, 8),
							new Molecule(EnumMolecule.quartz, 8),
							new Molecule(EnumMolecule.augite, 4) }));
			break;
		case stoneGneiss:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] { new Molecule(EnumMolecule.orthoclase, 8),
							new Molecule(EnumMolecule.plagioclaseAlbite, 4),
							new Molecule(EnumMolecule.plagioclaseAnorthite, 6),
							new Molecule(EnumMolecule.biotite, 4),
							new Molecule(EnumMolecule.quartz, 6),
							new Molecule(EnumMolecule.augite, 4) }));
			break;
		case stoneEclogite:
			// TODO: find actual components (this is c/p from basalt)
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] {
							new Molecule(EnumMolecule.plagioclaseAlbite, 8),
							new Molecule(EnumMolecule.plagioclaseAnorthite, 8),
							new Molecule(EnumMolecule.augite, 12),
							new Molecule(EnumMolecule.olivine, 4) }));
			break;
		case stoneMarble:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] { new Molecule(EnumMolecule.calcite, 32) }));
			break;
		case stoneQuartzite:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] { new Molecule(EnumMolecule.quartz, 32) }));
			break;
		case stoneGreenschist:
		case stoneBlueschist:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] {
							new Molecule(EnumMolecule.plagioclaseAlbite, 8),
							new Molecule(EnumMolecule.plagioclaseAnorthite, 8),
							new Molecule(EnumMolecule.biotite, 4),
							new Molecule(EnumMolecule.quartz, 8),
							new Molecule(EnumMolecule.augite, 4) }));
			break;
		case stoneSoapstone:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] { new Molecule(EnumMolecule.talc, 32) }));
			break;
		default:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore,
					new Chemical[] { new Molecule(EnumMolecule.quartz, 8),
							new Molecule(EnumMolecule.plagioclaseAnorthite, 8),
							new Molecule(EnumMolecule.plagioclaseAlbite, 8),
							new Molecule(EnumMolecule.orthoclase, 8) }));
			break;
		}
	}

}
