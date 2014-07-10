package minechem.tileentity.decomposer;

import java.util.ArrayList;
import java.util.Random;

import minechem.potion.PotionChemical;
import net.minecraft.item.ItemStack;

public class DecomposerRecipe
{

    public static ArrayList<DecomposerRecipe> recipes = new ArrayList<DecomposerRecipe>();

    ItemStack input;
    public ArrayList<PotionChemical> output = new ArrayList<PotionChemical>();

    public static DecomposerRecipe add(DecomposerRecipe recipe)
    {
        recipes.add(recipe);
        return recipe;
    }

    public DecomposerRecipe(ItemStack input, PotionChemical... chemicals)
    {
        this(chemicals);
        this.input = input;
    }

    public DecomposerRecipe(PotionChemical... chemicals)
    {
        for (PotionChemical potionChemical : chemicals)
        {
            this.output.add(potionChemical);
        }
    }

    public ItemStack getInput()
    {
        return this.input;
    }

    public ArrayList<PotionChemical> getOutput()
    {
        return this.output;
    }

    public ArrayList<PotionChemical> getOutputRaw()
    {
        return this.output;
    }

    public ArrayList<PotionChemical> getPartialOutputRaw(int f)
    {
        ArrayList<PotionChemical> raw = getOutput();
        ArrayList<PotionChemical> result = new ArrayList<PotionChemical>();
        if (raw != null)
        {
            for (PotionChemical chem : raw)
            {
                try
                {
                    if (chem != null)
                    {
                        PotionChemical reduced = chem.copy();
                        if (reduced != null)
                        {
                            reduced.amount = (int) Math.floor(chem.amount / f);
                            Random rand = new Random();
                            if (reduced.amount == 0 && rand.nextFloat() > (chem.amount / f))
                            {
                                reduced.amount = 1;
                            }
                            result.add(reduced);
                        }
                    }

                } catch (Exception e)
                {
                    // something has gone wrong
                    // but we do not know quite why
                    // debug code goes here
                }

            }
        }

        return result;
    }
}
