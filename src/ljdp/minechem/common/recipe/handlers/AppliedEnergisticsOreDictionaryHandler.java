package ljdp.minechem.common.recipe.handlers;

import cpw.mods.fml.common.Loader;
import ljdp.minechem.api.core.Chemical;
import ljdp.minechem.api.core.EnumMineral;
import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.api.core.Molecule;
import ljdp.minechem.api.recipe.DecomposerRecipe;
import ljdp.minechem.api.recipe.SynthesisRecipe;
import ljdp.minechem.common.recipe.OreDictionaryHandler;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class AppliedEnergisticsOreDictionaryHandler implements
		OreDictionaryHandler {

	private Molecule certusQuartzMolecule = new Molecule(
			EnumMolecule.aluminiumPhosphate, 4);

	private Chemical[] certusQuartzDecompositionFormula = EnumMineral.berlinite.getComposition();

	private Chemical[] certusQuartzCrystalSynthesisFormula = new Chemical[] {
			null, certusQuartzMolecule, null, certusQuartzMolecule, null,
			certusQuartzMolecule, null, certusQuartzMolecule, null };

	private Chemical[] certusQuartzDustSynthesisFormula = new Chemical[] {
			null, certusQuartzMolecule, null, certusQuartzMolecule,
			certusQuartzMolecule, certusQuartzMolecule, null, null, null };

	@Override
	public boolean canHandle(OreRegisterEvent oreEvent) {
		return oreEvent.Name.endsWith("CertusQuartz");
	}

	@Override
	public void handle(OreRegisterEvent oreEvent) {
		switch (oreEvent.Name) {
		case "dustCertusQuartz":
			DecomposerRecipe.add(new DecomposerRecipe(oreEvent.Ore,
					certusQuartzDecompositionFormula));
			if (Loader.isModLoaded("IC2")) {
				SynthesisRecipe.add(new SynthesisRecipe(oreEvent.Ore, true,
						20000, certusQuartzDustSynthesisFormula));
			}
			break;
		case "crystalCertusQuartz":
			DecomposerRecipe.add(new DecomposerRecipe(oreEvent.Ore,
					certusQuartzDecompositionFormula));
			if (!Loader.isModLoaded("IC2")) {
				SynthesisRecipe.add(new SynthesisRecipe(oreEvent.Ore, true,
						20000, certusQuartzCrystalSynthesisFormula));
			}
			break;
		default:
			System.err
					.println("MineChem > Applied Energistics Support > Unknown type of Certus Quartz : "
							+ oreEvent.Name);
			break;
		}
	}
}
