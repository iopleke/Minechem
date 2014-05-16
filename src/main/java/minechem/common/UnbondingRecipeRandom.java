package minechem.common;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.item.ItemStack;

public class UnbondingRecipeRandom extends UnbondingRecipe
{

    private ArrayList<ArrayList<ItemStack>> recipeOutcomes;
    private float chance;
    private Random random;

    public UnbondingRecipeRandom(ItemStack inputStack, float chance, ArrayList<ArrayList<ItemStack>> recipesOutcomes)
    {
        this.random = new Random();
        this.inputStack = inputStack;
        this.chance = chance;
        this.recipeOutcomes = recipesOutcomes;
    }

    @Override
    public ArrayList<ItemStack> getOutput()
    {
        if (random.nextFloat() < chance)
        {
            setRandomOutput();
            return super.getOutput();
        }
        else
            return null;
    }

    private void setRandomOutput()
    {
        int randomPos = random.nextInt(recipeOutcomes.size());
        this.outputStacks = recipeOutcomes.get(randomPos);
    }

}
