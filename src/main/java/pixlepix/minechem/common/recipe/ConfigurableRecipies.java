package pixlepix.minechem.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import pixlepix.minechem.api.core.*;
import pixlepix.minechem.api.recipe.DecomposerRecipe;

import java.util.ArrayList;

public class ConfigurableRecipies {

    public static void loadConfigurableRecipies(Configuration config) {
        config.load();
        ConfigurableRecipies.makeDummyRecipe(config);
        for (int i = 1; config.hasCategory("decomposerrecipe" + i); i++) {
            ConfigCategory recipe = config.getCategory("decomposerrecipe" + i);
            String result = recipe.get("Input").getString();
            ConfigCategory outputs = config.getCategory("Output");
            ArrayList<Chemical> chem = new ArrayList();

            for (int j = 1; config.hasCategory("decomposerrecipe" + i + "output" + j); j++) {
                ConfigCategory output = config.getCategory("decomposerrecipe" + i + "output" + j);
                boolean isMolecule = output.get("IsMolecule").getBoolean(false);
                int chemicalID = output.get("ChemicalID").getInt();

                int amount = output.get("Amount").getInt(1);
                Chemical newChem;
                if (isMolecule) {
                    newChem = new Molecule(EnumMolecule.getById(chemicalID), amount);

                } else {
                    newChem = new Element(EnumElement.elements[chemicalID], amount);
                }
                chem.add(newChem);
            }
            ItemStack inputStack = new ItemStack(parseId(result), 1, parseMeta(result));
            DecomposerRecipe.add(new DecomposerRecipe(inputStack, (Chemical[]) chem.toArray(new Chemical[10])));
        }
    }

    public static void makeDummyRecipe(Configuration config) {
        config.get("Decomposerrecipe1", "Input", "7:0");

        config.get("Decomposerrecipe1output1", "IsMolecule", false);

        config.get("Decomposerrecipe1output1", "ChemicalID", 2);

        config.get("Decomposerrecipe1output1", "Amount", 16);
        config.save();
    }

    public static int parseId(String s) {
        String ns = s;
        if (s.contains(":")) {
            ns = s.split(":")[0];
        }
        return Integer.parseInt(ns);
    }

    public static int parseMeta(String s) {

        if (s.contains(":")) {
            return Integer.parseInt(s.split(":")[1]);
        }
        return 0;
    }
}
