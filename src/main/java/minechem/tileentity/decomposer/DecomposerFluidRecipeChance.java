package minechem.tileentity.decomposer;

import java.util.ArrayList;
import java.util.Random;
import minechem.potion.PotionChemical;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class DecomposerFluidRecipeChance extends DecomposerFluidRecipe
{

    static Random random = new Random();
    float chance;

    public DecomposerFluidRecipeChance(String fluid, int amount, float chance, PotionChemical[] chemicals)
    {
        super(fluid, amount, chemicals);
        this.chance = chance;
    }

    public DecomposerFluidRecipeChance(FluidStack fluid, float chance, PotionChemical... chemicals)
    {
        super(fluid, chemicals);
        this.chance = chance;
    }

    public static void createAndAddFluidRecipeSafely(String fluid, int amount, float chance, PotionChemical... chemicals)
    {
        if (FluidRegistry.isFluidRegistered(fluid))
        {
            DecomposerRecipe.add(new DecomposerFluidRecipeChance(fluid, amount, chance, chemicals));
        }
    }

    @Override
    public ArrayList<PotionChemical> getOutput()
    {
        if (random.nextFloat() < this.chance)
        {
            return super.getOutput();
        } else
        {
            return null;
        }
    }

    @Override
    public float getChance()
    {
        return chance;
    }
}
