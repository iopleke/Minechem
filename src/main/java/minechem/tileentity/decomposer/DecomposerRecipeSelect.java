package minechem.tileentity.decomposer;

import java.util.ArrayList;

import minechem.potion.PotionChemical;
import net.minecraft.item.ItemStack;

public class DecomposerRecipeSelect extends DecomposerRecipeChance
{

    ArrayList<DecomposerRecipe> possibleRecipes = new ArrayList<DecomposerRecipe>();

    public DecomposerRecipeSelect(ItemStack input, float chance, DecomposerRecipe... possibleRecipes)
    {
        super(input, chance);
        for (DecomposerRecipe recipe : possibleRecipes)
            this.possibleRecipes.add(recipe);
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
    
    public DecomposerRecipe getRecipeRaw(){
    	return possibleRecipes.get(0);
    }

    public ArrayList<DecomposerRecipe> getAllPossibleRecipes()
    {
        return this.possibleRecipes;
    }
    
    @Override
    public boolean isNull() {
    	return (super.isNull()&&this.possibleRecipes==null);
    }

}
