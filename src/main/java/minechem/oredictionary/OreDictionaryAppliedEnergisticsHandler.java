package minechem.oredictionary;

import minechem.ModMinechem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.Molecule;
import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.synthesis.SynthesisRecipe;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

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
    public boolean canHandle(OreRegisterEvent oreEvent)
    {
        return oreEvent.Name.endsWith("CertusQuartz");
    }

    @Override
    public void handle(OreRegisterEvent oreEvent)
    {
        if (oreEvent.Name.equals("dustCertusQuartz"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(oreEvent.Ore, certusQuartzDecompositionFormula));
            SynthesisRecipe.add(new SynthesisRecipe(oreEvent.Ore, true, 30000, certusQuartzDustSynthesisFormula));
            // }
        }
        else if (oreEvent.Name.equals("crystalCertusQuartz"))
        {
            DecomposerRecipe.add(new DecomposerRecipe(oreEvent.Ore, certusQuartzDecompositionFormula));
            SynthesisRecipe.add(new SynthesisRecipe(oreEvent.Ore, true, 30000, certusQuartzCrystalSynthesisFormula));
            // }
        }
        else
        {
            ModMinechem.LOGGER.info("Unknown type of Certus Quartz : " + oreEvent.Name);
        }
    }
}
