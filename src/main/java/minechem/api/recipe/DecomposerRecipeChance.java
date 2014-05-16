package minechem.api.recipe;

import minechem.api.core.Chemical;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class DecomposerRecipeChance extends DecomposerRecipe {

    static Random random = new Random();
    float chance;

    public DecomposerRecipeChance(ItemStack input, float chance, Chemical... output) {
        super(input, output);
        this.chance = chance;
    }

    @Override
    public ArrayList<Chemical> getOutput() {
        if (random.nextFloat() < this.chance)
            return super.getOutput();
        else {
            return null;
        }
    }

    public float getChance() {
        return chance;
    }

}
