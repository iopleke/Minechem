package minechem.oredictionary;

import minechem.api.core.Chemical;
import minechem.api.core.EnumMolecule;
import minechem.api.core.Molecule;
import minechem.api.recipe.DecomposerRecipe;
import minechem.api.recipe.SynthesisRecipe;
import minechem.common.ModMinechem;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class AppliedEnergisticsOreDictionaryHandler implements OreDictionaryHandler
{

    private EnumMolecule certusQuartzMolecule = EnumMolecule.aluminiumPhosphate;

    private Chemical certusQuartzChemical = new Molecule(certusQuartzMolecule);

    private Chemical[] certusQuartzDecompositionFormula = new Chemical[]
    { new Molecule(certusQuartzMolecule, 4) };

    private Chemical[] certusQuartzCrystalSynthesisFormula = new Chemical[]
    { null, certusQuartzChemical, null, certusQuartzChemical, null, certusQuartzChemical, null, certusQuartzChemical, null };

    private Chemical[] certusQuartzDustSynthesisFormula = new Chemical[]
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
