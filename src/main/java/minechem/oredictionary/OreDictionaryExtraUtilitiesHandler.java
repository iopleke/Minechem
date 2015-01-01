package minechem.oredictionary;

import java.util.Arrays;
import java.util.List;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.tileentity.decomposer.DecomposerRecipe;

public class OreDictionaryExtraUtilitiesHandler implements OreDictionaryHandler
{

    private static final int BURNT_QUARTZ = 0;
    private static final int ICE_STONE = 1;

    private String[] blockTypes = new String[]
    {
        "burntquartz", "icestone"
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
            case BURNT_QUARTZ:
                DecomposerRecipe.createAndAddRecipeSafely(oreName, new Molecule(MoleculeEnum.siliconDioxide, 4), new Molecule(MoleculeEnum.arsenicOxide, 1),
                        new Molecule(MoleculeEnum.galliumOxide, 1));
            case ICE_STONE:
                DecomposerRecipe.createAndAddRecipeSafely(oreName, new Molecule(MoleculeEnum.water, 4));
        }
    }

}
