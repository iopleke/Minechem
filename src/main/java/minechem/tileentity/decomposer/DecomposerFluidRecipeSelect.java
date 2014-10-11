package minechem.tileentity.decomposer;

import java.util.ArrayList;

import minechem.potion.PotionChemical;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class DecomposerFluidRecipeSelect extends DecomposerFluidRecipeChance
{

    ArrayList<DecomposerRecipe> possibleRecipes = new ArrayList<DecomposerRecipe>();
    
    public DecomposerFluidRecipeSelect(FluidStack fluid, float chance, DecomposerRecipe[] recipes)
    {
        super(fluid, chance);
        for(DecomposerRecipe rec : recipes) {
            possibleRecipes.add(rec);
        }
    }

    public DecomposerFluidRecipeSelect(String fluid, int amount, float chance, DecomposerRecipe... recipes)
    {
        super(FluidRegistry.getFluidStack(fluid, amount), chance);
        for(DecomposerRecipe rec : recipes) {
            possibleRecipes.add(rec);
        }
    }
    
    @Override
    public ArrayList<PotionChemical> getOutput()
    {
        if (random.nextFloat() < this.chance)
        {
            DecomposerRecipe selectedRecipe = possibleRecipes.get(random.nextInt(possibleRecipes.size()));
            return selectedRecipe.getOutput();
        }
        return null;
    }

    @Override
    public ArrayList<PotionChemical> getOutputRaw()
    {
        return possibleRecipes.get(0).getOutputRaw();
    }

    public ArrayList<DecomposerRecipe> getAllPossibleRecipes()
    {
        return this.possibleRecipes;
    }

}
