package minechem.oredictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import minechem.ModMinechem;
import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.synthesis.SynthesisRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class OreDictionaryDefaultHandler implements OreDictionaryHandler
{

    private enum EnumOrePrefix
    {
        dust, block, ingot, ore, dustSmall, nugget, dustDirty, plate, gem
    }

    private String[] supportedOres;

    private Map<OreDictionaryBaseOreEnum, ArrayList<EnumOrePrefix>> seenOres = new HashMap<OreDictionaryBaseOreEnum, ArrayList<EnumOrePrefix>>();

    private Map<OreDictionaryBaseOreEnum, ItemStack> registeredIngots = new HashMap<OreDictionaryBaseOreEnum, ItemStack>();

    public OreDictionaryDefaultHandler()
    {
        ArrayList<String> ores = new ArrayList<String>();
        for (OreDictionaryBaseOreEnum ore : OreDictionaryBaseOreEnum.values())
        {
            ores.add(ore.name());
        }
        supportedOres = ores.toArray(new String[ores.size()]);
    }

    public String[] parseOreName(String oreName)
    {
        for (EnumOrePrefix prefix : EnumOrePrefix.values())
        {
            if (oreName.startsWith(prefix.name()))
            {
                String remainder = oreName.substring(prefix.name().length()).toLowerCase();
                if (Arrays.asList(supportedOres).contains(remainder))
                    return new String[]
                    { prefix.name(), remainder };
            }
        }

        return null;
    }

    @Override
    public boolean canHandle(OreRegisterEvent event)
    {
        if (this.parseOreName(event.Name) != null)
            return true;
        return false;
    }

    @Override
    public void handle(OreRegisterEvent event)
    {
        ModMinechem.LOGGER.info(OreDictionaryDefaultHandler.class.getSimpleName() + " registered : " + event.Name);

        String[] tokens = this.parseOreName(event.Name);
        EnumOrePrefix prefix = EnumOrePrefix.valueOf(tokens[0]);
        OreDictionaryBaseOreEnum ore = OreDictionaryBaseOreEnum.valueOf(tokens[1]);

        switch (prefix)
        {
        case ore:
            DecomposerRecipe.add(new DecomposerRecipe(event.Ore, scaleFloor(ore.getComposition(), 3d)));
            // Removed to prevent dupes with RC
            // SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000, scaleFloor(ore.getComposition(),3d)));
            break;
        case ingot:
            DecomposerRecipe.add(new DecomposerRecipe(event.Ore, ore.getComposition()));
            if (!haveSeen(ore, EnumOrePrefix.dust) && !haveSeen(ore, EnumOrePrefix.dustSmall))
            {
                SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000, ore.getComposition()));
                registeredIngots.put(ore, event.Ore);
            }
            break;

        case nugget:
            DecomposerRecipe.add(new DecomposerRecipe(event.Ore, scaleFloor(ore.getComposition(), 1d / 9d)));
            break;
        case dust:
            DecomposerRecipe.add(new DecomposerRecipe(event.Ore, ore.getComposition()));
            unregisterIngot(ore);
            SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000, ore.getComposition()));
            break;
        case dustDirty:
            DecomposerRecipe.add(new DecomposerRecipe(event.Ore, ore.getComposition()));
            break;
        case plate:
            DecomposerRecipe.add(new DecomposerRecipe(event.Ore, ore.getComposition()));
            break;
        case dustSmall:
            DecomposerRecipe.add(new DecomposerRecipe(event.Ore, scaleFloor(ore.getComposition(), 0.25d)));
            unregisterIngot(ore);
            SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000, scaleCeil(ore.getComposition(), 0.25d)));
            break;
        case gem:
            DecomposerRecipe.add(new DecomposerRecipe(event.Ore, ore.getComposition()));
            SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000, ore.getComposition()));
            break;

        default:
            ModMinechem.LOGGER.warn(OreDictionaryDefaultHandler.class.getSimpleName() + " : Invalid ore dictionary type.");
            break;
        }

        seen(ore, prefix);
    }

    private void unregisterIngot(OreDictionaryBaseOreEnum ore)
    {
        if (registeredIngots.containsKey(ore))
        {
            SynthesisRecipe.remove(registeredIngots.get(ore));
            registeredIngots.remove(ore);
        }
    }

    private PotionChemical[] scaleCeil(PotionChemical[] composition, double factor)
    {
        ArrayList<PotionChemical> newComposition = new ArrayList<PotionChemical>();

        for (PotionChemical chem : composition)
        {
            PotionChemical newChem = chem.copy();
            newChem.amount = (int) Math.ceil(chem.amount * factor);
            newComposition.add(newChem);
        }

        return newComposition.toArray(new PotionChemical[newComposition.size()]);
    }

    private PotionChemical[] scaleFloor(PotionChemical[] composition, double factor)
    {
        ArrayList<PotionChemical> newComposition = new ArrayList<PotionChemical>();

        for (PotionChemical chem : composition)
        {
            PotionChemical newChem = chem.copy();
            newChem.amount = (int) Math.floor(chem.amount * factor);
            newComposition.add(newChem);
        }

        return newComposition.toArray(new PotionChemical[newComposition.size()]);
    }

    private boolean haveSeen(OreDictionaryBaseOreEnum ore, EnumOrePrefix prefix)
    {
        if (this.seenOres.containsKey(ore) && this.seenOres.get(ore).contains(prefix))
            return true;
        return false;
    }

    private void seen(OreDictionaryBaseOreEnum ore, EnumOrePrefix prefix)
    {
        if (!this.seenOres.containsKey(ore))
            this.seenOres.put(ore, new ArrayList<EnumOrePrefix>());
        if (!this.seenOres.get(ore).contains(prefix))
            this.seenOres.get(ore).add(prefix);
    }
}
