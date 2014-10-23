package minechem.oredictionary;

import minechem.Minechem;
import minechem.item.element.Element;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.synthesis.SynthesisRecipe;

public class OreDictionaryAppliedEnergisticsHandler implements OreDictionaryHandler
{

	private MoleculeEnum certusQuartzMolecule = MoleculeEnum.aluminiumPhosphate;

	private PotionChemical certusQuartzChemical = new Molecule(certusQuartzMolecule);

	private MoleculeEnum chargedCertusQuartzMolecule = MoleculeEnum.aluminumHypophosphite;

	private PotionChemical chargedCertusQuartzChemical = new Molecule(chargedCertusQuartzMolecule);

	private PotionChemical[] certusQuartzDecompositionFormula = new PotionChemical[]
	{
		new Molecule(certusQuartzMolecule, 4)
	};

	private PotionChemical[] certusQuartzCrystalSynthesisFormula = new PotionChemical[]
	{
		null, certusQuartzChemical, null, certusQuartzChemical, null, certusQuartzChemical, null, certusQuartzChemical, null
	};

	private PotionChemical[] certusQuartzDustSynthesisFormula = new PotionChemical[]
	{
		null, certusQuartzChemical, null, certusQuartzChemical, certusQuartzChemical, certusQuartzChemical, null, null, null
	};

	private PotionChemical[] chargedCertusQuartzDecompositionFormula = new PotionChemical[]
	{
		new Molecule(chargedCertusQuartzMolecule, 4)
	};

	private PotionChemical[] chargedCertusQuartzCrystalSynthesisFormula = new PotionChemical[]
	{
		null, chargedCertusQuartzChemical, null, chargedCertusQuartzChemical, null, chargedCertusQuartzChemical, null, chargedCertusQuartzChemical, null
	};

	private PotionChemical[] fluixCertusQuartzDecompositionFormula = new PotionChemical[]
	{
		new Molecule(certusQuartzMolecule, 2), new Molecule(chargedCertusQuartzMolecule)
	};

	private PotionChemical[] fluixQuartzCrystalSynthesisFormula = new PotionChemical[]
	{
		null, new Molecule(MoleculeEnum.galliumarsenide, 1), null, certusQuartzChemical, null, chargedCertusQuartzChemical, null, chargedCertusQuartzChemical, null
	};

    private PotionChemical[] fluixQuartzDustDecompositionFormula = new PotionChemical[]
    {
            new Molecule(MoleculeEnum.galliumarsenide, 1), new Molecule(certusQuartzMolecule, 1), new Molecule(chargedCertusQuartzMolecule, 2)
    };

    private PotionChemical[] fluixQuartzDustSynthesisFormula = new PotionChemical[]
    {
            null, null, null, certusQuartzChemical, new Molecule(MoleculeEnum.galliumarsenide, 1), chargedCertusQuartzChemical, null, chargedCertusQuartzChemical, null
    };

    private PotionChemical[] fluixQuartzPearlDecompositionFormula = new PotionChemical[]
    {
            new Molecule(MoleculeEnum.galliumarsenide, 2), certusQuartzChemical, new Molecule(chargedCertusQuartzMolecule, 2)
    };

    private PotionChemical[] fluixQuartzPearlSynthesisFormula = new PotionChemical[]
    {
            null, new Molecule(MoleculeEnum.galliumarsenide, 1), null, certusQuartzChemical, new Molecule(MoleculeEnum.galliumarsenide, 1), chargedCertusQuartzChemical, null, chargedCertusQuartzChemical, null
    };

	@Override
	public boolean canHandle(String oreName)
	{
		return oreName.endsWith("CertusQuartz") || oreName.endsWith("Fluix");
	}

	@Override
	public void handle(String oreName)
	{
		if (oreName.equals("dustCertusQuartz"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, certusQuartzDecompositionFormula);
			SynthesisRecipe.createAndAddRecipeSafely(oreName, true, 30000, certusQuartzDustSynthesisFormula);
		} else if (oreName.equals("crystalCertusQuartz"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, certusQuartzDecompositionFormula);
			SynthesisRecipe.createAndAddRecipeSafely(oreName, true, 30000, certusQuartzCrystalSynthesisFormula);
		} else if (oreName.equals("crystalChargedCertusQuartz"))
        {
            DecomposerRecipe.createAndAddRecipeSafely(oreName, chargedCertusQuartzDecompositionFormula);
            SynthesisRecipe.createAndAddRecipeSafely(oreName, true, 30000, chargedCertusQuartzCrystalSynthesisFormula);
		} else if (oreName.equals("crystalFluix"))
		{
			DecomposerRecipe.createAndAddRecipeSafely(oreName, fluixCertusQuartzDecompositionFormula);
			SynthesisRecipe.createAndAddRecipeSafely(oreName, true, 30000, fluixQuartzCrystalSynthesisFormula);
        } else if (oreName.equals("dustFluix"))
        {
            DecomposerRecipe.createAndAddRecipeSafely(oreName, fluixQuartzDustDecompositionFormula);
            SynthesisRecipe.createAndAddRecipeSafely(oreName, true, 30000, fluixQuartzDustSynthesisFormula);
        } else if (oreName.equals("pearlFluix"))
        {
            DecomposerRecipe.createAndAddRecipeSafely(oreName, fluixQuartzPearlDecompositionFormula);
            SynthesisRecipe.createAndAddRecipeSafely(oreName, true, 30000, fluixQuartzPearlSynthesisFormula);
		} else
		{
			Minechem.LOGGER.info("Unknown type of AE2 item : " + oreName);
		}
	}
}
