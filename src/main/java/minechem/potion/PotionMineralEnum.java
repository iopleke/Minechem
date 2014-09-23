package minechem.potion;

import java.util.ArrayList;

import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.Molecule;
import net.minecraft.util.StatCollector;

public enum PotionMineralEnum
{
    quartz("Quartz", new Molecule(MoleculeEnum.siliconDioxide)), berlinite("Berlinite", new Molecule(MoleculeEnum.aluminiumPhosphate, 4)), ;

    // Descriptive name, in en_US. Should not be used; instead, use a
    // localized string from a .properties file.
    private final String descriptiveName;
    // Localization key.
    private final String localizationKey;

    private final ArrayList<PotionChemical> components;

    /** Returns the localized name of this mineral, or an en_US-based placeholder if no localization was found.
     * 
     * @return Localized name of this mineral. */
    public String getName()
    {
        String localizedName = StatCollector.translateToLocal(this.localizationKey);
        if (localizedName.isEmpty())
        {
            return localizationKey;
        }
        return localizedName;
    }

    public PotionChemical[] getComposition()
    {
        return this.components.toArray(new PotionChemical[this.components.size()]);
    }

    PotionMineralEnum(String descriptiveName, PotionChemical... chemicals)
    {
        this.descriptiveName = descriptiveName;
        this.localizationKey = "mineral." + name();
        this.components = new ArrayList<PotionChemical>();
        for (PotionChemical potionChemical : chemicals)
        {
            this.components.add(potionChemical);
        }
    }
}
