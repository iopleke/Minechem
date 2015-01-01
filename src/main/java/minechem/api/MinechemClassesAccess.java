package minechem.api;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * To access minechem classes.
 *
 * @author yushijinhun
 */
class MinechemClassesAccess
{

    static
    {
        AtomicBoolean isInstalled = new AtomicBoolean(true);

        classMoleculeEnum = getClassSafely("minechem.item.molecule.MoleculeEnum", isInstalled);
        classElementEnum = getClassSafely("minechem.item.element.ElementEnum", isInstalled);
        classElement = getClassSafely("minechem.item.element.Element", isInstalled);
        classMolecule = getClassSafely("minechem.item.molecule.Molecule", isInstalled);
        classChemicalRoomStateEnum = getClassSafely("minechem.item.ChemicalRoomStateEnum", isInstalled);
        classPotionChemical = getClassSafely("minechem.potion.PotionChemical", isInstalled);
        classArrayPotionChemical = getClassSafely("[Lminechem.potion.PotionChemical;", isInstalled);
        classChemicalFluidReactionHandler = getClassSafely("minechem.fluid.reaction.ChemicalFluidReactionHandler", isInstalled);
        classChemicalFluidReactionOutput = getClassSafely("minechem.fluid.reaction.ChemicalFluidReactionOutput", isInstalled);
        classChemicalFluidReactionRule = getClassSafely("minechem.fluid.reaction.ChemicalFluidReactionRule", isInstalled);
        classMinechemChemicalType = getClassSafely("minechem.item.MinechemChemicalType", isInstalled);
        classDecomposerRecipe = getClassSafely("minechem.tileentity.decomposer.DecomposerRecipe", isInstalled);
        classSynthesisRecipe = getClassSafely("minechem.tileentity.synthesis.SynthesisRecipe", isInstalled);
        classDecomposerFluidRecipe = getClassSafely("minechem.tileentity.decomposer.DecomposerFluidRecipe", isInstalled);

        isMinechemInstalled = isInstalled.get();
    }

    static final Class classMoleculeEnum;
    static final Class classElementEnum;
    static final Class classElement;
    static final Class classMolecule;
    static final Class classChemicalRoomStateEnum;
    static final Class classArrayPotionChemical;
    static final Class classPotionChemical;
    static final Class classChemicalFluidReactionHandler;
    static final Class classChemicalFluidReactionOutput;
    static final Class classChemicalFluidReactionRule;
    static final Class classMinechemChemicalType;
    static final Class classDecomposerRecipe;
    static final Class classSynthesisRecipe;
    static final Class classDecomposerFluidRecipe;
    static final boolean isMinechemInstalled;

    private MinechemClassesAccess()
    {
    }

    private static Class getClassSafely(String className, AtomicBoolean isInstalled)
    {
        try
        {
            return Class.forName(className);
        } catch (ClassNotFoundException e)
        {
            isInstalled.set(false);
            return null;
        }
    }
}
