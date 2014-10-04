package minechem.oredictionary;

import minechem.Minechem;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.synthesis.SynthesisRecipe;

public class OreDictionaryAppliedEnergisticsHandler implements OreDictionaryHandler
{

    private MoleculeEnum certusQuartzMolecule = MoleculeEnum.aluminiumPhosphate;

    private PotionChemical certusQuartzChemical = new Molecule(certusQuartzMolecule);

    private PotionChemical[] certusQuartzDecompositionFormula = new PotionChemical[]
    { new Molecule(certusQuartzMolecule, 4) };

    private PotionChemical[] certusQuartzCrystalSynthesisFormula = new PotionChemical[]
    { null, certusQuartzChemical, null, certusQuartzChemical, null, certusQuartzChemical, null, certusQuartzChemical, null };

    private PotionChemical[] certusQuartzDustSynthesisFormula = new PotionChemical[]
    { null, certusQuartzChemical, null, certusQuartzChemical, certusQuartzChemical, certusQuartzChemical, null, null, null };

    @Override
    public boolean canHandle(String oreName)
    {
        return oreName.endsWith("CertusQuartz");
    }

    @Override
    public void handle(String oreName)
    {
        if (oreName.equals("dustCertusQuartz"))
        {
            DecomposerRecipe.createAndAddRecipeSafely(oreName, certusQuartzDecompositionFormula);
            SynthesisRecipe.createAndAddRecipeSafely(oreName, true, 30000, certusQuartzDustSynthesisFormula);
            // }
        }
        else if (oreName.equals("crystalCertusQuartz"))
        {
            DecomposerRecipe.createAndAddRecipeSafely(oreName, certusQuartzDecompositionFormula);
            SynthesisRecipe.createAndAddRecipeSafely(oreName, true, 30000, certusQuartzCrystalSynthesisFormula);
            // }
        }
        else
        {
            Minechem.LOGGER.info("Unknown type of Certus Quartz : " + oreName);
        }
    }
}
